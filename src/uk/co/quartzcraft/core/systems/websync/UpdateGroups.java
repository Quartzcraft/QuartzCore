package uk.co.quartzcraft.core.systems.websync;

import org.json.JSONObject;
import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.data.QPlayer;
import uk.co.quartzcraft.core.exception.NoValidGroupException;
import uk.co.quartzcraft.core.systems.perms.Groups;
import uk.co.quartzcraft.core.util.TaskChain;
import uk.co.quartzcraft.core.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateGroups {

    public static void update() {
        TaskChain.newChain().add(new TaskChain.AsyncFirstTask() {
            @Override
            protected QPlayer[] run() {
                QPlayer[] players = null;
                try {
                    ResultSet res = QuartzCore.DBCore.createStatement().executeQuery("SELECT * FROM PlayerData;");
                    players[0] = null;
                    while (res.next()) {
                        players[res.getRow()] = new QPlayer(res.getInt("id"));
                    }
                } catch (SQLException e) {
                    Util.printException("Failed to retrieve QPlayer from database", e);
                }
                return players;
            }
        }).add(new TaskChain.AsyncLastTask<QPlayer[]>() {
            @Override
            protected void run(QPlayer[] players) {
                for (QPlayer player : players) {
                    try {
                        JSONObject jsonObject = JSONMethods.decode(Websync.getUser(player.getName()));

                        // Getting the value of a attribute in a JSONObject
                        String groups = jsonObject.getString("secondary_group_ids");

                        String siteGroups = groups;
                        player.setPrimaryGroup(Groups.findGroupFromSite(siteGroups));
                    } catch(NoValidGroupException e) {
                        Util.printException("No valid groups", e);
                    }
                }
            }
        }).execute();
    }
}
