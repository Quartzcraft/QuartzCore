package uk.co.quartzcraft.core.systems.chestui;

import uk.co.quartzcraft.core.util.ItemUtil;
import uk.co.quartzcraft.core.util.Util;
import com.google.common.base.Predicate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

        import javax.annotation.Nullable;

public class ConfirmChestInterface extends ChestUIInterface {

    private final String confirmTitle;
    private final Predicate<Player> onConfirm;
    private final Predicate<Player> onCancel;
    private final String[] lines;


    public ConfirmChestInterface(String confirmTitle, Predicate<Player> onConfirm, String... lines) {
        this(confirmTitle, onConfirm, null, lines);
    }

    public ConfirmChestInterface(
            String confirmTitle, Predicate<Player> onConfirm, Predicate<Player> onCancel, String... lines) {
        this.confirmTitle = confirmTitle;
        this.onConfirm = onConfirm;
        this.onCancel = onCancel;
        this.lines = lines;
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public String getTitle() {
        return ChatColor.stripColor(Util.colour(confirmTitle));
    }

    @Override
    public void init(final Player player, Inventory inventory) {
        final ItemUtil.LoreBuilder builder = add(Material.PAPER)
                .setName(confirmTitle);

        if (lines != null) {
            for (String line : lines) {
                builder.add(line);
            }
        }

        builder
                .empty()
                .add("&eClick on the &2Green Wool&e to confirm.")
                .add("&eClick on the &4Red Wool&e to cancel.")
                .save();

        add(3, Material.WOOL, (short) 13)
                .onClick(new Predicate<Player>() {
                    @Override
                    public boolean apply(@Nullable Player input) {
                        player.closeInventory();
                        return onConfirm.apply(input);
                    }
                })
                .setName("&2Confirm")
                .add("&2Click to Confirm")
                .save();

        add(5, Material.WOOL, (short) 14)
                .onClick(new Predicate<Player>() {
                    @Override
                    public boolean apply(@Nullable Player input) {
                        player.closeInventory();
                        return onCancel == null || onCancel.apply(input);
                    }
                })
                .setName("&4Cancel")
                .add("&4Click to Cancel")
                .save();

    }
}
