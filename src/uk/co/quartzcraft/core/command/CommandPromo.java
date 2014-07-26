package uk.co.quartzcraft.core.command;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.command.framework.*;
import uk.co.quartzcraft.core.features.PromoMenus;

public class CommandPromo {

    private static Plugin plugin;
    private static QCommandFramework framework;

    public CommandPromo(Plugin plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    @uk.co.quartzcraft.core.command.framework.QCommand(name = "promo", permission = "QCC.promo", description = "Lists the available promos.", usage = "Use /promo")
    public void promo(CommandArgs args) {
        PromoMenus.menuTest.open((Player) args.getSender());
    }

}
