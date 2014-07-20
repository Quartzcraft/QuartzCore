package uk.co.quartzcraft.core.features;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.systems.ChestUI.ChestUI;

public class PromoMenus {

    private static QuartzCore plugin = new QuartzCore();

    public static ChestUI menuLaunch = new ChestUI("Current Promotion: QuartzCraft Launch", 9, new ChestUI.OptionClickEventHandler() {
        @Override
        public void onOptionClick(ChestUI.OptionClickEvent event) {
            event.getPlayer().sendMessage("You have claimed the " + event.getName() + " promo!");
            event.setWillClose(true);
        }
    }, plugin)
            .setOption(3, PromoItems.MAGIC_DIRT)
            .setOption(4, new ItemStack(Material.IRON_SWORD, 1), "Weapon", "Weapons are for awesome people")
            .setOption(5, new ItemStack(Material.EMERALD, 1), "Money", "Money brings happiness");
}
