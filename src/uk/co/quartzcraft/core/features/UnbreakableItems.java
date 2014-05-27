package uk.co.quartzcraft.core.features;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.util.ItemUtil;

public class UnbreakableItems implements Listener {

    private static QuartzCore plugin;

    public UnbreakableItems(QuartzCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    public static final String UNBREAKING_KEY = "__unbreak";

    public static ItemStack makeUnbreaking(ItemStack item) {
        if (item == null) {
            throw new NullPointerException();
        }
        return makeUnbreaking(ItemUtil.buildLore(item)).save();
    }
    public static ItemUtil.LoreBuilder makeUnbreaking(ItemUtil.LoreBuilder builder) {
        return builder
                .add(0, "&6&l&nUnbreaking&f (&oCan not be damaged)")
                .setMeta(UNBREAKING_KEY, "1");
    }

    public static boolean isUnbreakable(ItemStack item) {
        return ItemUtil.hasMeta(item, UNBREAKING_KEY);
    }

    // Items Indestructibles
    @EventHandler
    public void noWeaponBreakDamage( EntityDamageByEntityEvent event){
        if( event.getDamager() instanceof Player) {
            ((Player) event.getDamager()).getItemInHand().setDurability((short) 1);
        } else if ( event.getEntity() instanceof Player ){
            ItemStack[] armor = ((Player) event.getEntity()).getInventory().getArmorContents();
            for(ItemStack i : armor) {
                i.setDurability((short) 0);
            }
            ((Player) event.getEntity()).getInventory().setArmorContents(armor);
        }
    }
    @EventHandler
    public void noWeaponBreakDamage( EntityShootBowEvent event){
        // Bow
        if( event.getEntity() instanceof Player )
            event.getBow().setDurability((short) 1);
    }
    @EventHandler
    public void noWeaponBreakDamage( PlayerItemBreakEvent event){
        ItemStack item = event.getBrokenItem().clone();
        item.setDurability((short)0);
        event.getPlayer().getInventory().addItem(item);
    }
}
