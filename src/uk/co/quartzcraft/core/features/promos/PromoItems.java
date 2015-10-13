package uk.co.quartzcraft.core.features.promos;

import uk.co.quartzcraft.core.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PromoItems extends ItemStack {

    public static final ItemStack PROMO_INSTRUCTIONS = new ItemStack(Material.PAPER) {{
        ItemUtil.buildLore(this).setName("&aMagic Dirt")
                .makeUnclaimable()
                .empty()
                .add("&bTo select what promo you want")
                .add("&bclick on the item.")
                .add("&bYou can not claim this item!")
                .save();
    }};

    public static final ItemStack MAGIC_DIRT = new ItemStack(Material.DIRT) {{
        ItemUtil.buildLore(this).setName("&aMagic Dirt")
                .add("&bThis is a magical item")
                .add("&bdesigned to test /promo")
                .save();
    }};

    public static final ItemStack JAKE_CAKE = new ItemStack(Material.CAKE) {{
        ItemUtil.buildLore(this).setName("&6Jake's Birthday Cake")
                .add("&bTest promo for")
                .add("&bJake's birthday! :D")
                .save();
    }};
}
