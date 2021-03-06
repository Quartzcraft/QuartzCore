package uk.co.quartzcraft.core.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.features.TextComponentMessages;
import uk.co.quartzcraft.core.features.bountifulapi.BountifulAPI;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.util.Util;
import uk.co.quartzcraft.core.command.framework.CommandArgs;

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
        QPlayer qplayer = new QPlayer(player);
        QPlayer target = new QPlayer(args1[0]);

        if(!(args.getSender() instanceof Player)){
            args.getSender().sendMessage(QCChat.getPhrase("player_use_only"));
        } else {
            if(args.getArgs().length == 0) {
                Util.sendMsg(args.getPlayer(), QCChat.getPhrase("please_specify_player_to_report"));
            } else {
                target.report(qplayer, getReportContent(args1));
                //args.getSender().sendMessage(QCChat.getPhrase("thank_you_for_reporting_user"));
                BountifulAPI.sendActionBar((Player) args.getSender(), ChatColor.GREEN + QCChat.getPhrase("thank_you_for_reporting_user"));
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
