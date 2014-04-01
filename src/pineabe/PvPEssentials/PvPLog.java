package pineabe.PvPEssentials;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PvPLog implements Listener 
{
	public final Plugin plugin;

	public PvPLog(Plugin plugin) 
	{
		this.plugin = plugin;
	}
	
	//PvP Log Putter
	@EventHandler(priority = EventPriority.MONITOR)
	public void AntiPvPLog(EntityDamageByEntityEvent event) 
	{
		if(plugin.getConfig().getBoolean("PvPEssentials.PvPLog.Enabled") == true)
		{
			if(event.getEntity().getType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER)
			{
				final Player p = (Player) event.getEntity();
				if(!p.hasPermission("pvp.pvplog.exempt"))
				{
					if(!plugin.map.containsKey(p.getName()))
					{
						p.sendMessage(ChatColor.GRAY + "You are in combat." + ChatColor.RED + " DO NOT LOG OUT");
						plugin.map.put(p.getName(), 1);
					}
					else
					{
						plugin.map.put(p.getName(), 1 + plugin.map.get(p.getName()).intValue());
					}
					Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable()
					{
						public void run()
						{
							if(plugin.map.containsKey(p.getName()))
								{
								if(plugin.map.get(p.getName()).intValue() <= 1)
								{
									plugin.map.remove(p.getName());
									p.sendMessage(ChatColor.GRAY + "It is now safe to log out");
								}
								else
								{
									plugin.map.put(p.getName(), 1 - plugin.map.get(p.getName()).intValue());
								}
							}
						}
					}, (long) (20 * plugin.getConfig().getDouble("PvPEssentials.PvPLog.TaggedTimer")));
				
				}
			}
		}
	}
	
	//PvP Log Checker
	@EventHandler(priority = EventPriority.MONITOR)
	public void PvPLogCheck(PlayerQuitEvent event)
	{
		Player p = (Player) event.getPlayer();
		if(!p.hasPermission("pvp.pvplog.exempt"))
		{
			if(plugin.map.containsKey(p.getName()))
			{
				p.setHealth(0);
				plugin.map.remove(p.getName());
				if(plugin.getConfig().getBoolean("PvPEssentials.PvPLog.Broadcast") == true)
				{
					Bukkit.broadcastMessage(ChatColor.GREEN + "[" + ChatColor.AQUA + "PVPE" + ChatColor.GREEN + "] " + ChatColor.GOLD + plugin.getConfig().getString("PvPEssentials.PvPLog.BroadcastMessage").replace("%p%", p.getName()));
				}
				else
				{
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[" + ChatColor.AQUA + "PVPE" + ChatColor.GREEN + "] " + ChatColor.GOLD + p.getName() + " was caught PvPLogging and killed");
				}
			}
		}
	}
}
	