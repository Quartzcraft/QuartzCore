package uk.co.quartzcraft;

public class QuartzCoreResponder {

	public static boolean respondToRequest(String request) {
		
		if(request.toString() == "Are you there?") {
			return true;
		} else {
			return false;
		}
	}
}
