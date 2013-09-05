package uk.co.quartzcraft.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface QuartzCommand {

	void DefineCommand();
	
	boolean RunCommand(CommandSender sender, Command command);
}
