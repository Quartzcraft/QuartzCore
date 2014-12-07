package uk.co.quartzcraft.core.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;

import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.event.QPlayerCreationEvent;
import uk.co.quartzcraft.core.event.QPlayerGroupChangeEvent;
import uk.co.quartzcraft.core.systems.chat.QCChat;
import uk.co.quartzcraft.core.systems.perms.Group;
import uk.co.quartzcraft.core.util.TaskChain;
import uk.co.quartzcraft.core.util.Util;

public class QPlayer {
	
	private Plugin plugin = QuartzCore.plugin;

    private String name;
    private UUID uuid;
    private int id;
    private Timestamp lastSeen;
    private int tokens;
    private Player player = null;
    private Group group;

    /**
     * Creates a QPlayer object using the specified player
     *
     * @param player
     */
	public QPlayer(Player player) {
        this.uuid = player.getUniqueId();
        String SUUID = uuid.toString();
        try {
            PreparedStatement s = QuartzCore.DBCore.prepareStatement("SELECT * FROM PlayerData WHERE UUID=?;");
            s.setString(1, SUUID);
            ResultSet res = s.executeQuery();
            if(res.next()) {
                if (res.getString("UUID").equals(uuid.toString())) {
                    this.id = res.getInt("id");
                    this.name = res.getString("DisplayName");
                    this.tokens = res.getInt("Tokens");
                    this.group = new Group(res.getInt("PrimaryGroupId"));
                    this.lastSeen = res.getTimestamp("LastSeen");
                } else {
                    Util.log(Level.SEVERE, "QPlayer UUID not equal");
                }
            }

        } catch(SQLException e) {
            Util.printException("Failed to retrieve QPlayer from database", e);
        }
        this.player = player;
	}

    /**
     * Creates a QPlayer object using the specified player name.
     *
     * @param name
     */
    public QPlayer(String name) {
        try {
            PreparedStatement s = QuartzCore.DBCore.prepareStatement("SELECT * FROM PlayerData WHERE DisplayName='?';");
            s.setString(1, name);
            ResultSet res = s.executeQuery();
            if(res.next()) {
                this.id = res.getInt("id");
                this.name = res.getString("DisplayName");
                this.tokens = res.getInt("Tokens");
                this.group = new Group(res.getInt("PrimaryGroupId"));
                this.uuid = UUID.fromString(res.getString("UUID"));
                this.lastSeen = res.getTimestamp("LastSeen");
            }

        } catch(SQLException e) {
            Util.printException("Failed to retrieve QPlayer from database", e);
        }

        if(Bukkit.getServer().getPlayer(this.uuid) != null) {
            this.player = Bukkit.getServer().getPlayer(this.uuid);
        }
    }

    /**
     * Creates a QPlayer object using the specified id
     *
     * @param id
     */
    public QPlayer(int id) {
        this.id = id;

        try {
            PreparedStatement s = QuartzCore.DBCore.prepareStatement("SELECT * FROM PlayerData WHERE id=?;");
            s.setInt(1, id);
            ResultSet res = s.executeQuery();
            if(res.next()) {
                if (res.getInt("id") == id) {
                    this.name = res.getString("DisplayName");
                    this.tokens = res.getInt("Tokens");
                    this.group = new Group(res.getInt("PrimaryGroupId"));
                    this.uuid = UUID.fromString(res.getString("UUID"));
                    this.lastSeen = res.getTimestamp("LastSeen");
                } else {
                    Util.log(Level.SEVERE, "QPlayer ID not equal");
                }
            }

        } catch(SQLException e) {
            Util.printException("Failed to retrieve QPlayer from database", e);
        }

        if(Bukkit.getServer().getPlayer(this.uuid) != null) {
            this.player = Bukkit.getServer().getPlayer(this.uuid);
        }
    }

    /**
     * Creates a player in the QuartzCore PlayerData database.
     *
     * @param player
     * @return boolean - true if player was created, false if otherwise
     */
    public static boolean createPlayer(Player player) {
        Long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);

        UUID UUID = player.getUniqueId();
        String SUUID = UUID.toString();

        try {
            java.sql.PreparedStatement s = QuartzCore.DBCore.prepareStatement("INSERT INTO PlayerData (UUID, DisplayName, JoinDate) VALUES (?, ?, ?);");
            s.setString(1, SUUID);
            s.setString(2, player.getName());
            s.setTimestamp(3, timestamp);
            if(s.executeUpdate() == 1) {
                QPlayerCreationEvent event = new QPlayerCreationEvent(new QPlayer(player));
                Bukkit.getServer().getPluginManager().callEvent(event);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            Util.printException("Failed to insert QPlayer into database", e);
            return false;
        }
    }

