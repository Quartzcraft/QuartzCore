package uk.co.quartzcraft.core.listeners;

import uk.co.quartzcraft.*;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.entity.QPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {
	
	public ConnectionListener(QuartzCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLogin(PlayerLoginEvent login) {
		Player player = login.getPlayer();
		
		//get player data from database
		//get player usergroup 
		//set player usergroup
		//other stuff
		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent join) {
		Player player = join.getPlayer();
		
		player.sendMessage("Welcome back, " + player + "!");
		//player.sendMessage("Your QC balance is: ");
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent quit) {
		//Shouldn't really be monitor, but need to make sure all other permissions plugins are finished.
		Player player = quit.getPlayer();
		
		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "-" + player + " disconnected!");
	}
	
}
