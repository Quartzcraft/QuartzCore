package uk.co.quartzcraft.core.command;

import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.features.bountifulapi.BountifulAPI;
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
        BountifulAPI.sendActionBar((Player) args.getSender(), "yay");

        new AlertBuilder().setType("QC").displayPrefix(true).setMessage("This is a test alert! :D").setReceiver(new QPlayer(args.getPlayer())).send();
    }
}
