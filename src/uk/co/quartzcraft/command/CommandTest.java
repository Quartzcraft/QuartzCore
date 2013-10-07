package uk.co.quartzcraft.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import uk.co.quartzcraft.chat.Announce;

public class CommandTest implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("test")){ 
			
			Announce.announce("&4This is a test announcement!");
			Announce.announceWithPrefix("QuartzCraft", "&a", "&6The test command has been run!");
			return true;
		}
		return false;
	}
}
