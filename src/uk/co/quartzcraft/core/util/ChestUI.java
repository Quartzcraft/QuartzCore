package uk.co.quartzcraft.core.util;

import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

public class ChestUI {

    Chest chest;

    public ChestUI(Player player) {
       player.openInventory(chest.getInventory());
    }
}
