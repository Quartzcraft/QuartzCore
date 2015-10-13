package uk.co.quartzcraft.core.features;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Title {

    /**
     * Sends a title to the specified player if they are running 1.8
     *
     * @param player
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param title
     * @param subtitle
     */
    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
//        if (craftPlayer.getHandle().playerConnection.networkManager.getVersion() != 47)
//            return; // If using 1.8, allow method to run

        if (title == null) title = "";
        title = ChatColor.translateAlternateColorCodes('&', title);

        if (subtitle == null) subtitle = "";
        subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);

        IChatBaseComponent title2;
        IChatBaseComponent subtitle2;
        IChatBaseComponent serializedTitle = ChatSerializer.a(title);
        IChatBaseComponent serializedSubTitle = ChatSerializer.a(subtitle);
        title2 = serializedTitle;
        subtitle2 = serializedSubTitle;

        craftPlayer.getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.TIMES, fadeIn, stay, fadeOut));
        if (title != null)
            craftPlayer.getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.TITLE, title2));
        if (subtitle != null)
            craftPlayer.getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.SUBTITLE, subtitle2));
    }

    public static void sendTabTitle(Player player, String header, String footer) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
//        if (craftPlayer.getHandle().playerConnection.networkManager.getVersion() != 47)
//            return; // If using 1.8, allow method to run

        PlayerConnection connection = craftPlayer.getHandle().playerConnection;


        if (header == null) header = "";
        header = ChatColor.translateAlternateColorCodes('&', header);

        if (footer == null) footer = "";
        footer = ChatColor.translateAlternateColorCodes('&', footer);

        header = header.replaceAll("%player%", player.getDisplayName());
        footer = footer.replaceAll("%player%", player.getDisplayName());

        IChatBaseComponent header2 = ChatSerializer.a("{'color': 'white', 'text': '" + header + "'}");
        IChatBaseComponent footer2 = ChatSerializer.a("{'color': 'white', 'text': '" + footer + "'}");
        connection.sendPacket(new ProtocolInjector.PacketTabHeader(header2, footer2));
    protected static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
