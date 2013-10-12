package uk.co.quartzcraft.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import uk.co.quartzcraft.database.*;

import uk.co.quartzcraft.Defaults;
import uk.co.quartzcraft.chat.Announce;

public class CommandTest implements CommandExecutor, Defaults {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("test")){ 
			
			Announce.announce("&4This is a test announcement!");
			Announce.announceWithPrefix("QuartzCraft", "&a", "&6The test command has been run!");
			//run database query
			Announce.announceWithPrefix("Database", "&5", "&bThe database retrived data: " + release);
			return true;
		}
		return false;
	}
}
