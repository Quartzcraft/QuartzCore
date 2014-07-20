package uk.co.quartzcraft.core.systems.ChestUI;

import org.bukkit.inventory.ItemStack;
import uk.co.quartzcraft.core.util.ItemUtil;

public class UnclaimableItem {

    public static final String NOCLAIMING_KEY = "__noclaim";

    public static ItemStack makeUnclaimable(ItemStack item) {
        if (item == null) {
            throw new NullPointerException();
        }
        return makeUnclaimable(ItemUtil.buildLore(item)).save();
    }

    public static ItemUtil.LoreBuilder makeUnclaimable(ItemUtil.LoreBuilder builder) {
        return builder
                .add(0, "&6&l&nUnclaimable&f (&oCan not be claimed)")
                .setMeta(NOCLAIMING_KEY, "1");
    }

    public static boolean isUnclaimable(ItemStack item) {
        return ItemUtil.hasMeta(item, NOCLAIMING_KEY);
    }
}
