package uk.co.quartzcraft.core.systems.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Announce {

    /**
     * Makes a broadcast
     *
     * @param message
     */
    public static void announce(String message) {

        message = ChatFormat.parse(message);

        Bukkit.getServer().broadcastMessage(message);
    }

    /**
     * Makes a broadcast with a prefix.
     *
     * @param prefix
     * @param prefixColour
     * @param message
     */
    public static void announceWithPrefix(String prefix, String prefixColour, String message) {

        //Parse Message
        message = ChatFormat.parse(message);

        //Parse Prefix
        prefix = ChatFormat.parse(prefix);

        //Parse Prefix Colour
        prefixColour = ChatFormat.parse(prefixColour);

        Bukkit.getServer().broadcastMessage(prefixColour + "[" + prefix + prefixColour + "] " + ChatColor.RESET + message);
    }
}
