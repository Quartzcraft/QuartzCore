package uk.co.quartzcraft.core.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.command.framework.CommandArgs;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.features.ActionBar;
import uk.co.quartzcraft.core.features.FancyMessages;
import uk.co.quartzcraft.core.systems.fancymessage.FancyMessage;
import uk.co.quartzcraft.core.systems.notifications.Alert;

import java.util.HashMap;
import java.util.Iterator;

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
        Player player = args.getPlayer();
        QPlayer qPlayer = new QPlayer(player);
        CommandSender sender = args.getSender();

        Alert[] alerts = qPlayer.getAlerts();

        for (int i = 0; i < alerts.length; i++) {
            Alert alert = alerts[i];
            qPlayer.alert(alert);
        }
    }
}
