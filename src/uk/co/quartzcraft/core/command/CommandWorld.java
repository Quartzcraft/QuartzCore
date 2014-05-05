package uk.co.quartzcraft.core.command;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.chat.ChatPhrase;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.*;

public class CommandWorld {
    private static QuartzCore plugin;
    private static QCommandFramework framework;

    public CommandWorld(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    @QCommand(name = "world", aliases = { "w" }, permission = "QCC.world", description = "Displays the world you are currently in.", usage = "Use /world")
    public void world(CommandArgs args) {
        Player player = (Player) args.getSender();
        World world = player.getWorld();
        player.sendMessage(ChatPhrase.getPhrase("you_are_currently_in_world") + ChatColor.WHITE + world.getName());
    }
}
