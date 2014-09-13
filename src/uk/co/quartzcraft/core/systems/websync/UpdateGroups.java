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
import java.util.ArrayList;

public class UpdateGroups {

    public static void update() {
        TaskChain.newChain().add(new TaskChain.AsyncFirstTask() {
            ArrayList<Integer> ids = new ArrayList();

            @Override
            protected ArrayList run() {
                try {
                    ResultSet res = QuartzCore.DBCore.createStatement().executeQuery("SELECT * FROM PlayerData;");
                    while (res.next()) {
                        ids.add(res.getInt("id"));
                    }
                } catch (SQLException e) {
                    Util.printException("Failed to retrieve QPlayer from database", e);
                }
                return ids;
            }
        }).add(new TaskChain.AsyncLastTask<ArrayList<Integer>>() {
            @Override
            protected void run(ArrayList<Integer> ids) {
                for (int id : ids) {
                    try {
                        QPlayer player = new QPlayer(id);
                        if(QuartzCore.plugin.getConfig().getBoolean("settings.websync.active")) {
                            JSONObject jsonObject = JSONMethods.decode(Websync.getUser(player.getName()));

                            // Getting the value of a attribute in a JSONObject
                            String groups = jsonObject.getString("secondary_group_ids");

                            String siteGroups = groups;
                            player.setPrimaryGroup(Groups.findGroupFromSite(siteGroups));
                        }
                    } catch(NoValidGroupException e) {
                        Util.printException("No valid groups", e);
                    }
                }
            }
        }).execute();
    }
}
