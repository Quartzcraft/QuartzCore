package uk.co.quartzcraft.core.util;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.systems.chat.ChatFormat;
import uk.co.quartzcraft.core.systems.chat.QCChat;

import java.sql.Timestamp;
import java.util.logging.Level;

public class Util {

    public static void printException(String message, Exception e) {
        QuartzCore.logger.log(Level.WARNING, "[QC] " + message);
        e.printStackTrace();
    }

    public static void printException(Level level, String message, Exception e) {
        QuartzCore.logger.log(level, "[QC] " + message);
        e.printStackTrace();
    }

    public static void printException(Exception e) {
        QuartzCore.logger.log(Level.WARNING, "[QC]An exception occurred!");
        e.printStackTrace();
    }

    public static void log(Level level, String message) {
        QuartzCore.logger.log(level, "[QC]" + message);
    }

    public static void log(String message) {
        QuartzCore.logger.log(Level.INFO, "[QC]" + message);
    }

    public static String colour(String s) {
        return ChatFormat.parse(s);
    }

    public static long timestamp() {
        java.util.Date date= new java.util.Date();
        Timestamp ts1 = new Timestamp(date.getTime());
        long tsTime1 = ts1.getTime();
        return tsTime1;
    }

    public static void sendMsg(Player player, String msg) {
        String finalmsg = Util.colour(msg);
        player.sendMessage(QCChat.getPhrase("official_prefix") + finalmsg);
    }

    public static void sendMsg(Player player, TextComponent msg) {
        TextComponent finalmsg = new TextComponent(QCChat.getPhrase("official_prefix"));
        finalmsg.addExtra(msg);
        player.spigot().sendMessage(finalmsg);
    }

    public static void sendPhrase(Player player, String phrase) {
        String finalmsg = QCChat.getPhrase(phrase);
        player.sendMessage(QCChat.getPhrase("official_prefix") + finalmsg);
    }

    public static void broadcastMsg(String msg) {
        String finalmsg = Util.colour(msg);
        Bukkit.broadcastMessage(QCChat.getPhrase("official_prefix") + finalmsg);
    }

    public static void performCommand(final Player player, final String cmd) {
        final CommandSender exc = player;

        TaskChain.newChain().add(new TaskChain.GenericTask() {
            public void run() {
                Bukkit.getServer().dispatchCommand(exc, cmd);
            }
        }).execute();
    }

    public static boolean givePlayerItem(Player player, ItemStack itemStack) {
        if(player.getInventory().addItem(itemStack).equals(null)) {
            return true;
        }
        return false;
    }

    public static String removeExtraChars(String str, int length) {
        try {
            return str.substring(0, length);
        } catch(StringIndexOutOfBoundsException e) {
            //Util.printException("Failed to remove chars from string!", e);
            Util.log(Level.INFO, "Failed to remove chars from string or string was not long enough");
            return str;
        }

    }
}
