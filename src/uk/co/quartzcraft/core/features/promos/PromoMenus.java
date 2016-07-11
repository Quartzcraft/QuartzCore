package uk.co.quartzcraft.core.features.promos;

import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.systems.ChestUI.ChestUI;
import uk.co.quartzcraft.core.systems.ChestUI.UnClaimableItem;
import uk.co.quartzcraft.core.util.ItemUtil;
import uk.co.quartzcraft.core.util.Util;

public class PromoMenus {

    private static Plugin plugin = QuartzCore.plugin;

    public static ChestUI menuTest = new ChestUI("Current Promo: Test", 9, new ChestUI.OptionClickEventHandler() {
        @Override
        public void onOptionClick(ChestUI.OptionClickEvent event) {
            if(UnClaimableItem.isUnclaimable(event.getItem())) {
                Util.sendMsg(event.getPlayer(), "You can not claim this item!");
                event.setCancelled(true);
            } else {
                event.getPlayer().getInventory().addItem(event.getItem());
                Util.sendMsg(event.getPlayer(), "You have claimed the " + ItemUtil.getName(event.getItem()) + " promo!");
                event.setWillClose(true);
                event.setWillDestroy(true);
            }
        }
    }, plugin)
            .setOption(1, PromoItems.PROMO_INSTRUCTIONS)
            .setOption(3, PromoItems.MAGIC_DIRT)
            .setOption(4, PromoItems.JAKE_CAKE);
}
