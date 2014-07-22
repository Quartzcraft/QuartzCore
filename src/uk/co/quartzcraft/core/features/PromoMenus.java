package uk.co.quartzcraft.core.features;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.systems.ChestUI.ChestUI;
import uk.co.quartzcraft.core.systems.ChestUI.UnclaimableItem;
import uk.co.quartzcraft.core.util.ItemUtil;

public class PromoMenus {

    private static Plugin plugin = QuartzCore.plugin;

    public static ChestUI menuLaunch = new ChestUI("Current Promo: Server Launch", 9, new ChestUI.OptionClickEventHandler() {
        @Override
        public void onOptionClick(ChestUI.OptionClickEvent event) {
            if(UnclaimableItem.isUnclaimable(event.getItem())) {
                event.getPlayer().sendMessage("You can not claim this item!");
                event.setCancelled(true);
            } else {
                event.getPlayer().getInventory().addItem(PromoItems.MAGIC_DIRT);
                event.getPlayer().sendMessage("You have claimed the " + ItemUtil.getName(event.getItem()) + " promo!");
                event.setWillClose(true);
                event.setWillDestroy(true);
            }
        }
    }, plugin)
            .setOption(1, PromoItems.PROMO_INSTRUCTIONS)
            .setOption(3, PromoItems.MAGIC_DIRT);
}
