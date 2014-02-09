package uk.co.quartzcraft.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import uk.co.quartzcraft.core.entity.QPlayer;

public class QPlayerGroupSubCommand extends QSubCommand {

	@Override
	public String getPermission() {
		return "QCC.QPlayer.group";
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		QPlayer.setPrimaryGroup(sender, label, label);

	}

}
