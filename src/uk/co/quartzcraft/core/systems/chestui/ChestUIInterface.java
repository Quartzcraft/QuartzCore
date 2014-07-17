package uk.co.quartzcraft.core.systems.chestui;

import uk.co.quartzcraft.core.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class ChestUIInterface {
    protected Player player;
    protected Inventory inventory;

    public static String getSlotLabel(int slot) {
        return "ChestUI_Slot_" + slot;
    }

    public abstract int getSize();
    public abstract String getTitle();
    public abstract void init(Player player, Inventory inventory);

    protected int slot = 0;
    protected final Inventory initialize(Player player) {
        this.player = player;
        inventory = Bukkit.createInventory(player, getSize(), getTitle());
        init(player, inventory);
        return inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public ChestInterfaceItem add(int slot, Material material, short data) {
        return add(slot, new ItemStack(material, 1, data));
    }
    public ChestInterfaceItem add(Material material, short data) {
        return add(slot, new ItemStack(material, 1, data));
    }
    public ChestInterfaceItem add(Material material) {
        return add(slot, new ItemStack(material));
    }
    public ChestInterfaceItem add(int slot, Material material) {
        return add(slot, new ItemStack(material));
    }
    public ChestInterfaceItem add(ItemStack item) {
        return add(slot, item);
    }
    public ChestInterfaceItem add(final int slot, ItemStack item) {
        this.slot = slot+1;
        final ChestInterfaceItem ciItem = new ChestInterfaceItem(player, inventory, slot, item);
        final HashMap<String, Object> meta = inventory.getMeta();
        meta.put(getSlotLabel(slot), ciItem);
        return ciItem;
    }
}
