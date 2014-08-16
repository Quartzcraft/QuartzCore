package uk.co.quartzcraft.core.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import uk.co.quartzcraft.core.data.QPlayer;

public class QPlayerGroupChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private QPlayer player;

    public QPlayerGroupChangeEvent(QPlayer arg) {
        player = arg;
    }

    public QPlayer getPlayer() {
        return player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
