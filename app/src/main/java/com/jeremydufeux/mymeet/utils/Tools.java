package com.jeremydufeux.mymeet.utils;

import android.content.Context;
import android.content.res.ColorStateList;

import com.jeremydufeux.mymeet.R;

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
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static Calendar getCalendarFromTime(int hour, int minute){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 0);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static ColorStateList createChipStateColors(Context context) {
        int[][] backgroundStates = new int[][] {
                new int[] { android.R.attr.state_checked}, // checked
                new int[] { -android.R.attr.state_checked}  // unchecked
        };
        int[] backgroundColors = new int[] {
                context.getResources().getColor(R.color.colorPrimary),
                context.getResources().getColor(R.color.light_grey) // TODO color for night mode
        };
        return new ColorStateList(backgroundStates, backgroundColors);
    }
}
