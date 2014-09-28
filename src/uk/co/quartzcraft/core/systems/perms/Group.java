package uk.co.quartzcraft.core.systems.perms;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class Group {

    private Plugin plugin = QuartzCore.plugin;

    private int id;
    private int site_id;
    private String name;
    private String fullName;
    private String prefix;
    private ChatColor colour;

    public Group(int id) {
        this.id = id;

        try {
            PreparedStatement s = QuartzCore.DBCore.prepareStatement("SELECT * FROM Groups WHERE id=?;");
            s.setInt(1, id);
            ResultSet res = s.executeQuery();
            if(res.next()) {
                if(res.getInt("id") == id) {
                    this.name = res.getString("group_name");
                    this.fullName = res.getString("full_group_name");
                    this.site_id = res.getInt("site_group_id");
                    this.prefix = res.getString("group_prefix");
                    this.colour = ChatColor.getByChar(res.getString("group_colour"));
                } else {
                    Util.log(Level.SEVERE, "Group id not equal");
                }
            }

        } catch(SQLException e) {
            Util.printException("Failed to retrieve Group from database", e);
        }
    }

    public Group(String groupName) {
        this.name = groupName;

        try {
            PreparedStatement s = QuartzCore.DBCore.prepareStatement("SELECT * FROM Groups WHERE group_name=?;");
            s.setString(1, groupName);
            ResultSet res = s.executeQuery();
            if(res.next()) {
                if(res.getString("group_name").equalsIgnoreCase(groupName)) {
                    this.id = res.getInt("id");
                    this.fullName = res.getString("full_group_name");
                    this.site_id = res.getInt("site_group_id");
                    this.prefix = res.getString("group_prefix");
                    this.colour = ChatColor.getByChar("&" + res.getString("group_colour"));
                } else {
                    Util.log(Level.SEVERE, "Group id not equal");
                }
            }

        } catch(SQLException e) {
            Util.printException("Failed to retrieve Group from database", e);
        }
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public ChatColor getColour() {
        return this.colour;
    }

    public String getFancyName() {
        return this.colour + this.fullName;
    }
}
