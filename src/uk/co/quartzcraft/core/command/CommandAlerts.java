package uk.co.quartzcraft.core.command;

import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.command.framework.CommandArgs;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.features.ActionBar;
import uk.co.quartzcraft.core.features.FancyMessages;
import uk.co.quartzcraft.core.systems.fancymessage.FancyMessage;

public class CommandAlerts {

    private static QuartzCore plugin;
    private static QCommandFramework framework;

    public CommandAlerts(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    @QCommand(name = "alerts", permission = "QCC.alerts", description = "Check all your recent alerts", usage = "/alerts")
    public void alerts(CommandArgs args) {

    }
}
