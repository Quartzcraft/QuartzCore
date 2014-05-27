package uk.co.quartzcraft.core.command;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.chat.ChatPhrase;
import uk.co.quartzcraft.core.command.framework.*;
import uk.co.quartzcraft.core.features.InventoryUI;

public class CommandPromo {

    private static QuartzCore plugin;
    private static QCommandFramework framework;

    public CommandPromo(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    InventoryUI menu = new InventoryUI("Current Promotions", 9, new InventoryUI.OptionClickEventHandler() {
        @Override
        public void onOptionClick(InventoryUI.OptionClickEvent event) {
            event.getPlayer().sendMessage("You have claimed the " + event.getName() + " promo!");
            event.setWillClose(true);
        }
    }, plugin)
            .setOption(3, new ItemStack(Material.APPLE, 1), "Food", "The food is delicious")
            .setOption(4, new ItemStack(Material.IRON_SWORD, 1), "Weapon", "Weapons are for awesome people")
            .setOption(5, new ItemStack(Material.EMERALD, 1), "Money", "Money brings happiness");

    @uk.co.quartzcraft.core.command.framework.QCommand(name = "promo", permission = "QCC.promo", description = "Lists the avaliable promos.", usage = "Use /promo")
    public void promo(CommandArgs args) {
        Player player = (Player) args.getSender();
        World world = player.getWorld();
        player.sendMessage(ChatPhrase.getPhrase("you_are_currently_in_world") + ChatColor.WHITE + world.getName());
    }

}
