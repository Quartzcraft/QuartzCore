package uk.co.quartzcraft.core.command;

import uk.co.quartzcraft.core.QuartzCore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandQuartz implements CommandExecutor {

	private QuartzCore plugin;

	public void quartz(QuartzCore plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("quartz")){ // If the player typed /quartz then do the following...
			sender.sendMessage(ChatColor.GOLD + "QuartzCore version " + ChatColor.GREEN + QuartzCore.version + ChatColor.GOLD + " for Minecraft 1.7.10");
			return true;
		} else {
			return false;
		}
	}
}
