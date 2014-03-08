package uk.co.quartzcraft.core.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import uk.co.quartzcraft.core.database.*;
import uk.co.quartzcraft.core.entity.QPlayer;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.chat.Announce;
import uk.co.quartzcraft.core.chat.ChatPhrase;
import uk.co.quartzcraft.core.command.QCommand;

public class CommandTest implements CommandExecutor {

    private static QuartzCore plugin;

    public CommandTest(QuartzCore plugin) {
        framework.registerCommands(this);
        this.plugin = plugin;
    }

    QCommand framework = new QCommand(plugin);

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return framework.handleCommand(sender, label, command, args);
    }

    @QCommand.Command(name = "test", aliases = { "testing" }, description = "This is a test command", usage = "This is how you use it")
    public void test(QCommand.CommandArgs args) {
        args.getSender().sendMessage("This is a test command");
    }
}
