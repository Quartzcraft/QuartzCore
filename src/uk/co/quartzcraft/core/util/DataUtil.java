package uk.co.quartzcraft.core.util;

import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.ResultSet;

//TODO Use taskchain to perform searches
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
        return null;
    }

    /**
     * Updates the specified data in the specified table.
     *
     * @param table
     * @param values
     * @return
     */
    public boolean update(String table, String values) {
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
