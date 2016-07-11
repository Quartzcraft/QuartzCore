package uk.co.quartzcraft.core.systems.websync;

import org.json.JSONException;
import org.json.JSONObject;
import uk.co.quartzcraft.core.util.Util;

public class JSONMethods {

    public static JSONObject decode(String s) {
        try {
           JSONObject json = new JSONObject(s);
        } catch(JSONException e) {
            Util.printException(e);
        }

        return null;
    }
}
