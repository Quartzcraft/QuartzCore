package uk.co.quartzcraft.core.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.command.framework.CommandArgs;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.util.ChatPhrase;

public class CommandPinfo {
    private static QuartzCore plugin;
    private static QCommandFramework framework;

    public CommandPinfo(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    @QCommand(name = "pinfo", aliases = { "p" }, permission = "QCC.pinfo", description = "Displays info on the specifed player", usage = "Use /pinfo [playername]")
    public void pinfo(CommandArgs args) {
        if(args.getArgs().length >= 1) {
            String[] arg1 = args.getArgs();

            args.getSender().sendMessage("Pinfo performed successfully on player: " + arg1[0]);
        }
    }
}
