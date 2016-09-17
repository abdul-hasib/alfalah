package org.apps.alfalahindia.Util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatetimeUtil {
    static String TAG = DatetimeUtil.class.getSimpleName();

    /**
     * parses given string using dd/MM/yy format
     *
     * @param date - date to parse
     * @return - Date object
     */
    public static Date parseDate(String date) {
        SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yy");
        try {
            return parser.parse(date);
        } catch (ParseException e) {
            Log.e(TAG, "Exception in parsing date: " + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * formats to dd/MM/yy format
     *
     * @param milliSeconds - time in milli seconds
     * @return - date in dd/MM/yy format
     */
    public static String formatDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        return formatter.format(milliSeconds);
    }
}
