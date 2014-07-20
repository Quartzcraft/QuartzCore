package uk.co.quartzcraft.core.features;

import uk.co.quartzcraft.core.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
}
