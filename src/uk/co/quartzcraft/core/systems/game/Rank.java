package uk.co.quartzcraft.core.systems.game;

import org.bukkit.ChatColor;

public class Rank {

    private String prefix;
    private ChatColor colour;

    public String getStyleForName() {
        String p = "[" + this.prefix + "]";
        if(this.prefix == null) {
            return "";
        } else if(this.colour != null) {
            return this.colour + p + ChatColor.RESET;
        } else {
            return p + ChatColor.RESET;
        }
    }

}
