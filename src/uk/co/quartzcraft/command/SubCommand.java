package uk.co.quartzcraft.command;

import org.bukkit.command.CommandSender;

public interface SubCommand {

	public boolean onSubCommand(String SubCommand, CommandSender sender, String[] arg0);
}
