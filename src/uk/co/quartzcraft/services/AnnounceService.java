package uk.co.quartzcraft.services;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class AnnounceService {

	public static void announce(String message) {
		
		message = ChatFormatParseService.parseChatColour(message);
				
		Bukkit.getServer().broadcastMessage(message);
	}
	
	public static void announceWithPrefix(String prefix, String prefixColour, String message) {
		
		//Parse Message
		message = ChatFormatParseService.parseChatColour(message);
		
		//Parse Prefix
		prefix = ChatFormatParseService.parseChatColour(prefix);
		
		//Parse Prefix Colour
		prefixColour = ChatFormatParseService.parseChatColour(prefixColour);
		
		Bukkit.getServer().broadcastMessage(prefixColour + "[" + prefix + "] " + message);
	}
}
