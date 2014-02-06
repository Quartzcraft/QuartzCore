package uk.co.quartzcraft.core.command;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPromo implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg0) {
		if(cmd.getName().equalsIgnoreCase("promo")){ 
			return true;
		} else {
			return false;
		}
	}

}
