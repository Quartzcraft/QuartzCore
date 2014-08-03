package uk.co.quartzcraft.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.systems.chat.ChatFilter;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.systems.log.ChatLogger;
import uk.co.quartzcraft.core.util.Util;


public class ChatListener implements Listener {

	public ChatListener(QuartzCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChatEvent(AsyncPlayerChatEvent chat) {
		String msg = chat.getMessage();

        //TODO log message

        if(ChatFilter.filterChatFind(msg)) {
            Util.sendMsg(chat.getPlayer(), QCChat.getPhrase("chat_contained_bad_words_blocked"));
            chat.setCancelled(true);
        }
		
	}

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChatEventLog(AsyncPlayerChatEvent chat) {
        String msg = chat.getMessage();

        if(!chat.isCancelled()) {
            ChatLogger.log(chat.getPlayer(), msg);
        }
        //TODO logging

    }
}
