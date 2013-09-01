package uk.co.quartzcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminGoCommand {

public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("go")){ 
			
			Player player = (Player) sender;
			
			if(!(player instanceof Player)){
	    		player.sendMessage(ChatColor.RED + "This command is for players only!");
	    	} else {
	    		if(args.length == 0) {
	    			player.sendMessage(ChatColor.RED + "Please specify a player using /go [playername].");
	    			return true;
	    		} else if(args.length < 2) {
	    			player.sendMessage(ChatColor.RED + "Please specify a playername using more than 3 letters.");
	    			return true;
	    		}
	    		
	    		Player target = Bukkit.getServer().getPlayer(args[0]);
	    		
	    		if (target == null) {
	    			player.sendMessage(ChatColor.RED + "Could not find player " + ChatColor.BLUE + args[0] + ChatColor.RED + ". Make sure the player is online.");
	    			return true;
	    		} else {
	    			player.teleport(target);
		    		player.sendMessage(ChatColor.GREEN + "Teleported to " + ChatColor.BLUE + args[0] + ChatColor.GREEN + ".");
		    		return true;
	    		}
	    		
	    	}
			
		}
		return false;
	}
}
