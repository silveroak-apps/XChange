package org.knowm.xchange.hitbtc.v2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class HitbtcUtil {

    public static String toDateAsISO8601(long date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        return df.format(new java.util.Date(date));
    }

    public static Long toLongDate(String date) {
        try {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            df.setTimeZone(tz);
            return df.parse(date).getTime();
        } catch (java.text.ParseException e) {
            return null;
        }
    }
}
