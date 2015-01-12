package uk.co.quartzcraft.core.listeners;

import java.util.UUID;

import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.event.QPlayerJoinEvent;
import uk.co.quartzcraft.core.features.ActionBar;
import uk.co.quartzcraft.core.features.FancyMessages;
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
import uk.co.quartzcraft.core.util.Util;

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

        if(!player.getName().equals(qplayer.getName())) {
            qplayer.updateName(player.getName());
        }

        if(qplayer.isOnline()) {
            player.kickPlayer(QCChat.getPhrase("you_can_only_be_connected_to_one_server_at_a_time"));
        }

        if(plugin.getConfig().getBoolean("settings.use-perm-system")) {
            Permissions.registerPlayerPerms(qplayer, null);

            player.setDisplayName(player.getName());
            player.setDisplayName(qplayer.getGroup().getStyleForName() + player.getName() + ChatColor.RESET);
            player.setPlayerListName(Util.removeExtraChars(player.getDisplayName(), 16));
        }

        if(plugin.getConfig().getBoolean("settings.join-broadcast")) {
            String lastSeen = qplayer.getLastSeen();
			//Long lastSeen = player.getLastPlayed();
			String message = QCChat.getPhrase("official_prefix") + player.getDisplayName() + ChatColor.YELLOW + " joined, last seen " + lastSeen + " ago";
			
			join.setJoinMessage(message);
		}

        QPlayerJoinEvent loginEvent = new QPlayerJoinEvent(qplayer);
        Bukkit.getServer().getPluginManager().callEvent(loginEvent);

//        if(!qplayer.setOnline(true)) {
//            player.kickPlayer(QCChat.getPhrase("database_error_try_again"));
//        }
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoinHigh(PlayerJoinEvent join) {
		Player player = join.getPlayer();

        player.sendMessage(FancyMessages.welcomeBack(player));
        player.sendMessage(FancyMessages.checkWebsite(player));
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent quit) {
		Player player = quit.getPlayer();
		
		if(plugin.getConfig().getBoolean("settings.join-broadcast")) {
			String message = QCChat.getPhrase("official_prefix") + player.getDisplayName() + ChatColor.YELLOW + " disconnected.";
			
			quit.setQuitMessage(message);
		}


	}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuitHigh(PlayerQuitEvent join) {
        Player player = join.getPlayer();
        QPlayer qplayer = new QPlayer(player);

        qplayer.setOnline(false);
    }
	
}
