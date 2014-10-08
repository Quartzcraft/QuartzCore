package uk.co.quartzcraft.core.features;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector;

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
        if (craftPlayer.getHandle().playerConnection.networkManager.getVersion() != 47)
            return; // If using 1.8, allow method to run

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
        if (craftPlayer.getHandle().playerConnection.networkManager.getVersion() != 47)
            return; // If using 1.8, allow method to run

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
    }
}
