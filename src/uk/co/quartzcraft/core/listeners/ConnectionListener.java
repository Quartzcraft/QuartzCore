package uk.co.quartzcraft.core.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import uk.co.quartzcraft.*;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.chat.ChatPhrase;
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
	
	/**
	 * Still a WIP - Must get player creation working some time.
	 * @param login
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLogin(PlayerLoginEvent login) {
		Player player = login.getPlayer();
		String splayer = player.toString();
		
		UUID UUID = player.getUniqueId();
		String SUUID = UUID.toString();
		
		Statement s1;
		try {
			s1 = QuartzCore.MySQLcore.openConnection().createStatement();
			ResultSet res1 = s1.executeQuery("SELECT * FROM PlayerData WHERE UUID='" + SUUID + "'");
			res1.next();
			if(res1 != null) {
				if(res1.getString("UUID") == SUUID) {
					QPlayer.setConnectionStatus(player, true);
				} else {
					QPlayer.addUUID(player);
				}
			} else {
				/*
				if(QPlayer.createPlayer(player)) {
					//Do nothing
				} else {
					player.kickPlayer(ChatPhrase.getPhrase("database_error_contact"));
				}
				*/
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//get player data from database
		//get player usergroup 
		//set player usergroup
		//other stuff
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent join) {
		Player player = join.getPlayer();
		String splayer = player.toString();
		String playername = player.getDisplayName();
		
		player.sendMessage("Welcome back, " + playername + "!");
		//player.sendMessage("Your QC balance is: ");
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent quit) {
		//Shouldn't really be monitor, but need to make sure all other permissions plugins are finished.
		Player player = quit.getPlayer();
		
		Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "-" + player + " disconnected!");
	}
	
}
