package uk.co.quartzcraft.core.command;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class CommandM implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("m")){ 
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
				return false;
			} else {
				Player player = (Player) sender;
				Object gameMode = player.getGameMode();
				
				if (gameMode == GameMode.SURVIVAL) {
					player.setGameMode(GameMode.CREATIVE);
					player.sendMessage(ChatColor.GOLD + "Your gamemode is now " + ChatColor.GREEN + "CREATIVE" + ChatColor.GOLD + "!");
				} else if (gameMode == GameMode.CREATIVE) {
					player.setGameMode(GameMode.SURVIVAL);
					player.sendMessage(ChatColor.GOLD + "Your gamemode is now " + ChatColor.GREEN + "SURVIVAL" + ChatColor.GOLD + "!");
				} else if (gameMode == GameMode.ADVENTURE) {
					player.setGameMode(GameMode.SURVIVAL);
					player.sendMessage(ChatColor.GOLD + "Your gamemode is now " + ChatColor.GREEN + "SURVIVAL" + ChatColor.GOLD + "!");
				} else {
					player.sendMessage(ChatColor.RED + "Your gamemode could not be changed!");
				}
			}
			
			return true;
		} else {
			return false;
		}
		//sender.sendMessage(ChatColor.RED + "Your gamemode could not be changed!");
	}

}
