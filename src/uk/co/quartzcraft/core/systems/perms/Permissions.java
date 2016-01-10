package uk.co.quartzcraft.core.systems.perms;

import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import uk.co.quartzcraft.core.QuartzCore;

import java.util.*;

public class Permissions {
    public static HashMap<UUID, PermissionAttachment> permissions = new HashMap<>();

    public static void registerPlayerPerms(QPlayer qplayer, String[] extraPerms) {
        Player player = qplayer.getPlayer();

        Integer group = qplayer.getGroup().getId();

        if (permissions.containsKey(player.getUniqueId())) {
            unregisterPlayerPerms(player);
        }
        PermissionAttachment attachmentPrimary = player.addAttachment(QuartzCore.plugin);

        attachmentPrimary.setPermission("QCC.everyone", true);

        // guests
        if (group == 1) {
            attachmentPrimary.setPermission("QCC.guest", true);
        }

        // everyone (not including guests)
        if (group >= 2) {
            attachmentPrimary.setPermission("QCC.normal", true);
        }

        //Premium and above
        if (group >= 10) {
            attachmentPrimary.setPermission("QCC.premium.basic", true);
        }

        //Beta Testers and above
        if (group >= 11) {
            attachmentPrimary.setPermission("QCC.premium.beta", true);
        }

        //Premium Plus and above
        if (group >= 12) {
            attachmentPrimary.setPermission("QCC.premium.plus", true);
        }

        //staff and above
        if (group >= 50) {
            attachmentPrimary.setPermission("QCC.staff", true);
        }

        //builders and above
        if (group >= 55) {
            attachmentPrimary.setPermission("QCC.staff.builder", true);
        }

        //training moderators and above
        if(group >= 60) {
            attachmentPrimary.setPermission("QCC.staff.trainingmod", true);
        }

        //moderators and above
        if(group >= 61) {
            attachmentPrimary.setPermission("QCC.staff.mod", true);
        }

        //specific server moderator groups//



        //end of specific server moderator groups//

        //global moderators and above
        if(group >= 80) {
            attachmentPrimary.setPermission("QCC.staff.globalmod", true);
        }

        //senior staff and above
        if (group >= 90) {
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

        permissions.put(player.getUniqueId(), attachmentPrimary);
    }

    public static void unregisterPlayerPerms(Player player) {
        if (permissions.containsKey(player.getUniqueId())) {
            try {
                player.removeAttachment(permissions.get(player.getUniqueId()));
            } catch (IllegalArgumentException ex) {
                Util.printException("Failed to unregister permissions", ex);
            }
            permissions.remove(player.getUniqueId());
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

        permissions.put(player.getUniqueId(), attachmentExtras);
    }

    public static void registerExtraPerm(QPlayer qplayer, String perm) {
        Player player = qplayer.getPlayer();

        if(perm == null) {
            return;
        }

        PermissionAttachment attachmentExtra = player.addAttachment(QuartzCore.plugin);

        attachmentExtra.setPermission(perm, true);

        permissions.put(player.getUniqueId(), attachmentExtra);
    }
}
