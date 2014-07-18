package uk.co.quartzcraft.core.systems;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QCChat {

    public static void performCommand(Player player, String cmd) {
        CommandSender exc = player;

        Bukkit.getServer().dispatchCommand(exc, cmd);
    }
}
