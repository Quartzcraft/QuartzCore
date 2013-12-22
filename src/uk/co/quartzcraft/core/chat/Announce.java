package uk.co.quartzcraft.core.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;


public class Announce {

	public static void announce(String message) {
		
		message = ChatFormatParser.parseChat(message);
				
		Bukkit.getServer().broadcastMessage(message);
	}
	
	public static void announceWithPrefix(String prefix, String prefixColour, String message) {
		
		//Parse Message
		message = ChatFormatParser.parseChat(message);
		
		//Parse Prefix
		prefix = ChatFormatParser.parseChat(prefix);
		
		//Parse Prefix Colour
		prefixColour = ChatFormatParser.parseChat(prefixColour);
		
		Bukkit.getServer().broadcastMessage(prefixColour + "[" + prefix + prefixColour + "] " + ChatColor.RESET + message);
	}
	
	//
}
