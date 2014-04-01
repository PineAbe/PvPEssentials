package pineabe.PvPEssentials;

import pineabe.PvPEssentials.Plugin;

import java.io.File;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Functions {
	
	private Plugin plugin;
    
	public static File folder;
	public static File playerFile;
	public static FileConfiguration playerFileConfig;
	
	public Functions(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public void createConfig() {
	  	  File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
	  	  if (!file.exists()) {
	  	   plugin.getLogger().info(Level.WARNING + " Generating config.yml for PvPEssentials");
	  	   plugin.getConfig().options().copyDefaults(true);
	  	   plugin.saveConfig();
	  	  }
	 }
	
	public void createPlayerData() {
		folder = plugin.getDataFolder();
		playerFile = new File(folder, "PlayerData.yml");
		playerFileConfig = new YamlConfiguration();
		
		if(!folder.exists())
		{
			try
			{
				folder.mkdir();
				Plugin.log.info("Creating directory for PVPEssentials");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		if(!playerFile.exists())
		{
			try
			{
				playerFile.createNewFile();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		try
		{
			playerFileConfig.load(playerFile);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	public void dropHead(PlayerDeathEvent event, boolean Random){
		Player p = (Player) event.getEntity();
		if(p.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK){
			if(Random == false){
				ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
				ItemMeta itemMeta = item.getItemMeta();
				((SkullMeta) itemMeta).setOwner(p.getName());
				item.setItemMeta(itemMeta);
				event.getDrops().add(item);
			}
			else{
				Random rand = new Random();
				int randn = rand.nextInt(plugin.getConfig().getInt("PvPEssentials.DropHead.RarityLevel"));
				if(randn == 1){
					ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
					ItemMeta itemMeta = item.getItemMeta();
					((SkullMeta) itemMeta).setOwner(p.getName());
					item.setItemMeta(itemMeta);
					event.getDrops().add(item);
				}
			}
		}
    }
	
	public void dropMobHead(EntityDeathEvent event, boolean Random){
		Entity e = event.getEntity();
		if(e.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK){
			if(Random == false){
				if(e.getType() == EntityType.ZOMBIE){
					event.getDrops().add(new ItemStack(Material.SKULL_ITEM, 1, (byte) 2));
				}
				if(e.getType() == EntityType.SKELETON){
					event.getDrops().add(new ItemStack(Material.SKULL_ITEM, 1, (byte) 0));
				}
				if(e.getType() == EntityType.CREEPER){
					event.getDrops().add(new ItemStack(Material.SKULL_ITEM, 1, (byte) 4));
				}
			}
			else{
				Random rand = new Random();
				int randn = rand.nextInt(plugin.getConfig().getInt("PvPEssentials.DropHead.MobRarityLevel"));
				if(randn == 1){
					if(e.getType() == EntityType.ZOMBIE){
						event.getDrops().add(new ItemStack(Material.SKULL_ITEM, 1, (byte) 2));
					}
					if(e.getType() == EntityType.SKELETON){
						event.getDrops().add(new ItemStack(Material.SKULL_ITEM, 1, (byte) 0));
					}
					if(e.getType() == EntityType.CREEPER){
						event.getDrops().add(new ItemStack(Material.SKULL_ITEM, 1, (byte) 4));
					}
				}
			}
		}
	}
	
	public static boolean isInt(String s, int radix) 
	{
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) 
	    {
	        if(i == 0 && s.charAt(i) == '-') 
	        {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
}