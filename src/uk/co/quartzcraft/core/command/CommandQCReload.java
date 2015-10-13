package uk.co.quartzcraft.core.command;

import org.bukkit.command.CommandSender;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.CommandArgs;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;

public class CommandQCReload {
    private static QuartzCore plugin;
    private static QCommandFramework framework;

    public CommandQCReload(QuartzCore plugin) {
        this.plugin = plugin;
        framework = new QCommandFramework(this.plugin);
        framework.registerCommands(this);
    }

    @QCommand(name = "qcreload", aliases = { "qcr" }, permission = "QCC.reload", description = "Reloads the plugin", usage = "Use /qcreload")
    public void qcreload(CommandArgs args) {
        CommandSender sender = args.getSender();
        plugin.getPluginLoader().disablePlugin(plugin);
        plugin.getPluginLoader().enablePlugin(plugin);
        sender.sendMessage("[QC] Successfully reloaded plugin!");
        this.plugin.getLogger().info("[QC] Successfully reloaded plugin!");
    }

    @QCommand(name = "qcreload.config", aliases = { "qcr.config" }, permission = "QCC.reload", description = "Reloads the config", usage = "Use /qcreload config")
    public void qcreloadConfig(CommandArgs args) {
        CommandSender sender = args.getSender();
        plugin.reloadConfig();
        sender.sendMessage("[QC] Successfully reloaded config!");
        this.plugin.getLogger().info("[QC] Successfully reloaded config!");
    }
}
