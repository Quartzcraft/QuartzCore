package uk.co.quartzcraft.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import uk.co.quartzcraft.services.AnnounceService;

public class CommandTest implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("test")){ 
			
			AnnounceService.announce("&4This is a test announcement!");
			AnnounceService.announceWithPrefix("QuartzCraft", "&a", "&6The test command has been run!");
			return true;
		}
		return false;
	}
}
