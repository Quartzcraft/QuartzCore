package uk.co.quartzcraft.core.features;

import static org.bukkit.ChatColor.*;
import uk.co.quartzcraft.core.systems.fancymessage.FancyMessage;

public class FancyMessages {

    public static String welcome(String playername) {
        return new FancyMessage("Hello, ")
                .color(YELLOW)
                .then(playername)
                .color(LIGHT_PURPLE)
                .style(ITALIC, UNDERLINE)
                .then("!")
                .color(YELLOW)
                .style(ITALIC)
                .toJSONString();
    }

    public static String advertisement() {
        return new FancyMessage("Visit ")
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
                .toJSONString();
    }

    public static String gui(String playername, int blocksEdited) {
        return new FancyMessage("Player ")
                .color(DARK_RED)
                .then(playername)
                .color(RED)
                .style(ITALIC)
                .then(" changed ").color(DARK_RED)
                .then(Integer.toString(blocksEdited)).color(AQUA)
                .then(" blocks. ").color(DARK_RED)
                .then("Roll back?")
                .color(GOLD)
                .style(UNDERLINE)
                .suggest("/rollenbacken " + playername)
                .tooltip("Be careful, this might undo legitimate edits!")
                .then(" ")
                .then("Ban?")
                .color(RED)
                .style(UNDERLINE)
                .suggest("/banhammer " + playername)
                .tooltip("Remember: only ban if you have photographic evidence of grief.")
                .toJSONString();
    }

}
