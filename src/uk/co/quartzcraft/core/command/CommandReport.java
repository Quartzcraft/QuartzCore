package uk.co.quartzcraft.core.command;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import uk.co.quartzcraft.core.command.framework.*;
import uk.co.quartzcraft.core.entity.QPlayer;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.chat.ChatPhrase;

import java.sql.SQLException;

public class CommandReport {

    private static QuartzCore plugin;
    private static QCommandFramework framework;

    public CommandReport(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    //TODO Rewrite sometime
    @uk.co.quartzcraft.core.command.framework.QCommand(name = "report", permission = "QCC.report", description = "Reports the specifed player", usage = "/report [player] [reason]")
    public void report(CommandArgs args) {

        String[] args1 = args.getArgs();
        Player player = (Player) args.getSender();
        Player player2 = Bukkit.getPlayer(args1[0]);
        QPlayer qplayer = new QPlayer(new QuartzCore(), player.getUniqueId());
        QPlayer qplayer2 = new QPlayer(new QuartzCore(), player2.getUniqueId()); //TODO Need latest 1.7 release

        if(!(player instanceof Player)){
            player.sendMessage(ChatPhrase.getPhrase("player_use_only"));
        } else {
            if(args.getArgs().length == 0) {
                player.sendMessage(ChatPhrase.getPhrase("please_specify_player_to_report"));
            } else {
                try {
                    java.sql.Connection connection = QuartzCore.MySQLcore.openConnection();
                    java.sql.PreparedStatement s = connection.prepareStatement("INSERT INTO Reports (reported_user_id, reporting_user_id, report_content) VALUES (" + qplayer2.getID() + ", " + qplayer.getID() + ", '" + getReportContent(args.getArgs()) + "');");
                    if(s.executeUpdate() == 1) {
                        player.sendMessage(ChatPhrase.getPhrase("thank_you_for_reporting_user"));
                    } else {
                        player.sendMessage(ChatPhrase.getPhrase("error_submitting_report"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    player.sendMessage(ChatPhrase.getPhrase("database_error_contact"));
                    player.sendMessage(ChatPhrase.getPhrase("error_submitting_report"));
                }
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
