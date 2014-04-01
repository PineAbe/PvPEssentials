package pineabe.PvPEssentials;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Commands implements CommandExecutor {
	
	private Plugin plugin;
	public final HashMap<String, Double> map = new HashMap<String, Double>();
	//private Functions Functions;
	
	public Commands(Plugin plugin) {
		this.plugin = plugin;
		//this.Functions = new Functions(plugin);
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
	{
	
	if(commandLabel.equalsIgnoreCase("pvpe") || commandLabel.equalsIgnoreCase("pvpessentials"))
	{
		if(args.length == 0)
		{
			if(sender.hasPermission("pvp.needfaction"))
			{
				sender.sendMessage(ChatColor.GOLD + "/nf:" + ChatColor.WHITE + " Request a faction");
			}
			if(sender.hasPermission("pvp.score"))
			{
				sender.sendMessage(ChatColor.GOLD + "/score:" + ChatColor.WHITE + " View kills and deaths of players");
			}
			if(sender.hasPermission("pvp.spawnhead"))
			{
				sender.sendMessage(ChatColor.GOLD + "/(s)pawn(h)ead:" + ChatColor.WHITE + " Spawn the head of a player");
			}
			if(sender.hasPermission("pvp.setkills"))
			{
				sender.sendMessage(ChatColor.GOLD + "/setkills:" + ChatColor.WHITE + " Set the kills of players");
			}
			if(sender.hasPermission("pvp.setdeaths"))
			{
				sender.sendMessage(ChatColor.GOLD + "/setdeaths:" + ChatColor.WHITE + " Set the kill deaths of players");
			}
			if(sender.hasPermission("pvp.reload"))
			{
				sender.sendMessage(ChatColor.GOLD + "/pvpe reload:" + ChatColor.WHITE + " Reload the configuration");
			}
		}
		else if(args.length == 1){
			if(args[0].equalsIgnoreCase("reload"))
			{
				if(sender.hasPermission("pvp.reload"))
				{
					plugin.reloadConfig();
					map.clear();
					sender.sendMessage(ChatColor.GREEN + "PvPEssentials Config Successfully Reloaded");
				}
			}
		}
	}
	
	if(commandLabel.equalsIgnoreCase("score"))
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage("This command can only be done by players");
		}
		else
		{
			Player p = (Player) sender;
			if(args.length == 0 && p.hasPermission("pvp.score"))
			{
				p.sendMessage(ChatColor.GOLD + "Kills: " + pineabe.PvPEssentials.Functions.playerFileConfig.getInt(p.getName() + ".kills"));
				p.sendMessage(ChatColor.GOLD + "Deaths: " + pineabe.PvPEssentials.Functions.playerFileConfig.getInt(p.getName() + ".deaths"));
			}
			else if(args.length == 1 && p.hasPermission("pvp.score.others"))
			{
				Player t = Bukkit.getPlayer(args[0]);
				if(t.isOnline())
				{
					p.sendMessage(ChatColor.GOLD + "Kills: " + pineabe.PvPEssentials.Functions.playerFileConfig.getInt(t.getName() + ".kills"));
					p.sendMessage(ChatColor.GOLD + "Deaths: " + pineabe.PvPEssentials.Functions.playerFileConfig.getInt(t.getName() + ".deaths"));
				}
				else
				{
					p.sendMessage(ChatColor.RED + "The player " + args[0] + " is not online");
				}
			}
		}
	}
	
	if(commandLabel.equalsIgnoreCase("setkills"))
	{
		if(sender.hasPermission("pvp.set.kills"))
		{
			if(args.length != 2)
			{
				sender.sendMessage(ChatColor.RED + "Syntax Error: /setkills (playername) (amount)");
			}
			else
			{
				if(sender.getServer().getPlayer(args[0]) != null)
				{
					Player t = Bukkit.getPlayer(args[0]);
					if(Functions.isInt(args[1], 10))
					{
						int amount = Integer.parseInt(args[1]);
						pineabe.PvPEssentials.Functions.playerFileConfig.set(t.getName() + ".kills", amount);
		        		try 
		        		{
		        			pineabe.PvPEssentials.Functions.playerFileConfig.save(pineabe.PvPEssentials.Functions.playerFile);
		        		}
		        		catch (Exception ex) 
		        		{
		        			ex.printStackTrace();
		        		}
						sender.sendMessage(ChatColor.YELLOW + "You have set the kills of " + t.getDisplayName() + " to " + args[1]);
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "Syntax Error: /setkills (playername) (amount)");
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "The player " + args[0] + " is not online");
				}
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
		}
	}
	
	if(commandLabel.equalsIgnoreCase("setdeaths"))
	{
		if(sender.hasPermission("pvp.set.deaths"))
		{
			if(args.length != 2)
			{
				sender.sendMessage(ChatColor.RED + "Syntax Error: /setdeaths (playername) (amount)");
			}
			else
			{
				if(sender.getServer().getPlayer(args[0]) != null)
				{
					Player t = Bukkit.getPlayer(args[0]);
					if(Functions.isInt(args[1], 10))
					{
						int amount = Integer.parseInt(args[1]);
						pineabe.PvPEssentials.Functions.playerFileConfig.set(t.getName() + ".deaths", amount);
		        		try 
		        		{
		        			pineabe.PvPEssentials.Functions.playerFileConfig.save(pineabe.PvPEssentials.Functions.playerFile);
		        		}
		        		catch (Exception ex) 
		        		{
		        			ex.printStackTrace();
		        		}
						sender.sendMessage(ChatColor.YELLOW + "You have set the deaths of " + t.getDisplayName() + " to " + args[1]);
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "Syntax Error: /setdeaths (playername) (amount)");
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "The player " + args[0] + " is not online");
				}
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
		}
	}
	
	if(commandLabel.equalsIgnoreCase("nf"))
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage("This command can only be done by players");
			return false;
		}
		
		final Player p = (Player) sender;
		if(plugin.getConfig().getBoolean("PvPEssentials.NeedFaction.Enabled") == true)
		{
			if(p.hasPermission("pvp.needfaction"))
			{
				if(!map.containsKey(p.getName()))
				{
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("PvPEssentials.NeedFaction.Message")/*.replace("pp", p.getDisplayName())*/));
					map.put(p.getName(), plugin.getConfig().getDouble("PvPEssentials.NeedFaction.Cooldown"));
					Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable()
					{
						public void run()
						{
							int time = map.get(p.getName()).intValue();
							if(time >= 1)
							{
								time --;
							}
							else
							{
								map.remove(p.getName());
							}
						}
					}, 1200L, 1200L);
				}
				else
				{
					int time = map.get(p.getName()).intValue();
					p.sendMessage(ChatColor.RED + "You cannot use this command for another " + time + " minute(s)");
				}
			}
			else
			{
				p.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			}
		}
	}
	
	if(commandLabel.equalsIgnoreCase("spawnhead") || commandLabel.equalsIgnoreCase("sh"))
	{
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			if(p.hasPermission("pvp.spawnhead"))
			{
				if(args.length == 0)
				{
					ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
					ItemMeta itemMeta = item.getItemMeta();
					((SkullMeta) itemMeta).setOwner(p.getName());
					item.setItemMeta(itemMeta);
					p.getInventory().addItem(item);
				}
				else if(args.length == 1)
				{
					ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
					ItemMeta itemMeta = item.getItemMeta();
					((SkullMeta) itemMeta).setOwner(args[0]);
					item.setItemMeta(itemMeta);
					p.getInventory().addItem(item);
				}
				else
				{
					p.sendMessage(ChatColor.RED + "Usage: /spawnhead (playername)");
				}
			}
			else
			{
				p.sendMessage(ChatColor.RED + "You do not have permission to use this command");
			}		
		}
		else
		{
			sender.sendMessage("This command can only be done by players");	
		}
	}
		return false;
	}

}
