package uk.co.quartzcraft.core.systems.websync;

import org.json.JSONObject;

public class JSONMethods {

    public static JSONObject decode(String s) {
        JSONObject json = new JSONObject(s);

        return json;
    }
}
