package uk.co.quartzcraft.listeners;

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

	private String quartzJoinMessage = ChatColor.GOLD + "A player joined!";

	public void setJoinMessage(String joinMessage) {
		this.quartzJoinMessage  = joinMessage;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		
		//Bukkit.getServer().broadcastMessage(player + " joined!");
		//player.sendMessage("Welcome back, " + player + "!");
		//announce("A member joined!");
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "+" + player + " joined!");
		player.sendMessage("Welcome back, " + player + "!");
		//announce("A member joined!");
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "-" + player + " disconnected!");
	}
	
}
