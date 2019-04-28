package com.pinwheel.anabel.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
public class DateUtils {

    public static Date expire(int lifetime) {
        final Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, lifetime);

        return new Date(cal.getTime().getTime());
    }
}
