package uk.co.quartzcraft.core.listeners;

import uk.co.quartzcraft.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

	//public String quartzJoinMessage = ChatColor.GOLD + "A player joined!";

	public void setJoinMessage(String joinMessage) {
		//this.quartzJoinMessage  = joinMessage;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent login) {
		Player player = login.getPlayer();
		
		//get player data from database
		//get player usergroup 
		//set player usergroup
		//other stuff
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent join) {
		Player player = join.getPlayer();
		
		player.sendMessage("Welcome back, " + player + "!");
		player.sendMessage("Your QC balance is: ");
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent quit) {
		Player player = quit.getPlayer();
		
		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "-" + player + " disconnected!");
	}
	
}
