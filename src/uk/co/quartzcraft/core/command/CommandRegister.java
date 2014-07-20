package uk.co.quartzcraft.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.util.ChatPhrase;
import uk.co.quartzcraft.core.entity.QPlayer;

public class CommandRegister implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        QPlayer player = new QPlayer(QuartzCore.plugin, ((Player) sender).getUniqueId()); //TODO Change the instance
		if(command.getName().equalsIgnoreCase("register") && sender instanceof Player) {
			if(player.getValidationCode() != null) {
				sender.sendMessage(ChatPhrase.getPhrase("to_register_on_the_website_please_visit_web"));
				sender.sendMessage(ChatPhrase.getPhrase("these_are_the_fields_required_for_website_registration"));
				sender.sendMessage(ChatPhrase.getPhrase("your_minecraft_username_is") + player.getName());
				sender.sendMessage(ChatPhrase.getPhrase("your_validation_code_is") + player.getValidationCode());
				sender.sendMessage(ChatPhrase.getPhrase("your_quartzcore_id_is") + player.getID());
				return true;
			} else if(player.createValidationCode()) {
				sender.sendMessage(ChatPhrase.getPhrase("to_register_on_the_website_please_visit_web"));
				sender.sendMessage(ChatPhrase.getPhrase("these_are_the_fields_required_for_website_registration"));
				sender.sendMessage(ChatPhrase.getPhrase("your_minecraft_username_is") + player.getName());
				sender.sendMessage(ChatPhrase.getPhrase("your_validation_code_is") + player.getValidationCode());
				sender.sendMessage(ChatPhrase.getPhrase("your_quartzcore_id_is") + player.getID());
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}
}
