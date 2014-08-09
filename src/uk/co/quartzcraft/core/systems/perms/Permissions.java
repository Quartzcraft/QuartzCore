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
        PermissionAttachment attachmentPrimary = player.addAttachment(QuartzCore.plugin);

        attachmentPrimary.setPermission("QCC.everyone", true);

        // everyone (not including guests)
        if (group >= 1) {
            attachmentPrimary.setPermission("QCC.normal", true);
        }

        //Iron supporters and above
        if (group >= 2) {
            attachmentPrimary.setPermission("QCC.premium.beta", true);
        }

        //Gold supporters and above
        if (group >= 3) {
            attachmentPrimary.setPermission("QCC.premium.one", true);
        }

        //Diamond supporters and above
        if (group >= 4) {
            attachmentPrimary.setPermission("QCC.premium.two", true);
        }

        //staff and above
        if (group >= 90) {
            attachmentPrimary.setPermission("QCC.staff", true);
        }

        //moderators and above
        if(group >= 95) {
            attachmentPrimary.setPermission("QCC.staff.mod", true);
        }

        //senior moderators and above
        if (group >= 97) {
            attachmentPrimary.setPermission("QCC.staff.srstaff", true);
        }

        //admin
        if (group >= 99) {
            attachmentPrimary.setPermission("QCC.staff.admin", true);
        }

        //owner
        if (group >= 100) {
            attachmentPrimary.setPermission("QCC.staff.owner", true);
        }

        if(extraPerms != null) {
            registerExtraPerms(qplayer, extraPerms);
        }

        registerNameColourPerm(qplayer);
        registerNamePrefixPerm(qplayer);


        permissions.put(player.getName(), attachmentPrimary);
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

        PermissionAttachment attachmentExtras = player.addAttachment(QuartzCore.plugin);

        for(String perm : perms) {
            attachmentExtras.setPermission(perm, true);
        }

        permissions.put(player.getName(), attachmentExtras);
    }

    public static void registerExtraPerm(QPlayer qplayer, String perm) {
        Player player = qplayer.getPlayer();

        if(perm == null) {
            return;
        }

        PermissionAttachment attachmentExtra = player.addAttachment(QuartzCore.plugin);

        attachmentExtra.setPermission(perm, true);

        permissions.put(player.getName(), attachmentExtra);
    }

    public static void registerNameColourPerm(QPlayer qplayer) {
        Player player = qplayer.getPlayer();

        PermissionAttachment attachmentColour = player.addAttachment(QuartzCore.plugin);

        switch(qplayer.getGroup()) {
            default:
                attachmentColour.setPermission("QCC.namecolour.white", true);

            case 2:
                attachmentColour.setPermission("QCC.namecolour.pink", true);

            case 3:
                attachmentColour.setPermission("QCC.namecolour.pink", true);

            case 4:
                attachmentColour.setPermission("QCC.namecolour.pink", true);

            case 95:
                attachmentColour.setPermission("QCC.namecolour.green", true);

            case 97:
                attachmentColour.setPermission("QCC.namecolour.lime", true);

            case 99:
                attachmentColour.setPermission("QCC.namecolour.red", true);

            case 100:
                attachmentColour.setPermission("QCC.namecolour.purple", true);
        }

        permissions.put(player.getName(), attachmentColour);
    }

    public static void registerNamePrefixPerm(QPlayer qplayer) {
        Player player = qplayer.getPlayer();

        PermissionAttachment attachmentColour = player.addAttachment(QuartzCore.plugin);

        switch(qplayer.getGroup()) {
            default:
                attachmentColour.setPermission("QCC.nameprefix.null", true);

            case 91:
                attachmentColour.setPermission("QCC.namecolour.B", true);
        }

        permissions.put(player.getName(), attachmentColour);
    }
}
