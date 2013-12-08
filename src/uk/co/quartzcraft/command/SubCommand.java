package uk.co.quartzcraft.command;

import org.bukkit.command.CommandSender;

public interface SubCommand {

	/**
	 * To be called to execute a sub command.
	 * 
	 * @author mba2012
	 */
	public boolean onSubCommand(String SubCommand, CommandSender sender, String[] arg0);
}
