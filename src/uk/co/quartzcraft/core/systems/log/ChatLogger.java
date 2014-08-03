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
            java.sql.PreparedStatement s = QuartzCore.DBCore.prepareStatement("INSERT INTO ChatLog (timestamp, player_id, message) VALUES (?, ?, ?);");
            s.setTimestamp(1, timestamp);
            s.setInt(2, player.getID());
            s.setString(3, message);
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
            java.sql.PreparedStatement s = QuartzCore.DBCore.prepareStatement("INSERT INTO ChatLog (timestamp, player_id, message) VALUES (?, ?, ?);");
            s.setTimestamp(1, timestamp);
            s.setInt(2, qPlayer.getID());
            s.setString(3, message);
        } catch(SQLException e) {
            Util.printException("Failed to log chat using QPlayer", e);
        }
    }
}
