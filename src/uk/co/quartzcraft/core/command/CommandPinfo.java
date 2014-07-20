package uk.co.quartzcraft.core.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.command.framework.CommandArgs;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.entity.QPlayer;
import uk.co.quartzcraft.core.util.ChatPhrase;

public class CommandPinfo {
    private static QuartzCore plugin;
    private static QCommandFramework framework;

    public CommandPinfo(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    //TODO Finish/fix up
    @QCommand(name = "pinfo", aliases = { "p" }, permission = "QCC.pinfo", description = "Displays info on the specifed player", usage = "Use /pinfo [playername]")
    public void pinfo(CommandArgs args) {
        if(args.getArgs().length >= 1) {
            String[] arg1 = args.getArgs();
            Player pplayer = Bukkit.getPlayer(arg1[0]);
            QPlayer player = new QPlayer(this.plugin, pplayer.getUniqueId());

            args.getSender().sendMessage(ChatPhrase.getPhrase("information_on_player_X") + arg1[0]);
            args.getSender().sendMessage(ChatPhrase.getPhrase("first_join") + player.getFirstJoin());
            args.getSender().sendMessage(ChatPhrase.getPhrase("last_seen") + player.getLastSeen());
        } else {
            args.getSender().sendMessage(ChatPhrase.getPhrase("specify_username"));
        }
    }
}