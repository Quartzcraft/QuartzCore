package uk.co.quartzcraft.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.util.Util;

public class CommandRegister implements CommandExecutor {
	//TODO migrate to new command system

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        QPlayer player = new QPlayer((Player) sender);
		Player bukkitPlayer = (Player) sender;
		if(command.getName().equalsIgnoreCase("register") && sender instanceof Player) {
			if(player.getValidationCode() != null) {
				Util.sendMsg(bukkitPlayer, QCChat.getPhrase("to_register_on_the_website_please_visit_web"));
				Util.sendMsg(bukkitPlayer, QCChat.getPhrase("these_are_the_fields_required_for_website_registration"));
				Util.sendMsg(bukkitPlayer, QCChat.getPhrase("your_minecraft_username_is") + player.getName());
				Util.sendMsg(bukkitPlayer, QCChat.getPhrase("your_validation_code_is") + player.getValidationCode());
				Util.sendMsg(bukkitPlayer, QCChat.getPhrase("your_quartzcore_id_is") + player.getID());
				return true;
			} else if(player.createValidationCode()) {
				Util.sendMsg(bukkitPlayer, QCChat.getPhrase("to_register_on_the_website_please_visit_web"));
				Util.sendMsg(bukkitPlayer, QCChat.getPhrase("these_are_the_fields_required_for_website_registration"));
				Util.sendMsg(bukkitPlayer, QCChat.getPhrase("your_minecraft_username_is") + player.getName());
				Util.sendMsg(bukkitPlayer, QCChat.getPhrase("your_validation_code_is") + player.getValidationCode());
				Util.sendMsg(bukkitPlayer, QCChat.getPhrase("your_quartzcore_id_is") + player.getID());
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}
}
