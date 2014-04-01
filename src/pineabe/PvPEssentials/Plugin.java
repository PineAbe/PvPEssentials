package pineabe.PvPEssentials;

import pineabe.PvPEssentials.Heads;
import pineabe.PvPEssentials.PvPLog;

import java.util.HashMap;
import java.util.logging.Logger;

import pineabe.PvPEssentials.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import pineabe.PvPEssentials.Functions;

public class Plugin extends JavaPlugin{
	public static final Logger log = Logger.getLogger("Minecraft");
	
	private Functions Functions;
	public final HashMap<String, Integer> map = new HashMap<String, Integer>();
	public void onEnable() 
	{
		log.info(String.format(
				"[%s] Version %s By PineAbe is now enabled!.",
				getDescription().getName(), getDescription().getVersion(),
				getDescription().getAuthors()));
		
		this.Functions = new Functions(this);
		Functions.createConfig();
		Functions.createPlayerData();
		
		map.clear();
		
		CommandExecutor listener = new Commands(this);
		if(getServer().getPluginManager().isPluginEnabled("Factions"))
		{
			getCommand("nf").setExecutor(listener);
		}
        getCommand("pvpessentials").setExecutor(listener);
        getCommand("pvpe").setExecutor(listener);
        getCommand("spawnhead").setExecutor(listener);
        getCommand("sh").setExecutor(listener);
        getCommand("score").setExecutor(listener);
        getCommand("setkills").setExecutor(listener);
        getCommand("setdeaths").setExecutor(listener);

        getServer().getPluginManager().registerEvents(new PvPLog(this), this);
        getServer().getPluginManager().registerEvents(new Heads(this), this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
        {
        	public void run()
        	{
        		try 
        		{
        			pineabe.PvPEssentials.Functions.playerFileConfig.save(pineabe.PvPEssentials.Functions.playerFile);
        		}
        		catch (Exception ex) 
        		{
        			ex.printStackTrace();
        		}
        	}
        }, 12000L, 12000L);
	}


	public void onDisable() 
	{
		try 
		{
			pineabe.PvPEssentials.Functions.playerFileConfig.save(pineabe.PvPEssentials.Functions.playerFile);
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		log.info(String.format("[%s] Disabled Version %s", getDescription()
				.getName(), getDescription().getVersion()));
	}
	
}
