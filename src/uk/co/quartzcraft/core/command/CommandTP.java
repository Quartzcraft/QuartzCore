package uk.co.quartzcraft.core.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.command.framework.CommandArgs;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.util.Util;

public class CommandTP {

    private static QuartzCore plugin;
    private static QCommandFramework framework;

    public CommandTP(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    @QCommand(name = "go", permission = "QCC.tp.go", description = "Teleports you to another player", usage = "/go [player]")
    public void go(CommandArgs args) {
        String args0[] = args.getArgs();
        Player bukkitPlayer = (Player) args.getSender();
        Player target = Bukkit.getPlayer(args0[0]);
        if(Bukkit.getServer().getPlayer(args0[0]).isOnline()) {
            bukkitPlayer.teleport(target);
            Util.sendMsg(bukkitPlayer, QCChat.getPhrase("teleported_you_to_player_X") + target.getName());
        } else {
            Util.sendMsg(bukkitPlayer, QCChat.getPhrase("specify_online_username"));
        }
    }

    @QCommand(name = "get", permission = "QCC.tp.get", description = "Teleports the specified player to you", usage = "/get [player]")
    public void get(CommandArgs args) {
        String args0[] = args.getArgs();
        Player bukkitPlayer = (Player) args.getSender();
        Player target = Bukkit.getPlayer(args0[0]);
        if(target.isOnline()) {
            target.teleport(bukkitPlayer);
            Util.sendMsg(target, QCChat.getPhrase("teleported_you_to_player_X") + bukkitPlayer.getName());
            Util.sendMsg(bukkitPlayer, target.getName() + QCChat.getPhrase("X_has_been_teleported_to_you"));
        } else {
            Util.sendMsg(bukkitPlayer, QCChat.getPhrase("specify_online_username"));
        }
    }
}
