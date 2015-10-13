package uk.co.quartzcraft.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import uk.co.quartzcraft.core.event.QPlayerGroupChangeEvent;
import uk.co.quartzcraft.core.systems.perms.Permissions;

/**
 * Listens to QPlayer events
 */
public class QPlayerListener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQPlayerGroupChange(QPlayerGroupChangeEvent event) {
        Permissions.registerPlayerPerms(event.getPlayer(), null);
    }
}
