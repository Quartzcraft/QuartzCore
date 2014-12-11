package uk.co.quartzcraft.core.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.systems.chat.PreparedMessage;

import java.util.ArrayList;
import java.util.List;

public class PInfoExtraFieldsEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private CommandSender sender;
    private QPlayer tPlayer;
    private List<String> fields = new ArrayList<String>();

    /**
     * Calls a new PInfoExtraFieldsEvent when extra fields need to be added to the pinfo information.
     *
     * @param sender The player requesting information
     * @param target The player who's information has been requested
     */
    public PInfoExtraFieldsEvent(CommandSender sender, QPlayer target) {
        this.sender = sender;
        this.tPlayer = target;
    }

    public CommandSender getSender() {
        return sender;
    }

    public QPlayer getTarget() {
        return tPlayer;
    }

    public List<String> getFields() {
        return fields;
    }

    public void addField(String msg) {
        fields.add(msg);
    }

    public void addField(PreparedMessage arg) {
        fields.add(arg.toString());
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
