package com.jeremydufeux.mymeet.utils;

import java.text.DateFormat;
import java.util.Calendar;

public class Tools {

    public static String getTimeFromCal(Calendar cal){
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.getTime());
    }

    public static String getDateFromCal(Calendar cal){
        return DateFormat.getDateInstance(DateFormat.SHORT).format(cal.getTime());
    }

    public static Calendar getCalendarFromDate(int year, int month, int day, int hour, int minute){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        return cal;
    }

    public static Calendar getCalendarFromTime(int hour, int minute){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 0);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        return cal;
    }
}
