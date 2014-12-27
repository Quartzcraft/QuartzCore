package uk.co.quartzcraft.core.systems.chat;

public class ChatFilter {

	/**
	 * Filters the chat to find any bad words, responds with either true or false
	 *
	 * @param message The message to be filtered
	 * @return boolean
	 */
	public static boolean filterChatFind(String message) {
		
		String[] wordsToFilter = new String[15];
		
		wordsToFilter[0] = "fuck";
		wordsToFilter[1] = "shit";
		wordsToFilter[2] = "nigga";
		wordsToFilter[3] = "nigger";
		wordsToFilter[4] = "bullshit";
		wordsToFilter[5] = "motherfucker";
		wordsToFilter[6] = "fucker";
		wordsToFilter[7] = "fucking";
		wordsToFilter[8] = "motherfucking";
		wordsToFilter[9] = "cunt";
		wordsToFilter[10] = "dickhead";
		
		for(String s : wordsToFilter) {
			if(message.toLowerCase().contains(s)) {
				return true;
			}
		}
		return true;
		
	}
}
