package me.Chase.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Chase.Main.Main;

public class Staffchat implements CommandExecutor
{
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
    {
    	if(commandLabel.equalsIgnoreCase("Staffchat") || commandLabel.equalsIgnoreCase("sc"))
    	{
    		if(sender.hasPermission("LV.staffchat"))
    		{
    			if(args.length > 0)
    			{
    				// /sc <text>
    				
    				StringBuilder sb = new StringBuilder();
    				for(int i = 1; i < args.length; i++) sb.append(args[i]).append(" ");
    				String s = sb.toString();
    				
    				Main.sendStaffChatMessage((Player) sender, s);
    				return true;
    			}
    			
    			if(!Main.chat.contains((Player) sender))
    			{
    				Main.chat.add((Player) sender);
    				sender.sendMessage(Main.color("&9[Staff] &7You have joined the staff chat."));
    				return true;
    			}
    			else
    			{
    				Main.chat.remove((Player) sender);
    				sender.sendMessage(Main.color("&9[Staff] &7You have left the staff chat."));
    				return true;
    			}
    		}
    		else
    		{
    			sender.sendMessage(Main.color(Main.noperms + Main.mod));
    			return true;
    		}
    	}
    	
    	return true;
    }
}