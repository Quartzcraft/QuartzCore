package uk.co.quartzcraft.core.systems.chat;

public class QCChat {
	
	/**
	 * Gets the requested phrase.
	 *
	 * @param requested_phrase_id
	 * @return phrase
	 */
	public static String getPhrase(String requested_phrase_id) {
		
		String phrase_key = ChatPhrase.match(requested_phrase_id);
		
		String phrase_value = ChatPhrase.getValue(phrase_key);
	
		String final_phrase = ChatFormat.parse(phrase_value);
		
		return final_phrase;
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
