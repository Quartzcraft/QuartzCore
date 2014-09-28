package uk.co.quartzcraft.core.systems.perms;

import org.bukkit.ChatColor;
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

    public static void registerPlayerPerms(QPlayer qplayer, String[] extraPerms) {
        Player player = qplayer.getPlayer();

        Integer group = qplayer.getGroup().getId();

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

        //builders and above
        if (group >= 92) {
            attachmentPrimary.setPermission("QCC.staff.builder", true);
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

        permissions.put(player.getName(), attachmentPrimary);
    }

    public static void unregisterPlayerPerms(Player player) {
        if (permissions.containsKey(player.getName())) {
            try {
                player.removeAttachment(permissions.get(player.getName()));
            } catch (IllegalArgumentException ex) {
                Util.printException("Failed to unregister permissions", ex);
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
}
