package vies.uniba.it.vies.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Daniele on 27/03/2016.
 */
public class DateUtils {

    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    public static String formatLong(long dateInMillis) {
        return formatDate(new Date(dateInMillis), DEFAULT_DATE_FORMAT);
    }
    public static String formatLong(long dateInMillis, String pattern) {
        return formatDate(new Date(dateInMillis), pattern);
    }
    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_DATE_FORMAT);
    }
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
