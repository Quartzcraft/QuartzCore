package uk.co.quartzcraft.core.data;

import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class QServer {

    private int id;
    private String name;

    private int maxPlayers;

    private Map<Integer, QPlayer> playerMap = new HashMap<Integer, QPlayer>();

    private Map<UUID, Integer> playerUUIDMap = new HashMap<UUID, Integer>();
    private Map<Player, Integer> playerPlayerMap = new HashMap<Player, Integer>();
    private Map<String, Integer> playerNameMap = new HashMap<String, Integer>();

    public QServer(int serverID) {
        this.id = serverID;
        try {
            PreparedStatement s = QuartzCore.DBCore.prepareStatement("SELECT * FROM Servers WHERE id=?;");
            s.setInt(1, serverID);
            ResultSet res = s.executeQuery();
            if(res.next()) {
                if(res.getInt("id") == serverID) {
                    this.name = res.getString("server_name");
                    this.maxPlayers = res.getInt("max_players");
                } else {
                    Util.log(Level.SEVERE, "QServer ID not equal");
                }
            }

        } catch(SQLException e) {
            Util.printException("Failed to retrieve QServer from database", e);
        }
    }

    public int getId() {
        return this.id;
    }

    public String getServerName() {
        return this.name;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public boolean createPlayerSession(Player player) {
        QPlayer qPlayer = new QPlayer(player);

        if(qPlayer.isOnline()) {
            player.kickPlayer(QCChat.getPhrase("you_can_only_be_connected_to_one_server_at_a_time"));
            return false;
        }

        playerMap.put(qPlayer.getID(), qPlayer);
        playerUUIDMap.put(qPlayer.getUniqueId(), qPlayer.getID());
        playerPlayerMap.put(player, qPlayer.getID());
        playerNameMap.put(player.getName(), qPlayer.getID());

        return true;
    }

    public void updatePlayerSession(QPlayer player) {
        if(playerMap.containsKey(player.getID())) {
            playerMap.remove(player.getID());
            playerMap.put(player.getID(), player);
        }
    }

    public QPlayer getPlayer(UUID uuid) {
        if(playerUUIDMap.containsKey(uuid)) {
            return playerMap.get(playerUUIDMap.get(uuid));
        }

        return new QPlayer(uuid);
    }

    public QPlayer getPlayer(Player player) {
        if(playerPlayerMap.containsKey(player)) {
            return playerMap.get(playerPlayerMap.get(player));
        }

        return new QPlayer(player);
    }

    public QPlayer getPlayer(int id) {
        if(playerMap.containsKey(id)) {
            return playerMap.get(id);
        }

        return new QPlayer(id);
    }

    public QPlayer getPlayer(String name) {
        if(playerNameMap.containsKey(name)) {
            return playerMap.get(playerNameMap.get(name));
        }

        return new QPlayer(name);
    }

    public void destoryPlayerSession(int id) {
        if(playerMap.containsKey(id)) {
            QPlayer qPlayer = playerMap.get(id);
            qPlayer.save();

            playerUUIDMap.remove(qPlayer.getUniqueId());
            playerPlayerMap.remove(qPlayer.getPlayer());
            playerNameMap.remove(qPlayer.getName());
            playerMap.remove(id);

            qPlayer = null;
        }
    }

}
