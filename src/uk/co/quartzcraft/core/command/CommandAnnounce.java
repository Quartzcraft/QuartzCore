package uk.co.quartzcraft.core.command;

import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.command.framework.*;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.systems.chat.Announce;

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
            args.getSender().sendMessage(QCChat.getPhrase("specify_arguments"));
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