    /**
     * Find out whether the player already exists in the database.
     *
     * @param uuid
     * @return true if exists false if not.
     */
    public static boolean exists(UUID uuid) {
        try {
            ResultSet res = QuartzCore.DBCore.createStatement().executeQuery("SELECT * FROM PlayerData WHERE UUID='" + uuid + "';");
            if(res.next()) {
                return true;
            } else {
                return false;
            }

        } catch(SQLException e) {
            Util.printException("Failed to retrieve QPlayer data from database", e);
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
     * Gets the name of the player with full group styling.
     *
     * @return The display name of the player as defined in the QuartzCore database
     */
    public String getFancyName() {
        return this.group.getStyleForName() + this.name;
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
            Util.printException("Failed to retrieve QPlayer data from database", e);
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
            Util.printException("Failed to retrieve QPlayer data from database", e);
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
        if(this.player != null) {
            Util.sendMsg(this.player, message);
        }
    }

    /**
     * Gets the the time the player first joined.
     */
    public String getFirstJoin() {
        try {
            java.sql.PreparedStatement s = QuartzCore.DBCore.prepareStatement("SELECT * FROM PlayerData WHERE id=?;");
            s.setInt(1, this.id);
            ResultSet res = s.executeQuery();
            if(res.next()) {
                long firstJoin = res.getTimestamp("JoinDate").getTime();
                long current = System.currentTimeMillis();
                return DurationFormatUtils.formatPeriod(firstJoin, current, "d 'days' H 'hours'");
            }
        } catch (SQLException e) {
            Util.printException("Failed to retrieve QPlayer data from database", e);
            return null;
        }
        return null;
    }

    /**
     * Gets the users primary group.
     */
    public Group getGroup() {
        return this.group;
    }

	/**
	 * Sets the users primary group.
	 *
	 * @param groupId
	 */
	public QPlayer setPrimaryGroup(int groupId) {
        try {
            java.sql.PreparedStatement s = QuartzCore.DBCore.prepareStatement("UPDATE PlayerData SET PrimaryGroupId=? WHERE id=?;");
            s.setInt(1, groupId);
            s.setInt(2, this.id);
            if(s.executeUpdate() == 1) {
                QPlayerGroupChangeEvent event = new QPlayerGroupChangeEvent(this);
                this.group = new Group(groupId);
                return this;
            } else {
                return this;
            }
        } catch (SQLException e) {
            Util.printException("Failed to set users primary user group", e);
            return this;
        }
	}
	
	/**
	 * Adds a secondary group for the user.
	 *
	 * @param groupId
     * @deprecated Don't use this for now.
	 */
	public QPlayer addSecondaryGroup(int groupId) {

		return this;
	}

    public void report(QPlayer rep, String reason) {
        final QPlayer reported_user = this;
        final QPlayer reporting_user = rep;
        final String freason = reason;

        TaskChain.newChain().add(new TaskChain.AsyncGenericTask() {
            @Override
            protected void run() {
                try {
                    java.sql.Connection connection = QuartzCore.MySQLcore.openConnection();
                    java.sql.PreparedStatement s = connection.prepareStatement("INSERT INTO Reports (reported_user_id, reporting_user_id, report_content) VALUES (?, ?, ?);");
                    s.setInt(1, reported_user.getID());
                    s.setInt(2, reporting_user.getID());
                    s.setString(3, freason);
                } catch (SQLException e) {
                    Util.printException("Failed to insert report into database", e);
                    reported_user.sendMessage(QCChat.getPhrase("database_error_contact"));
                }
            }
        }).execute();
    }

    /**
     * Gets the date the user was last seen
     *
     * @return String of the last seen date in X days/hours/minutes ago format.
     */
    public String getLastSeen() {
        long lastSeen = this.lastSeen.getTime();
        long current = System.currentTimeMillis();
        return DurationFormatUtils.formatPeriod(lastSeen, current, "d 'days' H 'hours'");
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
            Util.printException("Failed to retrieve QPlayer data from database", e);
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
            Util.printException("Failed to retrieve QPlayer data from database", e);
			return null;
		}
	}

}
