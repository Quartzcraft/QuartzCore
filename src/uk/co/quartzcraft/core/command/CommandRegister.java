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
			if(QPlayer.getValidationCode(player) != null) {
				sender.sendMessage(ChatPhrase.getPhrase("these_are_the_fields_required_for_website_registration"));
				sender.sendMessage(ChatPhrase.getPhrase("your_minecraft_username_is") + player.getDisplayName());
				sender.sendMessage(ChatPhrase.getPhrase("your_validation_code_is") + QPlayer.getValidationCode(player));
				sender.sendMessage(ChatPhrase.getPhrase("your_quartzcore_id_is") + QPlayer.getUserID(player));
				return true;
			} else if(QPlayer.createValidationCode(player)) {
				sender.sendMessage(ChatPhrase.getPhrase("these_are_the_fields_required_for_website_registration"));
				sender.sendMessage(ChatPhrase.getPhrase("your_minecraft_username_is") + player.getDisplayName());
				sender.sendMessage(ChatPhrase.getPhrase("your_validation_code_is") + QPlayer.getValidationCode(player));
				sender.sendMessage(ChatPhrase.getPhrase("your_quartzcore_id_is") + QPlayer.getUserID(player));
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}
}
