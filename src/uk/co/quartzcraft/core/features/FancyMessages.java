package uk.co.quartzcraft.core.features;

import static org.bukkit.ChatColor.*;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.systems.fancymessage.FancyMessage;

public class FancyMessages {

    public static String welcomeBack(Player player) {
        return new FancyMessage("Welcome back, ")
                .color(RED)
                .then(player.getDisplayName())
                .then("!")
                .color(RED)
                .toJSONString();
    }

    public static String checkWebsite(Player player) {
        return new FancyMessage("Check out our website!")
                .link("http://quartzcraft.co.uk")
                .style(UNDERLINE)
                .color(GOLD)
                .toJSONString();
    }

    public static String reportedPlayer() {
        return new FancyMessage(QCChat.getPhrase("thank_you_for_reporting_user"))
                .color(GREEN)
                .toJSONString();
    }

    public static String hasUnreadAlerts(Player player) {
        QPlayer qPlayer = new QPlayer(player);
        if(qPlayer.getUnreadAlertCount() != 0) {
            return new FancyMessage("You have ")
                    .color(GREEN)
                    .then(qPlayer.getUnreadAlertCount() + " unread alerts!")
                    .command("/alerts")
                    .style(UNDERLINE)
                    .color(RED)
                    .toJSONString();
        }

        return new FancyMessage("You have no unread alerts!")
                .color(GREEN)
                .style(UNDERLINE)
                .toJSONString();
    }

    public static void advertisement(Player player) {
        new FancyMessage("Visit ")
                .color(GREEN)
                .then("our website")
                .color(YELLOW)
                .style(UNDERLINE)
                .link("http://awesome-server.net")
                .tooltip("AwesomeServer Forums")
                .then(" to win ")
                .color(GREEN)
                .then("big prizes!")
                .color(AQUA)
                .style(BOLD)
                .tooltip("Terms and conditions may apply. Offer not valid in Sweden.")
                .send(player);
    }

    public static void gui(Player player, int blocksEdited) {
        new FancyMessage("Player ")
                .color(DARK_RED)
                .then(player.getName())
                .color(RED)
                .style(ITALIC)
                .then(" changed ").color(DARK_RED)
                .then(Integer.toString(blocksEdited)).color(AQUA)
                .then(" blocks. ").color(DARK_RED)
                .then("Roll back?")
                .color(GOLD)
                .style(UNDERLINE)
                .suggest("/rollenbacken " + player.getName())
                .tooltip("Be careful, this might undo legitimate edits!")
                .then(" ")
                .then("Ban?")
                .color(RED)
                .style(UNDERLINE)
                .suggest("/banhammer " + player.getName())
                .tooltip("Remember: only ban if you have photographic evidence of grief.")
                .send(player);
    }

}
