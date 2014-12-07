package uk.co.quartzcraft.core.features.promos;

import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.features.promos.PromoItems;
import uk.co.quartzcraft.core.systems.ChestUI.ChestUI;
import uk.co.quartzcraft.core.systems.ChestUI.UnclaimableItem;
import uk.co.quartzcraft.core.util.ItemUtil;

public class PromoMenus {

    private static Plugin plugin = QuartzCore.plugin;

    public static ChestUI menuTest = new ChestUI("Current Promo: Test", 9, new ChestUI.OptionClickEventHandler() {
        @Override
        public void onOptionClick(ChestUI.OptionClickEvent event) {
            if(UnclaimableItem.isUnclaimable(event.getItem())) {
                event.getPlayer().sendMessage("You can not claim this item!");
                event.setCancelled(true);
            } else {
                event.getPlayer().getInventory().addItem(event.getItem());
                event.getPlayer().sendMessage("You have claimed the " + ItemUtil.getName(event.getItem()) + " promo!");
                event.setWillClose(true);
                event.setWillDestroy(true);
            }
        }
    }, plugin)
            .setOption(1, PromoItems.PROMO_INSTRUCTIONS)
            .setOption(3, PromoItems.MAGIC_DIRT)
            .setOption(4, PromoItems.JAKE_CAKE);
}
