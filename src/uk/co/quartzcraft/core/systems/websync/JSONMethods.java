package uk.co.quartzcraft.core.systems.websync;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import uk.co.quartzcraft.core.util.Util;

public class JSONMethods {

    public static JSONArray decode(String s) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray)obj;

            return array;
        } catch(ParseException pe) {
            Util.printException("Failed to parse JSON", pe);

            return null;
        }
    }
}
