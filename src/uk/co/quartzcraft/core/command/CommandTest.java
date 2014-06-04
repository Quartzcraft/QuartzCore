package uk.co.quartzcraft.core.command;

import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.*;

public class CommandTest {

    private static QuartzCore plugin;
    private static QCommandFramework framework;

    public CommandTest(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    @QCommand(name = "test", aliases = { "testing" }, permission = "QCC.test", description = "This is a test command", usage = "This is how you use it")
    public void test(CommandArgs args) {
        args.getSender().sendMessage("This is a test command");
    }
}
