package uk.co.quartzcraft.core.util;

import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.QuartzCore;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//TODO Use taskchain to perform actions
/**
 * Access the database using simple methods
 */
public class DataUtil {

    private static Plugin plugin;
    private static Connection connection;

    public DataUtil(Plugin plugin, Connection connection) {
        this.plugin = plugin;
        this.connection = connection;
    }

    /**
     * Selects all data from the specified table depending on the where statements results.
     *
     * @param table
     * @param where
     * @return
     */
    public ResultSet selectWhere(String table, String where) {
        return null;
    }

    /**
     * Selects all data from the specified table.
     *
     * @param table
     * @return
     */
    public ResultSet select(String table) {
        Statement s;
        try {
            s = this.connection.createStatement();
            ResultSet res = s.executeQuery("SELECT * FROM " + table + ";");
            if(res.next()) {
                return res;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates the specified data in the specified table.
     *
     * @param table
     * @param values
     * @return
     */
    public boolean update(String table, String values, String where) {
        return false;
    }

    /**
     * Inserts the specified data into the specified table.
     *
     * @param table
     * @param values
     * @return
     */
    public boolean insert(String table, String values) {
        return false;
    }
}
