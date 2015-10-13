package uk.co.quartzcraft.core.command;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.features.promos.PromoMenus;
import uk.co.quartzcraft.core.command.framework.CommandArgs;
import uk.co.quartzcraft.core.command.framework.QCommand;

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
