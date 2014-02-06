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
	
	private static QuartzCore plugin;
	
	public ConnectionListener(QuartzCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
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
					boolean result = QPlayer.createPlayer(player);
					if(result) {
						player.kickPlayer(ChatPhrase.getPhrase("database_error_contact") + ChatPhrase.getPhrase("could_not_create_player"));
					} else {
						//Nothing
					}
				}
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(login.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST){
			String message = ChatPhrase.getPhrase("Kick_Whitelist");
            login.setKickMessage(message);
            
		} else if(login.getResult() == PlayerLoginEvent.Result.KICK_FULL){
			String message = ChatPhrase.getPhrase("Server_Full");
            login.setKickMessage(message);
            
		}
		
		
		//get player data from database
		//get player usergroup 
		//set player usergroup
		//other stuff
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoinHigh(PlayerJoinEvent join) {
		Player player = join.getPlayer();
		UUID UUID = player.getUniqueId();
		String SUUID = UUID.toString();
		
		String splayer = player.toString();
		String playername = player.getDisplayName();
		
		if(plugin.getConfig().getString("settings.connection-broadcast") == "true") {
			String lastSeen = QPlayer.getLastSeen(SUUID);
			String message = playername + ChatColor.YELLOW + " joined, last seen " + lastSeen;
			
			join.setJoinMessage(message);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent join) {
		Player player = join.getPlayer();
		UUID UUID = player.getUniqueId();
		String SUUID = UUID.toString();
		
		String splayer = player.toString();
		String playername = player.getDisplayName();
		
		player.sendMessage("Welcome back, " + playername + "!");
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent quit) {
		//Shouldn't really be monitor, but need to make sure all other permissions plugins are finished.
		Player player = quit.getPlayer();
		String playername = QPlayer.getDisplayName(player);
		
		if(plugin.getConfig().getString("settings.connection-broadcast") == "true") {
			String message = playername + ChatColor.YELLOW + " disconnected.";
			
			quit.setQuitMessage(message);
		}
	}
	
}
