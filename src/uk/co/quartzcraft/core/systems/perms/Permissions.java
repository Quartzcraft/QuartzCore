package uk.co.quartzcraft.core.systems.perms;

import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import uk.co.quartzcraft.core.QuartzCore;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Permissions {
    public static HashMap<String, PermissionAttachment> permissions = new HashMap<>();

    public static void initialize() {
        try {
            final ResultSet res = QuartzCore.DBCore.createStatement().executeQuery("SELECT * FROM permission_type ORDER BY id");
            while(res.next()) {
                Integer id = res.getInt("id");
                PermType type = PermType.getById(id);
                if (type == null) {
                    throw new IllegalStateException("Could not find a type for ID " + id);
                }
                type.name = res.getString("name");
                type.desc = res.getString("desc");
                type.maxLevel = res.getLong("max_level");
            }
        } catch (Exception e) {
            Util.printException("Could not get permissions from database", e);
            Bukkit.getServer().shutdown();
        }
    }

    public static void registerPlayerPerms(QPlayer qplayer, String[] extraPerms) {
        Player player = qplayer.getPlayer();

        Integer group = qplayer.getGroup();

        if (permissions.containsKey(player.getName())) {
            unregisterPlayerPerms(player);
        }
        PermissionAttachment attachment = player.addAttachment(QuartzCore.plugin);

        attachment.setPermission("empire.everyone", true);

        // everyone (not including guests)
        if (group > 0) {
            attachment.setPermission("empire.normal", true);
        }

        //Iron supporters and above
        if (group > 1) {
            attachment.setPermission("empire.supporter.iron", true);
        }

        //Gold supporters and above
        if (group > 2) {
            attachment.setPermission("empire.supporter.gold", true);
        }

        //Diamond supporters and above
        if (group > 3) {
            attachment.setPermission("empire.supporter.diamond", true);
        }

        //staff and above
        if (group > 5) {
            attachment.setPermission("empire.staff", true);
            /*
            if (Empire.serverId.equals(100)) {
                attachment.setPermission("empire.mode", true);
            }
            */
            attachment.setPermission("empire.staff.mod", true);
        }

        //senior moderators and above
        if (group > 7) {
            attachment.setPermission("empire.staff.srstaff", true);
        }

        //admin and superadmin
        if (group > 8) {
            attachment.setPermission("empire.staff.admin", true);
        }

        registerExtraPerms(qplayer, extraPerms);

        attachment.setPermission("empire.user." + player.getName(), true);
        //attachment.setPermission("empire.server." + QuartzCore.serverName(QuartzCore.serverId), true);
        permissions.put(player.getName(), attachment);
    }

    public static void unregisterPlayerPerms(Player player) {
        if (permissions.containsKey(player.getName())) {
            try {
                player.removeAttachment(permissions.get(player.getName()));
            } catch (IllegalArgumentException ex) {
            }
            permissions.remove(player.getName());
        }
    }

    public static void registerExtraPerms(QPlayer qplayer, String[] perms) {
        Player player = qplayer.getPlayer();

        if(perms == null) {
            return;
        }

        PermissionAttachment attachment = player.addAttachment(QuartzCore.plugin);

        for(String perm : perms) {
            attachment.setPermission(perm, true);
        }

        permissions.put(player.getName(), attachment);
    }
}
