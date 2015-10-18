package uk.co.quartzcraft.core.command;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.command.framework.CommandArgs;
import uk.co.quartzcraft.core.command.framework.QCommand;
import uk.co.quartzcraft.core.command.framework.QCommandFramework;
import uk.co.quartzcraft.core.util.Util;

public class CommandM {

	private static QuartzCore plugin;
	private static QCommandFramework framework;

	public CommandM(QuartzCore plugin) {
		this.plugin = plugin;
		framework = new QCommandFramework(this.plugin);
		framework.registerCommands(this);
	}

	@QCommand(name = "m", permission = "QCC.m", description = "Quickly changes gamemode", usage = "/m")
	public void mCommand(CommandArgs args) {
		Player player = args.getPlayer();
		Object gameMode = player.getGameMode();

		if (gameMode == GameMode.SURVIVAL) {
			player.setGameMode(GameMode.CREATIVE);
			Util.sendMsg(player, ChatColor.GOLD + "Your gamemode is now " + ChatColor.GREEN + "CREATIVE" + ChatColor.GOLD + "!");
		} else if (gameMode == GameMode.CREATIVE) {
			player.setGameMode(GameMode.SURVIVAL);
			Util.sendMsg(player, ChatColor.GOLD + "Your gamemode is now " + ChatColor.GREEN + "SURVIVAL" + ChatColor.GOLD + "!");
		} else if (gameMode == GameMode.ADVENTURE) {
			player.setGameMode(GameMode.SURVIVAL);
			Util.sendMsg(player, ChatColor.GOLD + "Your gamemode is now " + ChatColor.GREEN + "SURVIVAL" + ChatColor.GOLD + "!");
		} else {
			Util.sendMsg(player, ChatColor.RED + "Your gamemode could not be changed!");
		}
	}

}
