package uk.co.quartzcraft.core.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import uk.co.quartzcraft.core.data.QPlayer;

public class QPlayerLoginEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private QPlayer qPlayer;

    public QPlayerLoginEvent(Player arg) {
        player = arg;
        qPlayer = new QPlayer(arg);
    }

    public QPlayerLoginEvent(QPlayer arg) {
        qPlayer = arg;
        player = arg.getPlayer();
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