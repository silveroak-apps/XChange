package org.knowm.xchange.btcmarkets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class BTCUtils {
    //2018-08-20T06:22:11.000000Z
    public static String toDateAsISO8601(long date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        df.setTimeZone(tz);
        return df.format(new java.util.Date(date));
    }

    public static Long toLongDate(String date) {
        try {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
            df.setTimeZone(tz);
            return df.parse(date).getTime();
        } catch (java.text.ParseException e) {
            return null;
        }
    }
}
