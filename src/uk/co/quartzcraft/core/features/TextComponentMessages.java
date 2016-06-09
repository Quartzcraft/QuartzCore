package uk.co.quartzcraft.core.features;

import static org.bukkit.ChatColor.*;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.systems.chat.QCChat;

public class TextComponentMessages {

    public static BaseComponent[] welcomeBack(Player player) {
        return new ComponentBuilder("Welcome back, ")
                .color(ChatColor.RED)
                .append(player.getDisplayName())
                .append("!")
                .color(ChatColor.RED)
                .create();
    }

    public static BaseComponent[] checkWebsite(Player player) {
        return new ComponentBuilder("Check out our website!")
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://quartzcraft.co.uk"))
                .underlined(true)
                .color(ChatColor.GOLD)
                .create();
    }

    public static BaseComponent[] hasUnreadAlerts(Player player) {
        QPlayer qPlayer = new QPlayer(player);
        if(qPlayer.getUnreadAlertCount() != 0) {
            return new ComponentBuilder("You have ")
                    .color(ChatColor.GREEN)
                    .append(qPlayer.getUnreadAlertCount() + " unread alerts!")
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "alerts"))
                    .underlined(true)
                    .color(ChatColor.RED)
                    .create();
        }

        return new ComponentBuilder("You have no unread alerts!")
                .color(ChatColor.GREEN)
                .create();
    }
}
