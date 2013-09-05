package uk.co.quartzcraft.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandDonate implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("donate")){ 
			
			sender.sendMessage(ChatColor.GOLD + "Donate to " + ChatColor.GREEN + "QuartzCraft" + ChatColor.GOLD +"!");
			sender.sendMessage(ChatColor.GOLD + "Go to " + ChatColor.AQUA + "quartzcraft.co.uk?donate " + ChatColor.GOLD + "to donate!");
			return true;
		}
		return false;
	}

}
