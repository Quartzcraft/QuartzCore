package uk.co.quartzcraft.core.systems.chat;

import java.util.Map;

public class QCChat {
	
	/**
	 * Gets the requested phrase.
	 *
	 * @param requested_phrase_id
	 * @return phrase
	 */
	public static String getPhrase(String requested_phrase_id) {
		
		if(ChatPhrase.match(requested_phrase_id)) {
            String phrase_value = ChatPhrase.getValue(requested_phrase_id);

            return ChatFormat.parse(phrase_value);
        }
		return requested_phrase_id;
	}

    /**
     * Gets the requested phrase. Has input for variables.
     *
     * @param requested_phrase_id
     * @return phrase
     */
    public static String getPhrase(String requested_phrase_id, Map map_of_variables) {

        if(ChatPhrase.match(requested_phrase_id)) {
            String phrase_value = ChatPhrase.getValue(requested_phrase_id);
            String final_value = ChatPhrase.replaceVariables(phrase_value, map_of_variables);

            return ChatFormat.parse(phrase_value);
        }
        return requested_phrase_id;
    }
	
	/**
	 * Adds a phrase to the phrase list.
	 *
	 * @param phrase_id
	 * @param phrase
	 */
	public static void addPhrase(String phrase_id, String phrase) {
        ChatPhrase.phrases.put(phrase_id, phrase);
	}

    /**
     * Creates a new PreparedMessage object
     *
     * @return
     */
    public static PreparedMessage prepareMessage() {
        return new PreparedMessage();
    }

    /**
     * Creates a new PreparedMessage object
     *
     * @return
     */
    public static PreparedMessage prepareMessage(String message) {
        return new PreparedMessage(message);
    }
}
