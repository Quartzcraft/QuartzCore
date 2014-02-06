package uk.co.quartzcraft.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import uk.co.quartzcraft.core.QuartzCore;


public class ChatListener implements Listener {

	public ChatListener(QuartzCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChatEvent(AsyncPlayerChatEvent chat) {
		
		
	}
}
