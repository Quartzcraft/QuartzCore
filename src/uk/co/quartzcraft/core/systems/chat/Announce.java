package uk.co.quartzcraft.core.systems.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import uk.co.quartzcraft.core.features.ActionBar;
import uk.co.quartzcraft.core.features.Title;

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
     * Sends every online player a title
     *
     * @param message
     */
    public static void announceTitle(String message) {

        message = ChatFormat.parse(message);

        if(message.length() >= 5) {
            Title.sendTitle(null, 20, 80, 40, null, message);
        } else {
            Title.sendTitle(null, 20, 80, 40, message, null);
        }


    }

    /**
     * Sends every online an action bar message
     *
     * @param message
     */
    public static void announceBar(String message) {

        message = ChatFormat.parse(message);

        ActionBar.displayBar(null, message);
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
