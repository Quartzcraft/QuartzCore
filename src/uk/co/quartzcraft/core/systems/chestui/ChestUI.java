package uk.co.quartzcraft.core.systems.chestui;


import com.empireminecraft.util.BukkitUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import uk.co.quartzcraft.core.util.Util;

import java.util.HashMap;

public class ChestUI extends BukkitUtil.Listener {
    public static void initialize() {
        new ChestUI();
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        final Inventory inventory = event.getInventory();
        final HashMap<String, Object> meta = inventory.getMeta();
        if (!meta.containsKey("ChestUI")) {
            return;
        }
        event.setCancelled(true);

        if (event.getAction() != InventoryAction.PICKUP_ALL) {
            return;
        }
        if (event.getClickedInventory() != event.getView().getTopInventory()) {
            return;
        }
        final Object o = meta.get(ChestUIInterface.getSlotLabel(event.getSlot()));
        if (o instanceof ChestInterfaceItem) {
            ((ChestInterfaceItem) o).processClick();
        }
    }
    public static void open(Player player, Class <? extends ChestUIInterface> ui) {
        try {
            open(player, ui.newInstance());
        } catch (Exception e) {
            Util.printException(e);
        }
    }
    public static void open(Player player, ChestUIInterface ui) {
        try {
            final Inventory inventory = ui.initialize(player);
            inventory.getMeta().put("ChestUI", 1);
            player.openInventory(inventory);
        } catch (Exception e) {
            Util.printException(e);
        }
    }
}