package uk.co.quartzcraft.core.command;

import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.command.framework.*;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.systems.chat.Announce;
import uk.co.quartzcraft.core.util.Util;

public class CommandAnnounce {
    private static QuartzCore plugin;
    private static QCommandFramework framework;

    public CommandAnnounce(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    @QCommand(name = "announce", aliases = { "an", "a" }, permission = "QCC.announce", description = "Makes an announcement that all players can see.", usage = "Use /announce [announcement]")
    public void announce(CommandArgs args) {
        Player player = (Player) args.getSender();
        String[] args0 = args.getArgs();
        if(args0.length >= 1) {
            String announcement = getAnnouncementContent(args0);
            Announce.announce(announcement);
        } else {
            Util.sendMsg(args.getPlayer(), QCChat.getPhrase("specify_arguments"));
        }
    }

    @QCommand(name = "announce.qc", aliases = { "an.qc", "a.qc" }, permission = "QCC.announce.official", description = "Makes an announcement that all players can see with the official QuartzCraft prefix", usage = "Use /announce qc [announcement]")
    public void announceOfficial(CommandArgs args) {
        Player player = (Player) args.getSender();
        String[] args0 = args.getArgs();
        if(args0.length >= 1) {
            String announcement = getAnnouncementContent(args0);
            Util.broadcastMsg(announcement);
        } else {
            Util.sendMsg(args.getPlayer(), QCChat.getPhrase("specify_arguments"));
        }
    }

    @QCommand(name = "announce.title", aliases = { "an.title", "a.title" }, permission = "QCC.announce.title", description = "Makes an announcement that all players can see in a title.", usage = "Use /announce title [announcement]")
    public void announceTitle(CommandArgs args) {
        Player player = (Player) args.getSender();
        String[] args0 = args.getArgs();
        if(args0.length >= 1) {
            String announcement = getAnnouncementContent(args0);
            Announce.announceTitle(announcement);
        } else {
            Util.sendMsg(args.getPlayer(), QCChat.getPhrase("specify_arguments"));
        }
    }

    @QCommand(name = "announce.bar", aliases = { "an.bar", "a.bar" }, permission = "QCC.announce.bar", description = "Makes an announcement that all players can see in an ActionBar.", usage = "Use /announce bar [announcement]")
    public void announceBar(CommandArgs args) {
        Player player = (Player) args.getSender();
        String[] args0 = args.getArgs();
        if(args0.length >= 1) {
            String announcement = getAnnouncementContent(args0);
            Announce.announceBar(announcement);
        } else {
            Util.sendMsg(args.getPlayer(), QCChat.getPhrase("specify_arguments"));
        }
    }

    public String getAnnouncementContent(String[] args) {
        StringBuilder builder = new StringBuilder();
        for(String s : args) {
            builder.append(s + " ");
        }
        return builder.toString();
    }
}
