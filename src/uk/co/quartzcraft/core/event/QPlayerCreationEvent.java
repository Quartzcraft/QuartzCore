package uk.co.quartzcraft.core.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import uk.co.quartzcraft.core.data.QPlayer;

public class QPlayerCreationEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
    private Player player;
    private QPlayer qPlayer;

    public QPlayerCreationEvent(Player arg, QPlayer arg1) {
        player = arg;
        qPlayer = arg1;
    }

    public QPlayerCreationEvent(QPlayer arg) {
        player = arg.getPlayer();
        qPlayer = arg;
    }
 
    public Player getPlayer() {
        return player;
    }

    public QPlayer getQPlayer() {
        return qPlayer;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
