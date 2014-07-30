package uk.co.quartzcraft.core.util;

import java.util.Date;
import java.sql.Timestamp;

public class Util {

    public static void printException(String message, Exception e) {

    }

    public static String colour(String s) {
        return ChatUtil.parse(s);
    }

    public static long timestamp() {
        java.util.Date date= new java.util.Date();
        Timestamp ts1 = new Timestamp(date.getTime());
        long tsTime1 = ts1.getTime();
        return tsTime1;
    }
}
