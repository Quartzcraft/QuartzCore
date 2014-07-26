package uk.co.quartzcraft.core.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.event.QPlayerCreationEvent;
import uk.co.quartzcraft.core.util.ChatUtil;

public class QPlayer {
	
	private static Plugin plugin;

    private static String name;
    private static UUID uuid;
    private static int id;
    private static String lastSeen;
    private static int tokens;
    private static Player player;
	
	public QPlayer(Plugin plugin, UUID uuid) {
        this.plugin = plugin;
        this.uuid = uuid;

        String SUUID = uuid.toString();
        try {
            ResultSet res = QuartzCore.DBCore.createStatement().executeQuery("SELECT * FROM PlayerData WHERE UUID='" + SUUID + "';");
            if(res.next()) {
                if (res.getString("UUID").equals(uuid)) {
                    this.id = res.getInt("id");
                    this.name = res.getString("DisplayName");
                    this.tokens = res.getInt("Tokens");
                } else {
                    QuartzCore.log.log(Level.SEVERE, "QPLAYER UUID NOT EQUAL");
                }
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        this.player = Bukkit.getPlayer(this.name);
	}

    public QPlayer(Plugin plugin, int id) {
        this.plugin = plugin;
        this.id = id;

        try {
            ResultSet res = QuartzCore.DBCore.createStatement().executeQuery("SELECT * FROM PlayerData WHERE id='" + id + "';");
            if(res.next()) {
                if (res.getInt("id") == id) {
                    this.name = res.getString("DisplayName");
                    this.tokens = res.getInt("Tokens");
                } else {
                    QuartzCore.log.log(Level.SEVERE, "QPLAYER ID NOT EQUAL");
                }
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        this.player = Bukkit.getPlayer(this.name);
        this.uuid = this.player.getUniqueId();
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
            java.sql.PreparedStatement s = QuartzCore.DBCore.prepareStatement("INSERT INTO PlayerData (UUID, DisplayName, JoinDate, PrimaryGroupID, PassedTutorial) VALUES ('" + SUUID + "', '" + player.getName() + "', ?, 9, 0);");
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
     * Find out whether the player already exisits in the database.
     *
     * @param uuid
     * @return true if exisits false if not.
     */
    public static boolean exisits(UUID uuid) {
        try {
            ResultSet res = QuartzCore.DBCore.createStatement().executeQuery("SELECT * FROM PlayerData WHERE UUID='" + uuid + "';");
            if(res.next()) {
                return true;
            } else {
                return false;
            }

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	/**
	 * Gets a users id from the PlayerData database
	 *
	 * @return id of the player
	 */
	public int getID() {
        return this.id;
	}
	
	/**
	 * Gets the name of the player.
	 *
	 * @return The display name of the player as defined in the QuartzCore database
	 */
	public String getName() {
		return this.name;
	}

    /**
     * Gets the Minecraft UUID of a player
     *
     * @return The UUID of the player
     */
    public UUID getUniqueId() {
        return this.uuid;
    }

    /**
     * Gets the number of tokens a player has.
     *
     * @return The number of tokens.
     */
    public int getTokens() {
        return this.tokens;
    }

    /**
     * Adds the specified number of tokens.
     *
     * @return The QPlayer object
     */
    public QPlayer addTokens(int num) {
        int ntok = this.tokens + num;
        try {
            java.sql.PreparedStatement s = QuartzCore.DBCore.prepareStatement("UPDATE PlayerData SET tokens=? WHERE id=?;");
            s.setInt(1, ntok);
            s.setInt(2, this.id);
            if(s.executeUpdate() == 1) {
                this.tokens = ntok;
                return this;
            } else {
                return this;
            }
        } catch(SQLException e) {
            return this;
        }
    }

    /**
     * Takes the specified number of tokens.
     *
     * @return The QPlayer object
     */
    public QPlayer takeTokens(int num) {
        int ntok = this.tokens - num;
        try {
            java.sql.PreparedStatement s = QuartzCore.DBCore.prepareStatement("UPDATE PlayerData SET tokens=? WHERE id=?;");
            s.setInt(1, ntok);
            s.setInt(2, this.id);
            if(s.executeUpdate() == 1) {
                this.tokens = ntok;
                return this;
            } else {
                return this;
            }
        } catch(SQLException e) {
            return this;
        }
    }

    /**
     * Returns the bukkit player object for the specified player.
     *
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Sends the player a message
     *
     */
    public void sendMessage(String message) {
        this.player.sendMessage(ChatUtil.colour(message));
    }

    /**
     * Gets the the time the player first joined.
     */
    public String getFirstJoin() {
        Player targetPlayer = this.player;

        long current = System.currentTimeMillis();
        long firstJoin = targetPlayer.getPlayer().getFirstPlayed();

        return DurationFormatUtils.formatPeriod(firstJoin, current, "d 'days' H 'hours'");
    }
	
	/**
	 * Updates the QuartzCraft PlayerData to set the connection status. 
	 *
	 * @param conn Connection status 
	 */
    //TODO Finish up
	public QPlayer setConnectionStatus(boolean conn) {
        long time = System.currentTimeMillis();
        Date date = new Date(System.currentTimeMillis());
		try {
            java.sql.PreparedStatement s = QuartzCore.DBCore.prepareStatement("UPDATE PlayerData SET LastSeen=? WHERE id=?;");
            s.setString(1, date.toString());
            s.setInt(2, this.id);
            if(s.executeUpdate() == 1) {
                //QPlayerLoginEvent event = new QPlayerLoginEvent(this);
                return this;
            } else {
                return this;
            }
        } catch (SQLException e) {
            return this;
        }
	}

	/**
	 * Sets the users primary group
	 *
	 * @param GroupName
	 */
	public boolean setPrimaryGroup(CommandSender sender, String GroupName, boolean promote, Plugin plugin) {
		String promoteCommand = plugin.getConfig().getString("settings.primary-promote-command");
		String demoteCommand = plugin.getConfig().getString("settings.primary-demote-command");
		promoteCommand.replaceAll("<group>", GroupName);
		promoteCommand.replaceAll("<user>", this.name);
		//demoteCommand.replaceAll("<group>", GroupName);
		demoteCommand.replaceAll("<user>", this.name);
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
	 * @param GroupName
	 */
	public boolean addSecondaryGroup(CommandSender sender, String GroupName, boolean promote, Plugin plugin) {
		String promoteCommand = plugin.getConfig().getString("settings.secondary-promote-command");
        String demoteCommand = plugin.getConfig().getString("settings.secondary-demote-command");
		promoteCommand.replaceAll("<group>", GroupName);
		promoteCommand.replaceAll("<user>", this.name);
        demoteCommand.replaceAll("<group>", GroupName);
        demoteCommand.replaceAll("<user>", this.name);

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
	 * @return boolean - true if was successful, false if otherwise.
	 */
	public boolean autoManageGroups() {
		//TODO Need API Key and JSON stuffs
		String SUUID = this.uuid.toString();
		String playername = this.name;
        QPlayer player = this;
		String apiAction = "http://quartzcraft.co.uk/api.php?action=getUser&value=" + playername + "&hash=API_KEY";

		String secondary_group_ids = null;
		String[] temp;
		temp = secondary_group_ids.split(",", 25);
		int current = 0;
				
		//Remove groups
		player.setPrimaryGroup(Bukkit.getServer().getConsoleSender(), "Member", true, plugin);
		
		//Set groups
		for(String id : temp) {
                	int forswitch = 0;
	        	try {
	                        String makeint = temp[current];
	                        forswitch = Integer.parseInt(makeint);
	               	} catch (NumberFormatException e) {
	                        QuartzCore.log.info("[QC] autoManageGroups failed!");
	                        e.printStackTrace();
	                        return false;
	       		}
			switch (forswitch) {
	                case 3:
                        player.setPrimaryGroup(Bukkit.getServer().getConsoleSender(), "Admin", true, plugin);
	                	break;
	                case 4:
                        player.setPrimaryGroup(Bukkit.getServer().getConsoleSender(), "Moderator", true, plugin);
	                	break;
	                case 15:
                        player.setPrimaryGroup(Bukkit.getServer().getConsoleSender(), "Diamond", true, plugin);
	                    break;
	                case 14:
                        player.setPrimaryGroup(Bukkit.getServer().getConsoleSender(), "Gold", true, plugin);
	                    break;
	                case 13:
                        player.setPrimaryGroup(Bukkit.getServer().getConsoleSender(), "Iron", true, plugin);
	                    break;
	                case 9:
                        player.setPrimaryGroup(Bukkit.getServer().getConsoleSender(), "SeniorStaff", true, plugin);
	                    break;
	                case 5:
                        player.setPrimaryGroup(Bukkit.getServer().getConsoleSender(), "Owner", true, plugin);
	                    break;
	                default:
                        player.setPrimaryGroup(Bukkit.getServer().getConsoleSender(), "Member", true, plugin);
	                    break;
			}
			current++;
		}
        return false;
	}

    /**
     * Gets the date the user was last seen
     *
     * @return String of the last seen date in X days/hours/minutes ago format.
     */
    public String getLastSeen() {
        return this.lastSeen;
    }
	
	public boolean createValidationCode() {
		String validationCode;
		String playername = this.name;
        String UUID = this.uuid.toString();
        String code = UUID.substring(0, 8);
		//String hashedUsername = Obfuscate.obfuscate(playername);
		//validationCode = Integer.toString(QPlayer.getID(player)) + "-" + hashedUsername;
        validationCode = Integer.toString(this.id) + "-" + code;

		try {
			java.sql.PreparedStatement s = QuartzCore.DBCore.prepareStatement("INSERT INTO validationCodes (user_id, code) VALUES (" + this.id +", '" + validationCode + "');");
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
	
	public String getValidationCode() {
		try {
			ResultSet res = QuartzCore.DBCore.createStatement().executeQuery("SELECT * FROM validationCodes WHERE user_id ='" + this.id + "';");
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
