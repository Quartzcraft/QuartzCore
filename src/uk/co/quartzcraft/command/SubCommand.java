package uk.co.quartzcraft.command;

import org.bukkit.command.CommandSender;

public interface SubCommand {

	/*
	 * To be called to execute a sub command.
	 */
	public boolean onSubCommand(String SubCommand, CommandSender sender, String[] arg0);
}
