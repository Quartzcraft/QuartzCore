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
import uk.co.quartzcraft.core.event.QPlayerLoginEvent;

public class ConnectionListener implements Listener {
	
	private static QuartzCore plugin;
	
	public ConnectionListener(QuartzCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }
	
	/**
	 * Manages the login stuff for QuartzCore.
	 * @param login
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerLoginEvent login) {
		Player player = login.getPlayer();
		String splayer = player.toString();
		
		UUID UUID = player.getUniqueId();
		String SUUID = UUID.toString();

        if(login.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST){
            String message = ChatPhrase.getPhrase("Kick_Whitelist");
            login.setKickMessage(message);

        } else if(login.getResult() == PlayerLoginEvent.Result.KICK_FULL){
            String message = ChatPhrase.getPhrase("Server_Full");
            login.setKickMessage(message);

        }

		Statement s1;
		try {
			s1 = QuartzCore.MySQLcore.openConnection().createStatement();
			ResultSet res1 = s1.executeQuery("SELECT * FROM PlayerData WHERE UUID='" + SUUID + "'");
			if(res1.next()) {
				QPlayer.setConnectionStatus(player, true);
				//QPlayer.autoManageGroups(player, this.plugin);
				plugin.log.info("[QC] Player, " + player.getDisplayName() + " successfully joined!");
			} else {
				if(QPlayer.createPlayer(player)) {
					plugin.log.info("[QC] Player, " + player.getDisplayName() + " was created with UUID of " + SUUID);
				} else {
                    plugin.log.info("[QC] Could not create player!");
					player.kickPlayer(ChatPhrase.getPhrase("database_error_contact") + "\n" + ChatPhrase.getPhrase("could_not_create_player"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
            plugin.log.info("[QC] An SQL exception occurred during connection!");
		}
		
		//get player data from database
		//get player usergroup 
		//set player usergroup
		//other stuff
		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoinLow(PlayerJoinEvent join) {
		Player player = join.getPlayer();
		UUID UUID = player.getUniqueId();
		String SUUID = UUID.toString();
		
		String splayer = player.toString();
		String playername = player.getDisplayName();
		
		if(plugin.getConfig().getString("settings.join-broadcast") == "true") {
            String lastSeen = null;
			//Long lastSeen = player.getLastPlayed();
			String message = playername + ChatColor.YELLOW + " joined, last seen " + lastSeen;
			
			join.setJoinMessage(message);
		}

        QPlayerLoginEvent event = new QPlayerLoginEvent(player);
        Bukkit.getServer().getPluginManager().callEvent(event);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoinHigh(PlayerJoinEvent join) {
		Player player = join.getPlayer();
		UUID UUID = player.getUniqueId();
		String SUUID = UUID.toString();
		
		String splayer = player.toString();
		String playername = player.getDisplayName();
		
		player.sendMessage("Welcome back, " + playername + "!");
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent quit) {
		Player player = quit.getPlayer();
		
		if(plugin.getConfig().getString("settings.join-broadcast") == "true") {
			String message = player.getDisplayName() + ChatColor.YELLOW + " disconnected.";
			
			quit.setQuitMessage(message);
		}
	}
	
}
