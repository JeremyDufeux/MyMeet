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
}
