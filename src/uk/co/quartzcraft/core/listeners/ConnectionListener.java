package uk.co.quartzcraft.core.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.data.QPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import uk.co.quartzcraft.core.event.QPlayerLoginEvent;
import uk.co.quartzcraft.core.systems.perms.Permissions;

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
            String message = QCChat.getPhrase("Kick_Whitelist");
            login.setKickMessage(message);

        } else if(login.getResult() == PlayerLoginEvent.Result.KICK_FULL){
            String message = QCChat.getPhrase("Server_Full");
            login.setKickMessage(message);

        }

        if(QPlayer.exists(player.getUniqueId())) {
            plugin.log.info("[QC] Player, " + player.getDisplayName() + " successfully joined!");
        } else {
            if(QPlayer.createPlayer(player)) {
                plugin.log.info("[QC] Player, " + player.getDisplayName() + " was created with UUID of " + SUUID);
            } else {
                plugin.log.info("[QC] Could not create player!");
                player.kickPlayer(QCChat.getPhrase("database_error_contact") + "\n" + QCChat.getPhrase("could_not_create_player"));
            }
        }
		
		//get player data from database
		//get player usergroup 
		//set player usergroup
		//other stuff
		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoinLow(PlayerJoinEvent join) {
		Player player = join.getPlayer();
        QPlayer qplayer = new QPlayer(player);
		UUID UUID = player.getUniqueId();

        Permissions.registerPlayerPerms(qplayer, null);

        player.setDisplayName(player.getName());

        //TODO complete
        if(player.hasPermission("QCC.chat.nameRED")) {
            player.setDisplayName(ChatColor.RED + player.getDisplayName());
        }
		
		if(plugin.getConfig().getString("settings.join-broadcast").equals("true")) {
            String lastSeen = null;
			//Long lastSeen = player.getLastPlayed();
			String message = player.getDisplayName() + ChatColor.YELLOW + " joined, last seen " + lastSeen;
			
			join.setJoinMessage(message);
		}

        QPlayerLoginEvent loginEvent = new QPlayerLoginEvent(player);
        Bukkit.getServer().getPluginManager().callEvent(loginEvent);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoinHigh(PlayerJoinEvent join) {
		Player player = join.getPlayer();
        QPlayer qplayer = new QPlayer(player);
		UUID UUID = player.getUniqueId();
		String SUUID = UUID.toString();
		
		player.sendMessage("Welcome back, " + player.getDisplayName() + "!");
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent quit) {
		Player player = quit.getPlayer();
		
		if(plugin.getConfig().getString("settings.join-broadcast").equals("true")) {
			String message = player.getDisplayName() + ChatColor.YELLOW + " disconnected.";
			
			quit.setQuitMessage(message);
		}
	}
	
}
