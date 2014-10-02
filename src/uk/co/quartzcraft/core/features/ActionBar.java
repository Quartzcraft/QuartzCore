package uk.co.quartzcraft.core.features;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar {

    public static void displayBar(Player p, String message) {
        IChatBaseComponent barmsg = ChatSerializer.a(message);
        PacketPlayOutChat bar = new PacketPlayOutChat(barmsg, 2);

        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
    }
}
