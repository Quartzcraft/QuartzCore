package uk.co.quartzcraft.core.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReport implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arg0) {
		
		Player player = (Player) sender;
		
		if(command.getName().equalsIgnoreCase("report")) {
			if(!(player instanceof Player)){
	    		player.sendMessage(ChatColor.RED + "This command is for players only!");
	    	} else {
	    		if(arg0.length == 0) {
	    			player.sendMessage(ChatColor.RED + "Please specify a player to report");
	    		} else {
	    			//stuffs
	    			return true;
	    		}
	    	}
		} 
		return true;
	}

}
