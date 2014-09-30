package uk.co.quartzcraft.core.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.command.framework.CommandArgs;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.systems.chat.QCChat;

public class CommandPinfo {
    private static QuartzCore plugin;
    private static QCommandFramework framework;

    public CommandPinfo(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    @QCommand(name = "pinfo", aliases = { "p" }, permission = "QCC.pinfo", description = "Displays info on the specified player", usage = "Use /pinfo [playername]")
    public void pinfo(CommandArgs args) {
        if(args.getArgs().length >= 1) {
            String[] arg1 = args.getArgs();
            QPlayer target = new QPlayer(arg1[0]);

            args.getSender().sendMessage(QCChat.getPhrase("information_on_player_X") + arg1[0]);
            args.getSender().sendMessage(QCChat.getPhrase("group") + target.getGroup().getFancyName());
            args.getSender().sendMessage(QCChat.getPhrase("first_join") + target.getFirstJoin() + " ago");
            args.getSender().sendMessage(QCChat.getPhrase("last_seen") + target.getLastSeen());
            //TODO add extra fields from other plugins such as kingdoms
        } else {
            args.getSender().sendMessage(QCChat.getPhrase("specify_username"));
        }
    }
}
