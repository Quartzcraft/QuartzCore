package uk.co.quartzcraft.core.features;

import org.bukkit.inventory.ItemStack;
import uk.co.quartzcraft.core.util.ItemUtil;

public class FinalItems {
    public static final String FINAL_KEY = "__final";

    public static ItemStack makeFinal(ItemStack item) {
        if (item == null) {
            throw new NullPointerException();
        }
        return makeFinal(ItemUtil.buildLore(item)).save();
    }
    public static ItemUtil.LoreBuilder makeFinal(ItemUtil.LoreBuilder builder) {
        return builder
                .add(0, "&6&l&nFinal&f (&oCan not be modified)")
                .setMeta(FINAL_KEY, "1");
    }

    public static boolean isFinal(ItemStack item) {
        return ItemUtil.hasMeta(item, FINAL_KEY);
    }

    //TODO: finish This!
}
