package uk.co.quartzcraft.command;

import uk.co.quartzcraft.QuartzCore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandQuartz implements CommandExecutor {

	private QuartzCore plugin;

	public void quartz(QuartzCore plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("quartz")){ // If the player typed /quartz then do the following...
			sender.sendMessage(ChatColor.GOLD + "QuartzCore version " + ChatColor.GREEN + QuartzCore.release + " " + QuartzCore.version + ChatColor.GOLD + " for Minecraft 1.6.2");
			return true;
		} //If this has happened the function will return true. 
	        // If this hasn't happened the a value of false will be returned.
		return false; 
	}
}
