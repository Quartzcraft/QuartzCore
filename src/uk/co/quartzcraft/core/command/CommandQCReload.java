package uk.co.quartzcraft.core.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;

/**
 * Created by nicholasbassett on 22/03/2014.
 */
public class CommandQCReload {
    private static QuartzCore plugin;
    private static QCommand framework;

    public CommandQCReload(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommand(this.plugin);
        framework.registerCommands(this);
    }

    @QCommand.Command(name = "qcreload", aliases = { "qcr" }, permission = "QCC.reload", description = "Reloads the config", usage = "Use /qcreload")
    public void kingdom(QCommand.CommandArgs args) {
        CommandSender sender = args.getSender();
        plugin.getPluginLoader().disablePlugin(plugin);
        plugin.getPluginLoader().enablePlugin(plugin);
        sender.sendMessage("[QC] Successfully reloaded config!");
        this.plugin.getLogger().info("[QC] Successfully reloaded config!");
    }
}
