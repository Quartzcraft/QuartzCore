package uk.co.quartzcraft.core.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryUI {

    public InventoryUI(Player player, String name, Integer numSlots) {

        Inventory inv = Bukkit.createInventory(player, numSlots, name);

        player.openInventory(inv);
    }
}
