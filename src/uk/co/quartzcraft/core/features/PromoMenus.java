package uk.co.quartzcraft.core.features;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.systems.ChestUI.ChestUI;
import uk.co.quartzcraft.core.systems.ChestUI.UnclaimableItem;

public class PromoMenus {

    private static Plugin plugin = QuartzCore.plugin;

    public static ChestUI menuLaunch = new ChestUI("Current Promotion: QuartzCraft Launch", 9, new ChestUI.OptionClickEventHandler() {
        @Override
        public void onOptionClick(ChestUI.OptionClickEvent event) {
            event.getPlayer().sendMessage("You have claimed the " + event.getName() + " promo!");
            if(UnclaimableItem.isUnclaimable(event.getItem())) {
                event.getPlayer().sendMessage("You can not claim this item!");
                event.setCancelled(true);
            } else {
                event.getPlayer().sendMessage("You have claimed the " + event.getName() + " promo!");
                event.setWillClose(true);
                event.setWillDestroy(true);
            }
        }
    }, plugin)
            .setOption(1, PromoItems.PROMO_INSTRUCTIONS)
            .setOption(3, PromoItems.MAGIC_DIRT)
            .setOption(4, new ItemStack(Material.IRON_SWORD, 1), "Weapon", "Weapons are for awesome people")
            .setOption(5, new ItemStack(Material.EMERALD, 1), "Money", "Money brings happiness");
}
