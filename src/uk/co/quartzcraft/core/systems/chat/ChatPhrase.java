package uk.co.quartzcraft.core.systems.chat;

import java.util.HashMap;

public class ChatPhrase {
    public static HashMap<String, String> phrases = new HashMap<String, String>();
    public static String error = "The requested phrase could not be found!";

    public static String match(String phrase_to_match) {
        boolean match = false;
        error = phrase_to_match;

        for(String phrase_key : phrases.keySet()) {
            if(phrase_key.equalsIgnoreCase(phrase_to_match)) {

                match = true;
                return phrase_key;
            }
        }

        if(!match) {
            return error;
        }
        return error;
    }

    public static String getValue(String phrase_id_key) {

        String value = phrases.get(phrase_id_key);

        return value;
    }
}
