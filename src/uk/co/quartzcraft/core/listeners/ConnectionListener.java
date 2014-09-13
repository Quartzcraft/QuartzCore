package uk.co.quartzcraft.core.listeners;

import java.util.UUID;

import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.event.QPlayerJoinEvent;
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
import uk.co.quartzcraft.core.systems.perms.Permissions;

public class ConnectionListener implements Listener {
	
	private static QuartzCore plugin;
	
	public ConnectionListener(QuartzCore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

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
            plugin.log.info("[QC] Player, " + player.getName() + " successfully joined!");
        } else {
            if(QPlayer.createPlayer(player)) {
                plugin.log.info("[QC] Player, " + player.getName() + " was created with UUID of " + SUUID);
            } else {
                plugin.log.info("[QC] Could not create player!");
                player.kickPlayer(QCChat.getPhrase("database_error_contact") + "\n" + QCChat.getPhrase("could_not_create_player"));
            }
        }
		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoinLow(PlayerJoinEvent join) {
		Player player = join.getPlayer();
        QPlayer qplayer = new QPlayer(player);
		UUID UUID = player.getUniqueId();

        Permissions.registerPlayerPerms(qplayer, null);

        player.setDisplayName(player.getName());

        //TODO Move to another class
        //Username colouring
        if(player.hasPermission("QCC.namecolour.white")) {
            player.setDisplayName(ChatColor.WHITE + player.getName());
        } else if(player.hasPermission("QCC.namecolour.light-purple")) {
            player.setDisplayName(ChatColor.LIGHT_PURPLE + player.getName());
        } else if(player.hasPermission("QCC.namecolour.green")) {
            player.setDisplayName(ChatColor.DARK_GREEN + player.getName());
        } else if(player.hasPermission("QCC.namecolour.lime")) {
            player.setDisplayName(ChatColor.GREEN + player.getName());
        } else if(player.hasPermission("QCC.namecolour.red")) {
            player.setDisplayName(ChatColor.RED + player.getName());
        } else if(player.hasPermission("QCC.namecolour.purple")) {
            player.setDisplayName(ChatColor.DARK_PURPLE + player.getName());
        }

        //TODO Move to another class
        //Username prefixing
        if(player.hasPermission("QCC.nameprefix.null")) {
            //Do nothing
        } else if(player.hasPermission("QCC.nameprefix.b")) {
            String name = ChatColor.YELLOW + "[B]" + player.getDisplayName();
            player.setDisplayName(name);
        }

        if(plugin.getConfig().getString("settings.join-broadcast").equals("true")) {
            String lastSeen = qplayer.getLastSeen();
			//Long lastSeen = player.getLastPlayed();
			String message = player.getDisplayName() + ChatColor.YELLOW + " joined, last seen " + lastSeen;
			
			join.setJoinMessage(message);
		}

        QPlayerJoinEvent loginEvent = new QPlayerJoinEvent(qplayer);
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
