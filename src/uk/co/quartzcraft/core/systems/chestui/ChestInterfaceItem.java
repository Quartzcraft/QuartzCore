package uk.co.quartzcraft.core.systems.chestui;

import com.empireminecraft.systems.EmpireChat;
import com.empireminecraft.util.BukkitUtil;
import uk.co.quartzcraft.core.util.ItemUtil;
import com.google.common.base.Predicate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class ChestInterfaceItem extends ItemUtil.LoreBuilder {
    private Player player;
    private Inventory inventory;
    private int slot;
    private Predicate<Player> onClick = null;

    /**
     * Initiates a builder and sets the index to the first empty line after the lore
     *
     * @param item
     */
    public ChestInterfaceItem(Player player, Inventory inventory, int slot, ItemStack item) {
        super(item);
        this.player = player;
        this.inventory = inventory;
        this.slot = slot;
    }
    public ItemStack save() {
        ItemStack saved = super.save();
        inventory.setItem(slot, saved);
        return saved;
    }
    public ChestInterfaceItem onClick(Predicate<Player> run) {
        this.onClick = run;
        return this;
    }
    public ChestInterfaceItem onClick(final String cmd) {
        this.onClick = new Predicate<Player>() {
            @Override
            public boolean apply(@Nullable Player input) {
                EmpireChat.performCommand(player, cmd);
                return true;
            }
        };
        return this;
    }

    protected final void processClick() {
        if (onClick != null) {
            player.closeInventory();
            BukkitUtil.runTaskNextTick(new Runnable() {
                @Override
                public void run() {
                    onClick.apply(player);
                }
            });
        }
    }
}
