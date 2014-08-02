package uk.co.quartzcraft.core.systems.chat;

public class ChatFilter {

	/**
	 * Filters the chat to find any bad words, responds with either true or false
	 *
	 * @param msg
	 * @return boolean
	 */

	public boolean filterChatFind(String msg) {
		
		String[] wordsToFilter = null;
		
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
			if(msg.equalsIgnoreCase(s)) {
				return true;
			} else {
				return false;
			}
		}
		return true;
		
	}
}
