package uk.co.quartzcraft.core.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;


public class Announce {

	public static void announce(String message) {
		
		message = ChatFormatParser.parseChatColour(message);
				
		Bukkit.getServer().broadcastMessage(message);
	}
	
	public static void announceWithPrefix(String prefix, String prefixColour, String message) {
		
		//Parse Message
		message = ChatFormatParser.parseChatColour(message);
		
		//Parse Prefix
		prefix = ChatFormatParser.parseChatColour(prefix);
		
		//Parse Prefix Colour
		prefixColour = ChatFormatParser.parseChatColour(prefixColour);
		
		Bukkit.getServer().broadcastMessage(prefixColour + "[" + prefix + "] " + message);
	}
	
	//
}
