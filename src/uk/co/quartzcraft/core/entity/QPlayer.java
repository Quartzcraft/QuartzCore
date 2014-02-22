package uk.co.quartzcraft.core.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.chat.*;
import uk.co.quartzcraft.core.event.QPlayerCreationEvent;

public abstract class QPlayer {
	
	private static QuartzCore plugin;
	
	public void QuartzKingdomsConfig(QuartzCore plugin) {
		this.plugin = plugin;
	}
	private static java.sql.Timestamp getCurrentTimeStamp() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Timestamp(today.getTime());
	}
	
	/**
	 * Gets player data from QuartzCore PlayerData database table by using UUID.
	 * 
	 * @param UUID
	 * @return a map of all the data
	 */
	public static HashMap getData(UUID UUID) {
		
		String SUUID = UUID.toString();
		try {
			Statement s = QuartzCore.MySQLcore.openConnection().createStatement();
	        ResultSet res = s.executeQuery("SELECT * FROM PlayerData WHERE UUID ='" + SUUID + "';");
	        res.next();
	        
	        if(res.getString("UUID") == SUUID) {
	        	return null;
	        } else {
	        	return null;
	        }
	        
		} catch(SQLException e) {
			Logger.getLogger("Minecraft").log(Level.SEVERE, null, e);
			return null;
		}
		
	}
	
	/**
	 * Gets a users id from the PlayerData database
	 * 
	 * @param player
	 * @return id of the player
	 */
	public static int getUserID(Player player) {
		String SUUID = player.getUniqueId().toString();
		
		Statement s;
		try {
			s = QuartzCore.MySQLcore.openConnection().createStatement();
			ResultSet res = s.executeQuery("SELECT * FROM PlayerData WHERE UUID ='" + SUUID + "';");
	        if(res.next()) {
	        	int id = res.getInt("id");
	        	return id;
	        } else {
	        	return 0;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Gets the display name of the player.
	 * 
	 * @param player
	 * @return The display name of the player as defined in the QuartzCore database
	 */
	public static String getDisplayName(Player player) {
		Statement s;
		
		try {
			s = QuartzCore.MySQLcore.openConnection().createStatement();
			ResultSet res = s.executeQuery("SELECT * FROM PlayerData WHERE DisplayName ='" + player.getDisplayName().toString() + "';");
	        if(res.next()) {
	        	return res.getString(3);
	        } else {
	        	return null;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets the display name of the player.
	 * 
	 * @param id
	 * @return The display name of the player as defined in the QuartzCore database
	 */
	public static String getDisplayName(int id) {
		Statement s;
		
		try {
			s = QuartzCore.MySQLcore.openConnection().createStatement();
			ResultSet res = s.executeQuery("SELECT * FROM PlayerData WHERE id ='" + id + "';");
	        if(res.next()) {
	        	return res.getString(3);
	        } else {
	        	return null;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Updates the QuartzCraft PlayerData to set the connection status. 
	 * 
	 * @param player
	 * @param conn Connection status 
	 */
	public static void setConnectionStatus(Player player, boolean conn) {
		
	}
	
	/**
	 * Creates a player in the QuartzCore PlayerData database.
	 * 
	 * @param player
	 * @return boolean - true if player was created, false if otherwise
	 */
	public static boolean createPlayer(Player player) {
		long time = System.currentTimeMillis();
		//java.sql.Date date = new java.sql.Date(time);
		Date date = new Date(System.currentTimeMillis());
		
		UUID UUID = player.getUniqueId();
		String SUUID = UUID.toString();
		
		try {
			java.sql.Connection connection = QuartzCore.MySQLcore.openConnection();
			java.sql.PreparedStatement s = connection.prepareStatement("INSERT INTO PlayerData (UUID, DisplayName, JoinDate, PrimaryGroupID, PassedTutorial) VALUES ('" + SUUID +"', '" + player.getDisplayName() + "', ?, 9, 1);");
			s.setString(1, date.toString());
			if(s.executeUpdate( /* "INSERT INTO PlayerData (UUID, DisplayName, JoinDate, PrimaryGroupID, PassedTutorial) VALUES ('" + SUUID +"', '" + player.getDisplayName() + "', 9, 0);" */) == 1) {
				QPlayerCreationEvent event = new QPlayerCreationEvent(player);
				Bukkit.getServer().getPluginManager().callEvent(event);
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Sets the users primary group
	 * 
	 * @param playername
	 * @param GroupName
	 */
	public static boolean setPrimaryGroup(CommandSender sender, String playername, String GroupName, boolean promote) {
		String promoteCommand = plugin.getConfig().getString("settings.primary-promote-command");
		String demoteCommand = plugin.getConfig().getString("settings.primary-demote-command");
		promoteCommand.replaceAll("<group>", GroupName);
		promoteCommand.replaceAll("<user>", playername);
		demoteCommand.replaceAll("<group>", GroupName);
		demoteCommand.replaceAll("<user>", playername);
		boolean success = false;
		
		if(promote) {
			success = Bukkit.getServer().dispatchCommand(sender, promoteCommand);
		} else {
			success = Bukkit.getServer().dispatchCommand(sender, demoteCommand);
		}
		
		if(success) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Adds a secondary group for the user.
	 * 
	 * @param playername
	 * @param GroupName
	 */
	public static boolean addSecondaryGroup(CommandSender sender, String playername, String GroupName) {
		String promoteCommand = plugin.getConfig().getString("settings.secondary-promote-command");
		promoteCommand.replaceAll("<group>", GroupName);
		promoteCommand.replaceAll("<user>", playername);
		
		if(Bukkit.getServer().dispatchCommand(sender, promoteCommand)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Automatically manages a users group depending on their groups on the website.
	 * 
	 * @param player
	 * @return boolean - true if was successful, false if otherwise.
	 */
	public static boolean autoManageGroups(Player player) {
		String SUUID = player.getUniqueId().toString();
		String playername = player.getDisplayName().toString();
		try {
			java.sql.Connection connection = QuartzCore.MySQLweb.openConnection();
			java.sql.PreparedStatement secondary = connection.prepareStatement("SELECT user_group_id, secondary_group_ids FROM xf_user WHERE user_id = (SELECT user_id FROM xf_user_field_value WHERE field_id = 'Minecraft_Username' = ? LIMIT 1);");
			secondary.setString(1, playername);
			ResultSet res = secondary.executeQuery();
			if(res.next()) {
				int primary_group_id = res.getInt("user_group_id");
				String secondary_group_ids = res.getString("secondary_group_ids");
				String[] temp;
				temp = secondary_group_ids.split(", ", 25);
				int current = 0;
				
				//Remove groups
				setPrimaryGroup(plugin.getServer().getConsoleSender(), playername, "Member", true);
				
				//Set groups
				for(String id : temp) {
					String makeint = temp[current];
					int forswitch = Integer.parseInt(makeint);
					switch (forswitch) {
					case 3:
						setPrimaryGroup(plugin.getServer().getConsoleSender(), playername, "Admin", true);
						break;
					case 4:
						setPrimaryGroup(plugin.getServer().getConsoleSender(), playername, "Moderator", true);
						break;
					case 15:
						setPrimaryGroup(plugin.getServer().getConsoleSender(), playername, "Diamond", true);
						break;
					case 14:
						setPrimaryGroup(plugin.getServer().getConsoleSender(), playername, "Gold", true);
						break;
					case 13:
						setPrimaryGroup(plugin.getServer().getConsoleSender(), playername, "Iron", true);
						break;
					case 9:
						setPrimaryGroup(plugin.getServer().getConsoleSender(), playername, "SeniorStaff", true);
						break;
					case 5:
						setPrimaryGroup(plugin.getServer().getConsoleSender(), playername, "Owner", true);
						break;
					default:
						setPrimaryGroup(plugin.getServer().getConsoleSender(), playername, "Member", true);
						break;
					}
					current++;
				}
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gets the date the user was last seen
	 * 
	 * @param SUUID
	 * @return String of the last seen date in format - X days ago
	 */
	public static String getLastSeen(String SUUID) {
		String lastSeen = null;
		
		return lastSeen;
	}
	
	public abstract ResultSet getDataThisPlugin();
	
	public abstract boolean createPlayerThisPlugin();


}
