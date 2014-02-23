package uk.co.quartzcraft.core.util;

public class Obfuscate {

	static String source="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	static String target="Q9A8ZWS7XEDC6RFVT5GBY4HNU3J2MI1KO0LP";

	public static String obfuscate(String s) {
	    char[] result= new char[10];
	    for (int i=0;i<s.length();i++) {
	        char c=s.charAt(i);
	        int index=source.indexOf(c);
	        result[i]=target.charAt(index);
	    }

	    return new String(result);
	}

	public static String unobfuscate(String s) {
	    char[] result= new char[10];
	    for (int i=0;i<s.length();i++) {
	        char c=s.charAt(i);
	        int index=target.indexOf(c);
	        result[i]=source.charAt(index);
	    }

	    return new String(result);
	}
}
