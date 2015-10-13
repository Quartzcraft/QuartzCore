package uk.co.quartzcraft.core.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.command.framework.CommandArgs;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.event.PInfoExtraFieldsEvent;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.util.Util;

import java.util.List;

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
        if(!(args.getSender() instanceof Player)){
            args.getSender().sendMessage(QCChat.getPhrase("player_use_only"));
            return;
        }

        if(args.getArgs().length == 1) {
            String[] arg1 = args.getArgs();
            QPlayer target = new QPlayer(arg1[0]);
            PInfoExtraFieldsEvent event = new PInfoExtraFieldsEvent(args.getSender(), target);
            Bukkit.getServer().getPluginManager().callEvent(event);

            Util.sendMsg(args.getPlayer(), QCChat.getPhrase("information_on_player_X") + target.getFancyName());
            Util.sendMsg(args.getPlayer(), QCChat.getPhrase("group") + target.getGroup().getFancyName());
            Util.sendMsg(args.getPlayer(), QCChat.getPhrase("first_join") + target.getFirstJoin() + " ago");
            if(target.isOnline()) {
                Util.sendMsg(args.getPlayer(), target.getFancyName() + " " + QCChat.getPhrase("is_online_now_on_server") + " " + target.getLastSeenServer()); //TODO Make this change for different servers and to specify activities and location
            } else {
                Util.sendMsg(args.getPlayer(), QCChat.getPhrase("last_seen") + target.getLastSeen() + " on server &r" + target.getLastSeenServer());
            }

            List<String> fields = event.getFields();
            for(int i = 0; i < fields.size(); i++) {
                args.getSender().sendMessage(fields.get(i));
            }
        } else {
            Util.sendMsg(args.getPlayer(), QCChat.getPhrase("specify_username"));
        }
    }
}
