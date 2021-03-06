package uk.co.quartzcraft.core.listeners;

import java.util.UUID;

import net.md_5.bungee.api.chat.TextComponent;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.event.QPlayerJoinEvent;
import uk.co.quartzcraft.core.features.TextComponentMessages;
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

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLogin(PlayerLoginEvent login) {
		Player player = login.getPlayer();
		UUID UUID = player.getUniqueId();
		String SUUID = UUID.toString();

        if(!QPlayer.exists(player.getUniqueId())) {
            if(QPlayer.createPlayer(player)) {
                Util.log("QPlayer, " + player.getName() + " was created with UUID of " + SUUID);
            } else {
                Util.log("Could not create player!");
                player.kickPlayer(QCChat.getPhrase("database_error_contact") + "\n" + QCChat.getPhrase("could_not_create_player"));
            }
        }

        QPlayer qplayer = new QPlayer(player);

        if(!player.getName().equals(qplayer.getName())) {
            qplayer.updateName(player.getName());
        }

        if(plugin.getConfig().getBoolean("settings.use-perm-system")) {
            Permissions.registerPlayerPerms(qplayer, null);

            player.setDisplayName(player.getName());
            player.setDisplayName(qplayer.getGroup().getStyleForName() + player.getName() + ChatColor.RESET);
            player.setPlayerListName(Util.removeExtraChars(player.getDisplayName(), 16));
        }

        if(login.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST){
            String message = QCChat.getPhrase("Kick_Whitelist");
            login.setKickMessage(message);
        }

        if(login.getResult() == PlayerLoginEvent.Result.KICK_FULL){
            if(!(Bukkit.getOnlinePlayers().size() > QuartzCore.getQServer().getMaxPlayers()) && player.hasPermission("QCC.bypassmax")) {
                login.setResult(PlayerLoginEvent.Result.ALLOWED);
            } else {
                String message = QCChat.getPhrase("Server_Full");
                login.setKickMessage(message);
            }
        }

        if(!qplayer.setOnline(true)) {
            player.kickPlayer(QCChat.getPhrase("database_error_try_again"));
        }

        /*
        if(QuartzCore.getQServer().createPlayerSession(player)) {
            Util.log("Player, " + player.getName() + " successfully joined!");
        }
        */

        Util.log("Player, " + player.getName() + " successfully joined!");
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoinHigh(PlayerJoinEvent join) {
		Player player = join.getPlayer();
        QPlayer qplayer = new QPlayer(player);

        if(plugin.getConfig().getBoolean("settings.join-broadcast")) {
            String lastSeen = qplayer.getLastSeen();
            //Long lastSeen = player.getLastPlayed();
            String message = QCChat.getPhrase("official_prefix") + player.getDisplayName() + ChatColor.YELLOW + " joined, last seen " + lastSeen + " ago";

            join.setJoinMessage(message);
        }

        QPlayerJoinEvent joinEvent = new QPlayerJoinEvent(qplayer);
        Bukkit.getServer().getPluginManager().callEvent(joinEvent);

        player.spigot().sendMessage(TextComponentMessages.welcomeBack(player));
        player.spigot().sendMessage(TextComponentMessages.checkWebsite(player));
        player.spigot().sendMessage(TextComponentMessages.hasUnreadAlerts(player));
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent quit) {
		Player player = quit.getPlayer();
		
		if(plugin.getConfig().getBoolean("settings.join-broadcast")) {
			String message = QCChat.getPhrase("official_prefix") + player.getDisplayName() + ChatColor.YELLOW + " disconnected.";
			
			quit.setQuitMessage(message);
		}


	}

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuitHigh(PlayerQuitEvent quit) {
        Player player = quit.getPlayer();
        QPlayer qplayer = new QPlayer(player);

        if(!qplayer.setOnline(false)) {
            Util.log("Player, " + player.getName() + " was not set offline");
            return;
        }

        Util.log("Player, " + player.getName() + " successfully disconnected!");
    }
}
