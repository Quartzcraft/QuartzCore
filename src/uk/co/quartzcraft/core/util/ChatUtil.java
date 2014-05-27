package uk.co.quartzcraft.core.util;

import org.bukkit.ChatColor;

public class ChatUtil {

    /**
     * Parses the chat colours.
     *
     * @author mba2012, SoulPunisher
     * @param message
     * @return message
     */
    public static String colour(String message) {

        //Parse Colours
        message = message.replaceAll("&0", ChatColor.BLACK + "");
        message = message.replaceAll("&1", ChatColor.DARK_BLUE + "");
        message = message.replaceAll("&2", ChatColor.DARK_GREEN + "");
        message = message.replaceAll("&3", ChatColor.DARK_AQUA + "");
        message = message.replaceAll("&4", ChatColor.DARK_RED + "");
        message = message.replaceAll("&5", ChatColor.DARK_PURPLE + "");
        message = message.replaceAll("&6", ChatColor.GOLD + "");
        message = message.replaceAll("&7", ChatColor.GRAY + "");
        message = message.replaceAll("&8", ChatColor.DARK_GRAY + "");
        message = message.replaceAll("&9", ChatColor.BLUE + "");
        message = message.replaceAll("&a", ChatColor.GREEN + "");
        message = message.replaceAll("&b", ChatColor.AQUA + "");
        message = message.replaceAll("&c", ChatColor.RED + "");
        message = message.replaceAll("&d", ChatColor.LIGHT_PURPLE + "");
        message = message.replaceAll("&e", ChatColor.YELLOW + "");
        message = message.replaceAll("&f", ChatColor.WHITE + "");

        return message;
    }

    /**
     * Parses chat formatting.
     *
     * @author mba2012
     * @param message
     * @return message
     */
    public static String format(String message) {

        //Parse Colours
        message = message.replaceAll("&k", ChatColor.MAGIC + "");
        message = message.replaceAll("&0", ChatColor.ITALIC + "");
        message = message.replaceAll("&n", ChatColor.UNDERLINE + "");
        message = message.replaceAll("&m", ChatColor.STRIKETHROUGH + "");
        message = message.replaceAll("&l", ChatColor.BOLD + "");
        message = message.replaceAll("&r", ChatColor.RESET + "");

        return message;
    }

    /**
     * Parses chat, using the format and colour parsers.
     *
     * @author mba2012
     * @param message
     * @return message
     */
    public static String parse(String message) {

        message = colour(message);
        message = format(message);

        return message;
    }
}
