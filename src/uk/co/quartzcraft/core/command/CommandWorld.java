package uk.co.quartzcraft.core.command;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.chat.ChatPhrase;

public class CommandWorld {
    private static QuartzCore plugin;
    private static QCommand framework;

    public CommandWorld(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommand(this.plugin);
        framework.registerCommands(this);
    }

    @QCommand.Command(name = "world", aliases = { "w" }, permission = "QCC.world", description = "Displays the world you are currently in.", usage = "Use /world")
    public void kingdom(QCommand.CommandArgs args) {
        Player player = (Player) args.getSender();
        World world = player.getWorld();
        player.sendMessage(ChatPhrase.getPhrase("you_are_currently_in_world") + ChatColor.WHITE + world.toString());
    }
}
