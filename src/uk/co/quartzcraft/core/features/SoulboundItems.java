package uk.co.quartzcraft.core.features;

import org.bukkit.event.Listener;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.util.ItemUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class SoulboundItems implements Listener {
    public static final String SOULBOUND_KEY = "__soulbound";
    private static QuartzCore plugin;

    public SoulboundItems(QuartzCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    public static ItemStack makeSoulbound(ItemStack item) {
        if (item == null) {
            throw new NullPointerException();
        }
        return makeSoulbound(ItemUtil.buildLore(item)).save();
    }
    public static ItemUtil.LoreBuilder makeSoulbound(ItemUtil.LoreBuilder builder) {
        return builder
                .add(0, "&6&l&nSoulbound&f (&oWill not drop on death)")
                .setMeta(SOULBOUND_KEY, "1");
    }

    public static boolean isSoulbound(ItemStack item) {
        return ItemUtil.hasMeta(item, SOULBOUND_KEY);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        final List<ItemStack> drops = event.getDrops();
        final List<ItemStack> itemsToKeep = null;//event.getItemsToKeep(); //TODO: FIX THIS!
        final Iterator<ItemStack> iterator = drops.iterator();
        while (iterator.hasNext()) {
            ItemStack item = iterator.next();
            if (isSoulbound(item)) {
                iterator.remove();
                itemsToKeep.add(item);
            }
        }

    }
}
