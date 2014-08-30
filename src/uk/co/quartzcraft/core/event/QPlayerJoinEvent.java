package uk.co.quartzcraft.core.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import uk.co.quartzcraft.core.data.QPlayer;

public class QPlayerJoinEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private QPlayer qPlayer;

    public QPlayerJoinEvent(Player arg) {
        player = arg;
        qPlayer = new QPlayer(arg);
    }

    public QPlayerJoinEvent(QPlayer arg) {
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