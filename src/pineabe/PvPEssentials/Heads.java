package pineabe.PvPEssentials;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Heads implements Listener 
{
	public final Plugin plugin;
	private Functions Functions;

	public Heads(Plugin plugin) 
	{
		this.plugin = plugin;
		this.Functions = new Functions(plugin);
	}

	//PLAYER HEADS & SCORE
	@EventHandler(priority = EventPriority.MONITOR)
	public void dropHeads(PlayerDeathEvent event) 
	{
		Player p = (Player) event.getEntity();
		Entity k = (Player) p.getKiller();
		if(plugin.getConfig().getBoolean("PvPEssentials.DropHead.Enabled") == true)
		{
			if(p.hasPermission("pvp.candrophead"))
			{
				if(plugin.getConfig().getInt("PvPEssentials.DropHead.RarityLevel") <= 1)
				{
					Functions.dropHead(event, false);
				}
				else{
					Functions.dropHead(event, true);
				}
			}
		}
		if(k instanceof Player)
		{
			pineabe.PvPEssentials.Functions.playerFileConfig.set(((Player) k).getName() + ".kills", pineabe.PvPEssentials.Functions.playerFileConfig.getInt(((Player) k).getName() + ".kills") + 1);
			pineabe.PvPEssentials.Functions.playerFileConfig.set(((Player) k).getName() + ".killstreak", pineabe.PvPEssentials.Functions.playerFileConfig.getInt(((Player) k).getName() + ".killstreak") + 1);
			if(plugin.getConfig().getBoolean("pvpessentials.killstreak.announce") == true)
			{
				if(pineabe.PvPEssentials.Functions.playerFileConfig.getInt(((Player) k).getName() + "killstreak") >= plugin.getConfig().getInt("pvpessentials.killstreak.announceat"))
				{
					Bukkit.broadcastMessage(ChatColor.GOLD + "[PVPE]" + ChatColor.AQUA + " " + ((Player) k).getDisplayName() + "is now a " + pineabe.PvPEssentials.Functions.playerFileConfig.getInt(((Player) k).getName() + "killstreak") + "killstreak!");
				}
			}
		}
		pineabe.PvPEssentials.Functions.playerFileConfig.set(p.getName() + ".deaths", pineabe.PvPEssentials.Functions.playerFileConfig.getInt(p.getName() + ".deaths") + 1);
		pineabe.PvPEssentials.Functions.playerFileConfig.set(p.getName() + ".killstreak", 0);
		try 
		{
			pineabe.PvPEssentials.Functions.playerFileConfig.save(pineabe.PvPEssentials.Functions.playerFile);
		} catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	
	
	//MOB HEADS
	@EventHandler(priority = EventPriority.MONITOR)
	public void dropMobHeads(EntityDeathEvent event) 
	{
		if(plugin.getConfig().getBoolean("PvPEssentials.DropHead.MobEnabled") == true)
		{
			if(plugin.getConfig().getInt("PvPEssentials.DropHead.MobRarityLevel") <= 1)
			{
				Functions.dropMobHead(event, false);
			}
			else
			{
				Functions.dropMobHead(event, true);
			}
		}
	}
}
	