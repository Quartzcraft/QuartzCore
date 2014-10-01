package uk.co.quartzcraft.core.systems.perms;

import uk.co.quartzcraft.core.QuartzCore;
import uk.co.quartzcraft.core.exception.NoValidGroupException;
import uk.co.quartzcraft.core.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Groups {

    public static Map<Integer, List<String>> getGroups() {
        Map<Integer, List<String>> groups = new HashMap<Integer, List<String>>();

        try {
            ResultSet res = QuartzCore.DBCore.createStatement().executeQuery("SELECT * FROM Groups");
            while(res.next()) {
                if(res.getInt("id") != 0) {
                    List<String> valSet = new ArrayList<String>();
                    valSet.add(res.getString("group_name"));
                    valSet.add(res.getString("site_group_id"));
                    valSet.add(res.getString("full_group_name"));
                    valSet.add(res.getString("group_colour"));
                    valSet.add(res.getString("group_prefix"));
                    groups.put(res.getInt("id"), valSet);
                }
            }
            return groups;
        } catch(SQLException e) {
            Util.printException("Could not retrieve groups from database", e);
            return null;
        }
    }

    public static boolean exists(int groupId) {
        try {
            ResultSet res = QuartzCore.DBCore.createStatement().executeQuery("SELECT * FROM Groups");
            while(res.next()) {
                if(groupId == res.getInt("id")) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch(SQLException e) {
            Util.printException("Could not retrieve groups from database", e);
            return false;
        }
        return false;
    }

    public static int findGroupFromSite(String siteGroupId) throws NoValidGroupException {
        Map<Integer, List<String>> groups = getGroups();

        for(int i=0; i<101; i++) {
            if(groups.get(i) != null) {
                List<String> values = groups.get(i);
                String site_group_ids = values.get(2);
                if(site_group_ids.contains(",")) {
                    String[] parts = site_group_ids.split(",");
                    for(String part : parts) {
                        if(part.equals(siteGroupId)) {
                            return i;
                        }
                    }
                } else {
                    if(site_group_ids.equals(siteGroupId)) {
                        return i;
                    }
                }
            }
        }
        new NoValidGroupException();
        return 0;
    }
}
