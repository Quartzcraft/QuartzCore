package uk.co.quartzcraft.core.util;

import java.util.HashMap;

import uk.co.quartzcraft.core.QuartzCore;

public class ChatPhrase {
private static QuartzCore plugin;
	
	public ChatPhrase(QuartzCore plugin) {
        this.plugin = plugin;
    }
	
	private static HashMap<String, String> phrases = new HashMap<String, String>();
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
	
	/**
	 * Gets the requested phrase.
	 *
	 * @param requested_phrase_id
	 * @return phrase
	 */
	public static String getPhrase(String requested_phrase_id) {
		
		String phrase_key = match(requested_phrase_id);
		
		String phrase_value = getValue(phrase_key);
	
		String final_phrase = ChatUtil.parse(phrase_value);
		
		return final_phrase;
	}
	
	/**
	 * Adds a phrase to the phrase list.
	 *
	 * @param phrase_id
	 * @param phrase
	 */
	public static void addPhrase(String phrase_id, String phrase) {
        phrases.put(phrase_id, phrase);
	}
}
