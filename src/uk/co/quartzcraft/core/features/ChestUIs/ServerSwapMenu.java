package uk.co.quartzcraft.core.features.ChestUIs;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.features.PromoItems;
import uk.co.quartzcraft.core.systems.ChestUI.ChestUI;
import uk.co.quartzcraft.core.systems.ChestUI.UnclaimableItem;
import uk.co.quartzcraft.core.util.ItemUtil;

public class ServerSwapMenu {

    private static Plugin plugin = QuartzCore.plugin;

    public static final ItemStack SWAP_INSTRUCTIONS = new ItemStack(Material.PAPER) {{
        ItemUtil.buildLore(this).setName("&aInstructions")
                .add("&bTo select what server you want")
                .add("&bclick on the item allocated to it.")
                .save();
    }};

    public static ChestUI menuServerSwap = new ChestUI("Choose a server", 9, new ChestUI.OptionClickEventHandler() {
        @Override
        public void onOptionClick(ChestUI.OptionClickEvent event) {
            event.getPlayer().sendMessage("You have picked the server " + event.getItem().getItemMeta().getDisplayName());
        }
    }, plugin)
            .setOption(0, SWAP_INSTRUCTIONS)
            .setOption(2, new ItemStack(Material.QUARTZ, 1), "Hub", "Move to the QuartzCraft hub")
            .setOption(3, new ItemStack(Material.IRON_SWORD, 1), "Kingdoms", "Move to the Kingdoms server");
}
