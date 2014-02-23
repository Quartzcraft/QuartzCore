package uk.co.quartzcraft.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.quartzcraft.core.chat.ChatPhrase;
import uk.co.quartzcraft.core.entity.QPlayer;

public class CommandRegister implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if(command.getName().equalsIgnoreCase("register")) {
			sender.sendMessage(ChatPhrase.getPhrase("your_validation_code_is_X") + QPlayer.getValidationCode(player));
			return true;
		} else {
			return false;
		}
		
	}
}
