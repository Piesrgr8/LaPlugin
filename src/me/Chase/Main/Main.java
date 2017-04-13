package me.Chase.Main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Main extends JavaPlugin implements Listener
{
	
	public static ArrayList<Player> chat = new ArrayList<Player>();
	public static ArrayList<Player> instant = new ArrayList<Player>();
	public static ArrayList<Player> fall = new ArrayList<Player>();
	public static ArrayList<Player> staff = new ArrayList<Player>();
	public static ArrayList<Player> frozen = new ArrayList<Player>();
	public static ArrayList<Player> vanish = new ArrayList<Player>();
	
	public static final String vip = "&5VIP&r";
	public static final String vipplus = "&5VIP+&r";
	public static final String premium = "&9Premium&r";
	public static final String mod = "&6Mod&r";
	public static final String admin = "&cAdmin&r";
	public static final String owner = "&4Owner&r";
	
	public static final String noperms = "&9[Permissions] &7You don't have permission! ";
	
	@Override
	public void onEnable()
	{
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(new StaffModeEvents(), this);
	}
	
	@Override
	public void onDisable()
	{
		for(Player p : vanish)
		{
			for(Player p1 : Bukkit.getOnlinePlayers())
			{
				p1.showPlayer(p);
			}
			p.sendMessage(color("&6A reload has forced you to unvanish."));
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		//staffchat or /sc
		if(commandLabel.equalsIgnoreCase("staffchat") || commandLabel.equalsIgnoreCase("sc"))
		{
			if(sender.hasPermission("LV.staffchat"))
			{
				if(args.length > 0)
				{
					// /sc <text>
					
					StringBuilder sb = new StringBuilder();
					for(int i = 1; i < args.length; i++) sb.append(args[i]).append(" ");
					String s = sb.toString();
					
					sendStaffChatMessage((Player) sender, s);
					return true;
				}
				
				if(!chat.contains((Player) sender))
				{
					chat.add((Player) sender);
					sender.sendMessage(color("&9[Staff] &7You have joined the staff chat."));
					return true;
				}
				else
				{
					chat.remove((Player) sender);
					sender.sendMessage(color("&9[Staff] &7You have left the staff chat."));
					return true;
				}
			}
			else
			{
				sender.sendMessage(color(noperms + mod));
				return true;
			}
		}
		
		//instant
		if(commandLabel.equalsIgnoreCase("instant"))
		{
			if(sender.hasPermission("LV.instant"))
			{
				if(!instant.contains((Player) sender))
				{
					instant.add((Player) sender);
					sender.sendMessage(color("&9[Perks] &7Instant mode activated."));
					sender.sendMessage(color("&9[Perks] &eNote: You will not recieve blocks you break while in instant mode."));
					sender.sendMessage(color("&9[Perks] &c&l&nWARNING: VERY FAST"));
					return true;
				}
				else
				{
					instant.remove((Player) sender);
					sender.sendMessage(color("&9[Perks] &7Instant mode deactivated."));
					return true;
				}
			}
			else
			{
				sender.sendMessage(color(noperms + vipplus));
				return true;
			}
		}
		
		//fall
		if(commandLabel.equalsIgnoreCase("fall"))
		{
			if(sender.hasPermission("LV.fall"))
			{
				if(!fall.contains((Player) sender))
				{
					fall.add((Player) sender);
					sender.sendMessage(color("&9[Perks] &7You have disabled fall damage for yourself."));
					return true;
				}
				else
				{
					fall.remove((Player) sender);
					sender.sendMessage(color("&9[Perks] &7You have enabled fall damage for yourself."));
					return true;
				}
			}
			else
			{
				sender.sendMessage(color(noperms + vipplus));
				return true;
			}
		}
		
		
			if(commandLabel.equalsIgnoreCase("fly"))
			{
				if(args.length == 0)
				{
					if(sender.hasPermission("LV.fly"))
					{
						Player p = (Player) sender;
						if(!p.getAllowFlight())
						{
							p.setAllowFlight(true);
							p.sendMessage(color("&9[Perks] &7You have enabled flight."));
							return true;
						}
						else
						{
							p.setAllowFlight(false);
							p.sendMessage(color("&9[Perks] &7You have disabled flight."));
							return true;
						}
					}
					else
					{
						sender.sendMessage(color(noperms + premium));
						return true;
					}
				}
				else if(args.length == 1)
				{
					if(sender.hasPermission("LV.fly.others"))
					{
						if(Bukkit.getOfflinePlayer(args[0]).isOnline())
						{
							Player p = Bukkit.getPlayer(args[0]);
							if(!p.getAllowFlight())
							{
								p.setAllowFlight(true);
								p.sendMessage(color("&9[Perks] &7Flight has been enabled."));
								sender.sendMessage(color("&9[Perks] &7Enabled flight for &e" + p.getName()));
								return true;
							}
							else
							{
								p.setAllowFlight(false);
								p.sendMessage(color("&9[Perks] &7Flight has been disabled."));
								sender.sendMessage(color("&9[Perks] &7Disabled flight for &e" + p.getName()));
								return true;
							}
						}
						else
						{
							sender.sendMessage(color("&9[Perks] &7The requested user is not online!"));
							return true;
						}
					}
					else
					{
						sender.sendMessage(color(noperms + mod));
						return true;
					}
				}
				else
				{
					if(sender.hasPermission("LV.fly") && !sender.hasPermission("LV.fly.others"))
					{
						sender.sendMessage(color("&9[Perks] &7Syntax: /fly"));
					}
					else if(sender.hasPermission("LV.fly.others"))
					{
						sender.sendMessage(color("&9[Perks] &7Syntax: /fly or /fly <player>"));
					}
					else if(!sender.hasPermission("LV.fly"))
					{
						sender.sendMessage(color(noperms + premium));
						return true;
					}
				}
			}
		
		//perks
		if(commandLabel.equalsIgnoreCase("help"))
		{
			//Bukkit.broadcastMessage(PermissionsEx.getUser((Player) sender).getPrefix() + "j");
			
			if(args.length == 0)
			{
				if(ChatColor.stripColor(PermissionsEx.getUser((Player) sender).getPrefix()).equalsIgnoreCase("")) 
					return perks(sender, 1);
				if(ChatColor.stripColor(PermissionsEx.getUser((Player) sender).getPrefix()).equalsIgnoreCase("&5&lVIP&r ")) 
					return perks(sender, 2);
				if(ChatColor.stripColor(PermissionsEx.getUser((Player) sender).getPrefix()).equalsIgnoreCase("&5&lVIP+&r ")) 
					return perks(sender, 3);
				if(ChatColor.stripColor(PermissionsEx.getUser((Player) sender).getPrefix()).equalsIgnoreCase("&9&lPREMIUM&r ")) 
					return perks(sender, 4);
				if(PermissionsEx.getUser((Player) sender).getPrefix().equalsIgnoreCase("&6&lMOD&f ")) 
					return perks(sender, 5);
			}
			else
			{
				if(args[0].equalsIgnoreCase("Default")) return perks(sender, 1);
				if(args[0].equalsIgnoreCase("VIP")) return perks(sender, 2);
				if(args[0].equalsIgnoreCase("VIP+")) return perks(sender, 3);
				if(args[0].equalsIgnoreCase("Premium")) return perks(sender, 4);
				if(args[0].equalsIgnoreCase("Mod")) return perks(sender, 5);
				
				sender.sendMessage(color("&9[Info] &eThere is no rank with the defined prefix: &9" + args[0]));
				return true;
			}
		}
		
		if(commandLabel.equalsIgnoreCase("staff") || commandLabel.equalsIgnoreCase("sm"))
		{
			if(sender.hasPermission("LV.staffmode"))
			{
				if(!staff.contains((Player) sender))
				{
					staff.add((Player) sender);
					staff((Player) sender);
					sender.sendMessage(color("&9[Staff] &7You have enabled staff mode."));
					return true;
				}
				else
				{
					staff.remove((Player) sender);
					staff((Player) sender);
					sender.sendMessage(color("&9[Perks] &7You have disabled staff mode."));
					return true;
				}
			}
			else
			{
				sender.sendMessage(color(noperms + mod));
				return true;
			}
		}
		
		if(commandLabel.equalsIgnoreCase("freeze"))
		{
			if(sender.hasPermission("LV.freeze"))
			{
				if(args.length == 1)
				{
					for(Player p : Bukkit.getOnlinePlayers())
					{
						if(Bukkit.getPlayer(args[0]) == p)
						{
							if(!frozen.contains(p))
							{
								frozen.add(p);
								p.teleport(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 0F, 0F));
								sender.sendMessage(color("&9[Staff] &7You have frozen &e" + p.getName()));
								p.sendMessage(color("&c&l&nYou have been frozen by " + sender.getName()));
								return true;
							}
							else
							{
								frozen.remove((Player) sender);
								sender.sendMessage(color("&9[Staff] &7You have thawed &e" + p.getName()));
								p.sendMessage(color("&a&l&nYou have been thawed by " + sender.getName()));
								return true;
							}
						}
					}
				}
				else
				{
					sender.sendMessage(color("&9[Staff] &eUsage: /freeze <player>"));
					return true;
				}
			}
			else
			{
				sender.sendMessage(color(noperms + mod));
				return true;
			}
		}
		
		if(commandLabel.equalsIgnoreCase("updaterank"))
		{
			if(sender.hasPermission("LV.updaterank"))
			{
				//TODO: Manually update every time there's a new rank
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + args[0] + " group set " + args[1]);
				
				if(args[1].equalsIgnoreCase("Default")) Bukkit.getPlayer(args[0]).sendMessage(color("&9[Ranks] &7You've recieved rank: Default"));
				else if(args[1].equalsIgnoreCase("VIP")) Bukkit.getPlayer(args[0]).sendMessage(color("&9[Ranks] &7You've recieved rank: &5&lVIP"));
				else if(args[1].equalsIgnoreCase("VIP+")) Bukkit.getPlayer(args[0]).sendMessage(color("&9[Ranks] &7You've recieved rank: &5&lVIP+"));
				else if(args[1].equalsIgnoreCase("Premium")) Bukkit.getPlayer(args[0]).sendMessage(color("&9[Ranks] &7You've recieved rank: &9&lPREMIUM"));
				else if(args[1].equalsIgnoreCase("Mod")) Bukkit.getPlayer(args[0]).sendMessage(color("&9[Ranks] &7You've recieved rank: &6&lMOD"));
				else if(args[1].equalsIgnoreCase("Admin")) Bukkit.getPlayer(args[0]).sendMessage(color("&9[Ranks] &7You've recieved rank: &c&lADMIN"));
				else if(args[1].equalsIgnoreCase("Owner")) Bukkit.getPlayer(args[0]).sendMessage(color("&9[Ranks] &7You've recieved rank: &4&lOWNER"));
				
				if(args[1].equalsIgnoreCase("Default")) sender.sendMessage(color("&9[Ranks] &7You've given " + args[0] + " rank: Default"));
				else if(args[1].equalsIgnoreCase("VIP")) sender.sendMessage(color("&9[Ranks] &7You've given " + args[0] + " rank: &5&lVIP"));
				else if(args[1].equalsIgnoreCase("VIP+")) sender.sendMessage(color("&9[Ranks] &7You've given " + args[0] + " rank: &5&lVIP+"));
				else if(args[1].equalsIgnoreCase("Premium")) sender.sendMessage(color("&9[Ranks] &7You've given " + args[0] + " rank: &9&lPREMIUM"));
				else if(args[1].equalsIgnoreCase("Mod")) sender.sendMessage(color("&9[Ranks] &7You've given " + args[0] + " rank: &6&lMOD"));
				else if(args[1].equalsIgnoreCase("Admin")) sender.sendMessage(color("&9[Ranks] &7You've given " + args[0] + " rank: &c&lADMIN"));
				else if(args[1].equalsIgnoreCase("Owner")) sender.sendMessage(color("&9[Ranks] &7You've given " + args[0] + " rank: &4&lOWNER"));
				
				return true;
			}
			else
			{
				sender.sendMessage(color(noperms + owner));
				return true;
			}
		}
		
		if(commandLabel.equalsIgnoreCase("weather"))
		{
			if(sender.hasPermission("LV.weather"))
			{
				if(args.length == 1)
				{
					if(args[0].equalsIgnoreCase("rain") || args[0].equalsIgnoreCase("storm"))
					{
						Player p = (Player) sender;
						p.getWorld().setStorm(true);
						p.getWorld().setThundering(true);
						p.sendMessage(color("&9[Perks] &7You set the weather to rainy."));
						return true;
					}
					else if(args[0].equalsIgnoreCase("sun") || args[0].equalsIgnoreCase("sunny") || args[0].equalsIgnoreCase("clear"))
					{
						Player p = (Player) sender;
						p.getWorld().setStorm(false);
						p.getWorld().setThundering(false);
						p.sendMessage(color("&9[Perks] &7You set the weather to clear."));
						return true;
					}
				}
				else
				{
					sender.sendMessage(color("&9[Perks] &7Syntax: /weather <clear | storm>"));
					return true;
				}
			}
			else
			{
				sender.sendMessage(color(noperms + premium));
				return true;
			}
		}
		
		if(commandLabel.equalsIgnoreCase("vanish") || commandLabel.equalsIgnoreCase("v"))
		{
			if(sender.hasPermission("LV.vanish"))
			{
				Player p = (Player) sender;
				if(!vanish.contains(p))
				{
					vanish.add(p);
					vanish(p);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player " + p.getName() + " prefix &6");
					sender.sendMessage(color("&9[Staff] &7You have vanished!"));
					return true;
				}
				else
				{
					vanish.remove(p);
					vanish(p);
					sender.sendMessage(color("&9[Staff] &7You have been revealed."));
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte player " + p.getName() + " clear");
					return true;
				}
			}
			else
			{
				sender.sendMessage(color(noperms + mod));
			}
		}
		
		if(commandLabel.equalsIgnoreCase("invsee"))
		{
			if(sender.hasPermission("LV.invsee"))
			{
				//TODO
			}
		}
		
		//TODO: /staff or /staffmode - kinda done - inventory saving
	//TODO: /updaterank - done
	//TODO: /freeze - done
		//TODO: Personal Vaults (external plugin???)
		//TODO: Chat formatting with name
		
	//TODO: Vanish? - done
		//TODO: Hard code kits?
		//TODO: Set time
		//TODO: invsee
		//TODO: warps
		//TODO: admin silent join
		//TODO: tptoggle
		//TODO: nicknames
		//TODO: heal and feed SEPERATELY
		//TODO: tpa, tpaccept, and tpdeny
		//TODO: /spawn
		//TODO: /sethome and /home
		
		return true;
	}
	
	public static void staff(Player p)
	{
		if(staff.contains(p))
		{
			p.setGameMode(GameMode.CREATIVE);
			vanish(p, true);
			p.sendMessage("&9[Staff] &7You have vanished!");
		}
		else
		{
			p.setGameMode(GameMode.SURVIVAL);
			vanish(p, false);
			p.sendMessage("&9[Staff] &7You have been revealed.");
		}
	}
	
	public static void vanish(Player p)
	{
		//if you ARE vanished and you use the command
		if(vanish.contains(p)) 
		{
			for(Player p1 : Bukkit.getOnlinePlayers()) 
			{
				if(!p1.hasPermission("LV.vanish.see")) 
				{
					p1.hidePlayer(p);
				}
			}
		}
		else 
		{
			for(Player p1 : Bukkit.getOnlinePlayers()) 
			{
				p1.showPlayer(p);
			}
		}
	}
	
	//b is to enable or disable manually
	public static void vanish(Player p, boolean b)
	{
		//true to vanish the player
		if(b)
		{
			for(Player p1 : Bukkit.getOnlinePlayers()) 
			{
				if(!p1.hasPermission("LV.vanish.see")) 
				{
					p1.hidePlayer(p);
				}
			}
		}
		else
		{
			for(Player p1 : Bukkit.getOnlinePlayers()) 
			{
				p1.showPlayer(p);
			}
		}
	}
	
	public static boolean perks(CommandSender sender, int i)
	{
		if(i == 1)
		{
			sender.sendMessage(color("&9[Info] &ePerks for rank: &7Default"));
			sender.sendMessage(color("&9/spawn        &7- Teleport to spawn."));
			sender.sendMessage(color("&9/sethome      &7- Sets a home, teleport to it with /home."));
			sender.sendMessage(color("&9/tpa <player> &7- Request to teleport to another player."));
			sender.sendMessage(color("&9/tpaccept     &7- Accept a request to teleport to you."));
			sender.sendMessage(color("&9/tpdeny       &7- Deny a request to teleport to you."));
			return true;
		}
		
		if(i == 2)
		{
			sender.sendMessage(color("&9[Info] &ePerks for rank: " + vip));
			sender.sendMessage(color("&9/kit vip &7- Gives player kit pvp."));
			sender.sendMessage(color("&9/heal    &7- Heals player."));
			sender.sendMessage(color("&9/feed    &7- Restores player's hunger."));
			sender.sendMessage(color("&9/fall    &7- Disable fall damage."));
			return true;
		}
		
		if(i == 3)
		{
			sender.sendMessage(color("&9[Info] &ePerks for rank: " + vipplus));
			sender.sendMessage(color("&9/kit vip  &7- Gives player kit pvp."));
			sender.sendMessage(color("&9/kit vip+ &7- Gives player kit pvp+."));
			sender.sendMessage(color("&9/heal     &7- Restores life and hunger."));
			sender.sendMessage(color("&9/feed     &7- Restores hunger."));
			sender.sendMessage(color("&9/fall     &7- Disable fall damage."));
			sender.sendMessage(color("&9/nick     &7- Changes user's chat name."));
			return true;
		}
		
		if(i == 4)
		{
			sender.sendMessage(color("&9[Info] &ePerks for rank: " + premium));
			sender.sendMessage(color("&9/kit vip  &7- Gives player kit pvp."));
			sender.sendMessage(color("&9/kit vip+ &7- Gives player kit pvp+."));
			sender.sendMessage(color("&9/kit premium &7- Gives player kit pvp+."));
			sender.sendMessage(color("&9/heal     &7- Restores life and hunger."));
			sender.sendMessage(color("&9/feed     &7- Restores hunger."));
			sender.sendMessage(color("&9/nick     &7- Changes user's chat name."));
			sender.sendMessage(color("&9/fly      &7- Toggles fly mode."));
			sender.sendMessage(color("&9/instant  &7- Toggles instant mining."));
			return true;
		}
		
		if(i == 5)
		{
			sender.sendMessage(color("&9[Info] &ePerks for rank: " + mod));
			sender.sendMessage(color("&9/warn <player> <reason> &7- Warns player."));
			sender.sendMessage(color("&9/kick <player> <reason> &7- Removes player from server."));
			sender.sendMessage(color("&9/mute <player> <time> <reason> &7- Disables chat for player."));
			sender.sendMessage(color("&9/ban <player> <time> <reason> &7- Prevents player from joining server."));
			sender.sendMessage(color("&9/tp <player> &7- Teleport to another player."));
			sender.sendMessage(color("&9/co i &7- Toggles block inspector."));
			sender.sendMessage(color("&9/staffchat or /sc &7- Toggles staff mode."));
			sender.sendMessage(color("&9/staff &7- Toggles staff mode."));
			sender.sendMessage(color("&9/vanish or /v &7- Toggles flight."));
			return true;
		}
		
		sender.sendMessage(color("&7You should know what your perks are. &9(Admins and Owners)"));
		return true;
	}
	
	
	
	public static void sendStaffChatMessage(Player p, String s)
	{
		for(Player p1 : Bukkit.getOnlinePlayers())
		{
			if(PermissionsEx.getUser(p1).getPrefix().equalsIgnoreCase("&6&lMOD&f ") || PermissionsEx.getUser(p1).getPrefix().equalsIgnoreCase("&c&lADMIN&r ") 
					|| PermissionsEx.getUser(p1).getPrefix().equalsIgnoreCase("&4&lOWNER&r ")) 
			{
				String prefix = ChatColor.stripColor(PermissionsEx.getUser(p).getPrefix());
				p1.sendMessage(color("&9[Staff Chat] &r" + prefix + p.getName() + ": &d" + s));
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		for(Player p : vanish)
		{
			if(!e.getPlayer().hasPermission("LV.vanish.see")) 
			{
				e.getPlayer().hidePlayer(p);
			}
		}
	}
	
	@EventHandler
	public void onLogout(PlayerQuitEvent e)
	{
		if(chat.contains(e.getPlayer())) chat.remove(e.getPlayer());
		if(instant.contains(e.getPlayer())) instant.remove(e.getPlayer());
		for(Player p : Bukkit.getOnlinePlayers()) e.getPlayer().showPlayer(p);
		if(vanish.contains(e.getPlayer())) vanish.remove(e.getPlayer());
	}
	
	@EventHandler
	public void onFallDamage(EntityDamageEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if(e.getCause() == DamageCause.FALL && fall.contains(p))
			{
				e.setCancelled(true);
				p.sendMessage(color("&9[Perks] &7/fall has prevented you from taking fall damage!"));
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) && instant.contains(e.getPlayer()))
		{
			if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
			{
				if(e.getClickedBlock().getLocation().getY() > 0.0D)
				{
					if(!e.getClickedBlock().getType().equals(Material.BEDROCK))
					{
						e.getClickedBlock().setType(Material.AIR);
					}
					else
					{
						e.getPlayer().sendMessage(color("&9[Perks] &7You may not break bedrock with instant."));
						e.setCancelled(true);
					}
				}
				else
				{
					e.getPlayer().sendMessage(color("&9[Perks] &7You may not break blocks at or below Y: 0 with instant."));
					e.setCancelled(true);
				}
			}
			else
			{
				e.getPlayer().sendMessage(color("&9[Perks] &7You must be in survival mode to use instant."));
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e)
	{
		if(chat.contains(e.getPlayer()))
		{
			e.setCancelled(true);
			sendStaffChatMessage(e.getPlayer(), e.getMessage());
		}
	}
	
	public static String color(String msg)
	{
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		return msg;
	}
}
