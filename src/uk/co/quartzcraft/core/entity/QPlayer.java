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
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.chat.*;
import uk.co.quartzcraft.core.event.QPlayerCreationEvent;
import uk.co.quartzcraft.core.event.QPlayerLoginEvent;
import uk.co.quartzcraft.core.util.Obfuscate;

public class QPlayer {
	
	private static QuartzCore plugin;
	
	public void QPlayer(QuartzCore plugin) {
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
    public static int getUserID(OfflinePlayer player) {
        //String SUUID = player.getUniqueId().toString();
        String playername = player.getName();

        Statement s;
        try {
            s = QuartzCore.MySQLcore.openConnection().createStatement();
            ResultSet res = s.executeQuery("SELECT FROM PlayerData WHERE DisplayName='" + playername + "';");
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
	 * @param player The player that is being updated
	 * @param conn Connection status 
	 */
	public static void setConnectionStatus(Player player, boolean conn) {
        long time = System.currentTimeMillis();
        Date date = new Date(System.currentTimeMillis());
		try {
            java.sql.Connection connection = QuartzCore.MySQLcore.openConnection();
            java.sql.PreparedStatement s = connection.prepareStatement("UPDATE PlayerData SET LastSeen=" + date + " WHERE id=" + getUserID(player) + ";");
            s.setString(1, date.toString());
            if(s.executeUpdate() == 1) {
                QPlayerLoginEvent event = new QPlayerLoginEvent(player);
            } else {

            }
        } catch (SQLException e) {

        }
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
			java.sql.PreparedStatement s = connection.prepareStatement("INSERT INTO PlayerData (UUID, DisplayName, JoinDate, PrimaryGroupID, PassedTutorial) VALUES ('" + SUUID +"', '" + player.getName() + "', ?, 9, 0);");
			s.setString(1, date.toString());
			if(s.executeUpdate() == 1) {
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
	public static boolean setPrimaryGroup(CommandSender sender, String playername, String GroupName, boolean promote, Plugin plugin) {
		String promoteCommand = plugin.getConfig().getString("settings.primary-promote-command");
		String demoteCommand = plugin.getConfig().getString("settings.primary-demote-command");
		promoteCommand.replaceAll("<group>", GroupName);
		promoteCommand.replaceAll("<user>", playername);
		//demoteCommand.replaceAll("<group>", GroupName);
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
	public static boolean addSecondaryGroup(CommandSender sender, String playername, String GroupName, boolean promote, Plugin plugin) {
		String promoteCommand = plugin.getConfig().getString("settings.secondary-promote-command");
        String demoteCommand = plugin.getConfig().getString("settings.secondary-demote-command");
		promoteCommand.replaceAll("<group>", GroupName);
		promoteCommand.replaceAll("<user>", playername);
        demoteCommand.replaceAll("<group>", GroupName);
        demoteCommand.replaceAll("<user>", playername);

        if(promote) {
            if(Bukkit.getServer().dispatchCommand(sender, promoteCommand)) {
                return true;
            } else {
                return false;
            }
        } else {
            if(Bukkit.getServer().dispatchCommand(sender, demoteCommand)) {
                return true;
            } else {
                return false;
            }
        }
	}
	
	/**
	 * Automatically manages a users group depending on their groups on the website.
	 * 
	 * @param player
	 * @return boolean - true if was successful, false if otherwise.
	 */
	public static boolean autoManageGroups(Player player, QuartzCore plugin) {
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
				temp = secondary_group_ids.split(",", 25);
				int current = 0;
				
				//Remove groups
				setPrimaryGroup(Bukkit.getServer().getConsoleSender(), playername, "Member", true, plugin);
				
				//Set groups
				for(String id : temp) {
                    int forswitch = 0;
                    try {
                        String makeint = temp[current];
                        forswitch = Integer.parseInt(makeint);
                    } catch (NumberFormatException e) {
                        plugin.log.info("[QC] autoManageGroups failed!");
                        e.printStackTrace();
                        return false;
                    }
					switch (forswitch) {
                        case 3:
                            setPrimaryGroup(Bukkit.getServer().getConsoleSender(), playername, "Admin", true, plugin);
                            break;
                        case 4:
                            setPrimaryGroup(Bukkit.getServer().getConsoleSender(), playername, "Moderator", true, plugin);
                            break;
                        case 15:
                            setPrimaryGroup(Bukkit.getServer().getConsoleSender(), playername, "Diamond", true, plugin);
                            break;
                        case 14:
                            setPrimaryGroup(Bukkit.getServer().getConsoleSender(), playername, "Gold", true, plugin);
                            break;
                        case 13:
                            setPrimaryGroup(Bukkit.getServer().getConsoleSender(), playername, "Iron", true, plugin);
                            break;
                        case 9:
                            setPrimaryGroup(Bukkit.getServer().getConsoleSender(), playername, "SeniorStaff", true, plugin);
                            break;
                        case 5:
                            setPrimaryGroup(Bukkit.getServer().getConsoleSender(), playername, "Owner", true, plugin);
                            break;
                        default:
                            setPrimaryGroup(Bukkit.getServer().getConsoleSender(), playername, "Member", true, plugin);
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
	 * @param player
	 * @return String of the last seen date in default bukkit format
	 */
	public static String getLastSeen(OfflinePlayer player) {
		String lastSeen = null;
		
		return lastSeen;
	}

    /**
     * Gets the date the user was last seen
     *
     * @param player
     * @return String of the last seen date in X days/hours/minutes ago format.
     */
    public static String getLastSeen(Player player) {
        String lastSeen = null;

        return lastSeen;
    }
	
	public static boolean createValidationCode(Player player) {
		String validationCode = null;
		String playername = player.getDisplayName();
        String UUID = validationCode = player.getUniqueId().toString();
        String code = UUID.substring(0, 8);
		//String hashedUsername = Obfuscate.obfuscate(playername);
		//validationCode = Integer.toString(QPlayer.getUserID(player)) + "-" + hashedUsername;
        validationCode = Integer.toString(QPlayer.getUserID(player)) + "-" + code;

		try {
			java.sql.Connection connection = QuartzCore.MySQLcore.openConnection();
			java.sql.PreparedStatement s = connection.prepareStatement("INSERT INTO validationCodes (user_id, code) VALUES (" + QPlayer.getUserID(player) +", '" + validationCode + "');");
			if(s.executeUpdate() == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String getValidationCode(Player player) {
		Statement s;
		
		try {
			s = QuartzCore.MySQLcore.openConnection().createStatement();
			ResultSet res = s.executeQuery("SELECT * FROM validationCodes WHERE user_id ='" + QPlayer.getUserID(player) + "';");
	        if(res.next()) {
	        	return res.getString("code");
	        } else {
	        	return null;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
