package uk.co.quartzcraft.core.util;

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
        QuartzCore.log.log(Level.WARNING, "[QC] " + message);
        e.printStackTrace();
    }

    public static void printException(Exception e) {
        QuartzCore.log.log(Level.WARNING, "[QC]An exception occurred!");
        e.printStackTrace();
    }

    public static void log(Level level, String message) {
        QuartzCore.log.log(level, message);
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
        player.sendMessage(finalmsg);
    }

    public static void sendPhrase(Player player, String phrase) {
        String finalmsg = QCChat.getPhrase(phrase);
        player.sendMessage(finalmsg);
    }

    public static void performCommand(Player player, String cmd) {
        CommandSender exc = player;

        Bukkit.getServer().dispatchCommand(exc, cmd);
    }

    public static boolean givePlayerItem(Player player, ItemStack itemStack) {
        if(player.getInventory().addItem(itemStack).equals(null)) {
            return true;
        }
        return false;
    }
}
