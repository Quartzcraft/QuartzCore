package uk.co.quartzcraft.core.command;

import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.chat.Announce;
import uk.co.quartzcraft.core.chat.ChatPhrase;

public class CommandAnnounce {
    private static QuartzCore plugin;
    private static QCommand framework;

    public CommandAnnounce(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommand(this.plugin);
        framework.registerCommands(this);
    }

    @QCommand.Command(name = "announce", aliases = { "an" }, permission = "QCC.announce", description = "Makes an announcement that all players can see.", usage = "Use /announce [announcement]")
    public void kingdom(QCommand.CommandArgs args) {
        Player player = (Player) args.getSender();
        String[] args0 = args.getArgs();
        String announcement;
        if(args0.length >= 1) {
            for(String arg : args0) {
                announcement = announcement.concat(arg);
            }
            Announce.announce(announcement);
        } else {
            args.getSender().sendMessage(ChatPhrase.getPhrase("specify_arguments"));
        }
    }
}
