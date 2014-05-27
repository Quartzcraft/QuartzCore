package uk.co.quartzcraft.core.features;

import org.bukkit.inventory.ItemStack;
import uk.co.quartzcraft.core.util.ItemUtil;

public class UnbreakableItems {
    public static final String UNBREAKING_KEY = "__unbreak";

    public static ItemStack makeUnbreaking(ItemStack item) {
        if (item == null) {
            throw new NullPointerException();
        }
        return makeUnbreaking(ItemUtil.buildLore(item)).save();
    }
    public static ItemUtil.LoreBuilder makeUnbreaking(ItemUtil.LoreBuilder builder) {
        return builder
                .add(0, "&6&l&nUnbreaking&f (&oCan not be damaged)")
                .setMeta(UNBREAKING_KEY, "1");
    }

    public static boolean isUnbreakable(ItemStack item) {
        return ItemUtil.hasMeta(item, UNBREAKING_KEY);
    }

    //TODO: Finish this
}
