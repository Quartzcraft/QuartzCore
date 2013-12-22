package uk.co.quartzcraft.core.entity;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.quartzcraft.core.chat.*;

public class QPlayer {

	public static void sendMessage(CommandSender sender, String message) {
		ChatFormatParser.parseChat(message);
		
		sender.sendMessage(message);
	}
	
	public static HashMap getData(Player player) {
		
		return null;
	}
}
