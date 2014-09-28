package uk.co.quartzcraft.core.systems.chat;

import java.util.HashMap;
import java.util.Map;

public class ChatPhrase {
    public static HashMap<String, String> phrases = new HashMap<String, String>();

    public static boolean match(String phrase_to_match) {

        for(String phrase_key : phrases.keySet()) {
            if(phrase_key.equalsIgnoreCase(phrase_to_match)) {
                return true;
            }
        }
        return false;
    }

    public static String getValue(String phrase_id_key) {

        return phrases.get(phrase_id_key);
    }

    public static String replaceVariables(String phrase_value, Map map_of_variables) {
        return null;
    }
}
