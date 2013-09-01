package uk.co.quartzcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerListCommand implements CommandExecutor  {
	
	int numberOnlinePlayers = Bukkit.getOnlinePlayers().length;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("list")){ 
			sender.sendMessage(ChatColor.AQUA + "Players Online(" + numberOnlinePlayers + "):");
			sender.sendMessage(ChatColor.AQUA + "These are the online players.");
			
			return true;
		}
		return false;
	}

}
