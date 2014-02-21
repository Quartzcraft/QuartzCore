package uk.co.quartzcraft.core.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class QPlayerCreationEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
    private Player player;
 
    public QPlayerCreationEvent(Player arg) {
        player = arg;
    }
 
    public Player getPlayer() {
        return player;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
