package uk.co.quartzcraft.core.command;

import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.features.ActionBar;
import uk.co.quartzcraft.core.features.FancyMessages;
import uk.co.quartzcraft.core.systems.fancymessage.FancyMessage;
import uk.co.quartzcraft.core.systems.notifications.AlertBuilder;
import uk.co.quartzcraft.core.command.framework.CommandArgs;

public class CommandTest {

    private static QuartzCore plugin;
    private static QCommandFramework framework;

    public CommandTest(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    @QCommand(name = "test", aliases = { "testing" }, permission = "QCC.test", description = "This is a test command", usage = "This is how you use it", requirePlayer = false)
    public void test(CommandArgs args) {
        args.getSender().sendMessage("This is a test command");
        FancyMessages.gui(args.getPlayer(), 5);
        ActionBar.displayBar((Player) args.getSender(), new FancyMessage("This is a test ActionBar thingy! :D").toJSONString());
        args.getSender().sendMessage("The server name is: " + QuartzCore.getServerName());

        new AlertBuilder().setType("QC").displayPrefix(true).setMessage("This is a test alert! :D").setReceiver(new QPlayer(args.getPlayer())).send();
    }
}
