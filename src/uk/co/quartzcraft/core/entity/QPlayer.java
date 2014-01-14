package uk.co.quartzcraft.core.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.chat.*;

public abstract class QPlayer {
	
	/**
	 * Sends a message to the specified player. Message is parsed by ChatFormatParser.
	 * @param player
	 * @param message
	 */
	public static void sendMessage(Player player, String message) {
		ChatFormatParser.parseChat(message);
		
		player.sendMessage(message);
	}
	
	/**
	 * Gets player data from QuartzCore PlayerData database table by using UUID.
	 * @param UUID
	 * @return ResultSet
	 */
	public static ResultSet getData(UUID UUID) {
		
		String SUUID = UUID.toString();
		try {
			Statement s = QuartzCore.MySQLcore.openConnection().createStatement();
	        ResultSet res = s.executeQuery("SELECT * FROM PlayerData WHERE UUID ='" + SUUID + "';");
	        res.next();
	        
	        if(res.getString("UUID") == SUUID) {
	        	return res;
	        } else {
	        	return null;
	        }
	        
		} catch(SQLException e) {
			Logger.getLogger("Minecraft").log(Level.SEVERE, null, e);
			return null;
		}
		
	}
	
	/**
	 * 
	 * Gets player data from QuartzCore PlayerData database table by used the QPlayer object.
	 * @author mba2012
	 * @param qplayer
	 * @deprecated Just going to use getData().
	 */
	public static ResultSet getQPlayer(QPlayer qplayer) {
		
		String playername = qplayer.toString();
		try {
			Statement s = QuartzCore.MySQLcore.openConnection().createStatement();
	        ResultSet res = s.executeQuery("SELECT * FROM PlayerData WHERE DisplayName ='" + playername + "';");
	        res.next();
	        
	        if(res.getString("DisplayName") == playername) {
	        	return res;
	        }
	        
		} catch(SQLException e) {
			Logger.getLogger("Minecraft").log(Level.SEVERE, null, e);
		}
		return null;
		
	}
	
	/**
	 * Updates the QuartzCraft PlayerData to set the connection status. 
	 * @param player
	 * @param conn
	 */
	public static void setConnectionStatus(Player player, boolean conn) {
		
	}
	
	/**
	 * Creates a play in the QuartzCore PlayerData database.
	 * @param player
	 * @return boolean
	 */
	public static boolean createPlayer(Player player) {
		
		long time = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(time);
		
		UUID UUID = player.getUniqueId();
		String SUUID = UUID.toString();
		
		try {
			Statement s = QuartzCore.MySQLcore.openConnection().createStatement();
			ResultSet res = s.executeQuery("INSERT INTO PlayerData (UUID, DisplayName, JoinDate, PrimaryGroupID, PassedTutorial) VALUES ('" + SUUID +"', '" + player.getDisplayName() + "', " + date + ", 9, 1);");
	        res.next();
	        
	        if(res != null) {
	        	return true;
	        } else {
	        	return false;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public abstract HashMap getDataThisPlugin();
	
	public abstract boolean createPlayerThisPlugin();

	/**
	 * 
	 * @param player
	 */
	public static void addUUID(Player player) {
		UUID UUID = player.getUniqueId();
		String SUUID = UUID.toString();
		
		String playername = player.getDisplayName();
		
		try {
			Statement s = QuartzCore.MySQLcore.openConnection().createStatement();
	        ResultSet res = s.executeQuery("UPDATE PlayerData SET UUID='" + SUUID + "' WHERE DisplayName='" + playername + "';");
			res.next();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}


}
