package uk.co.quartzcraft.core.systems.log;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.event.QPlayerCreationEvent;
import uk.co.quartzcraft.core.util.Util;

import java.sql.SQLException;
import java.sql.Timestamp;

public class ChatLogger {

    /**
     * Logs the message to be retrieved later.
     *
     * @param player QPlayer object of a player
     * @param message The message to log
     */
    public static void log(QPlayer player, String message) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try{
            java.sql.PreparedStatement s = QuartzCore.DBLog.prepareStatement("INSERT INTO ChatLog (server_id, timestamp, player_id, message) VALUES (?, ?, ?, ?);");
            s.setInt(1, QuartzCore.server);
            s.setTimestamp(2, timestamp);
            s.setInt(3, player.getID());
            s.setString(4, message);
            s.executeUpdate();
        } catch(SQLException e) {
            Util.printException("Failed to log chat using QPlayer", e);
        }
    }

    /**
     * Logs the message to be retrieved later.
     *
     * @param player Player object of a player
     * @param message The message to log
     */
    public static void log(Player player, String message) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        QPlayer qPlayer = new QPlayer(player);
        try{
            java.sql.PreparedStatement s = QuartzCore.DBLog.prepareStatement("INSERT INTO ChatLog (server_id, timestamp, player_id, message) VALUES (?, ?, ?, ?);");
            s.setInt(1, QuartzCore.server);
            s.setTimestamp(2, timestamp);
            s.setInt(3, qPlayer.getID());
            s.setString(4, message);
            s.executeUpdate();
        } catch(SQLException e) {
            Util.printException("Failed to log chat using Player", e);
        }
    }
}
