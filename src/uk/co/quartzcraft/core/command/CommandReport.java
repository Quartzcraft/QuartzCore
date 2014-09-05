package uk.co.quartzcraft.core.command;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.command.framework.*;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.systems.chat.QCChat;

import java.sql.SQLException;

public class CommandReport {

    private static Plugin plugin;
    private static QCommandFramework framework;

    public CommandReport(Plugin plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    //TODO Rewrite sometime
    @QCommand(name = "report", permission = "QCC.report", description = "Reports the specified player", usage = "/report [player] [reason]")
    public void report(CommandArgs args) {

        String[] args1 = args.getArgs();
        Player player = (Player) args.getSender();
        Player player2 = Bukkit.getPlayer(args1[0]);
        QPlayer qplayer = new QPlayer(player);
        QPlayer qplayer2 = new QPlayer(player2);

        if(!(args.getSender() instanceof Player)){
            args.getSender().sendMessage(QCChat.getPhrase("player_use_only"));
        } else {
            if(args.getArgs().length == 0) {
                player.sendMessage(QCChat.getPhrase("please_specify_player_to_report"));
            } else {
                qplayer2.report(qplayer, getReportContent(args1));
                qplayer.sendMessage(QCChat.getPhrase("thank_you_for_reporting_user"));
            }
        }
    }

    public String getReportContent(String[] args) {
        StringBuilder builder = new StringBuilder();
        for(String s : args) {
            builder.append(s + " ");
        }
        return builder.toString();
    }

}
