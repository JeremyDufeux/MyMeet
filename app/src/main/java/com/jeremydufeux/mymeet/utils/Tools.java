package com.jeremydufeux.mymeet.utils;

import android.content.Context;
import android.content.res.ColorStateList;

import com.jeremydufeux.mymeet.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Tools {

    public static String getTimeFromCal(Calendar cal){  // Get a string representation of time of the given Calendar
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.getTime());
    }

    public static String getDateFromCal(Calendar cal){  // Get a string representation of date of the given Calendar
        return DateFormat.getDateInstance(DateFormat.SHORT).format(cal.getTime());
    }

    public static Calendar getCalendarFromDate(int year, int month, int day, int hour, int minute){ // get calendar a from year, month, day, hour and minute
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        return cal;
    }

    public static Calendar getCalendarFromTime(int hour, int minute){ // get calendar a from hour and minute
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        return cal;
    }

    public static boolean isSameDay(Calendar calendarA, Calendar calendarB){ // Compare calendars to check if they are same day
        return calendarA.get(Calendar.YEAR) == calendarB.get(Calendar.YEAR) &&
                calendarA.get(Calendar.MONTH) == calendarB.get(Calendar.MONTH) &&
                calendarA.get(Calendar.DAY_OF_MONTH) == calendarB.get(Calendar.DAY_OF_MONTH);
    }

    public static ColorStateList createChipStateColors(Context context) { // Generate states colors for chip with theme colors for check state
        int[][] backgroundStates = new int[][] {
                new int[] { android.R.attr.state_checked}, // checked
                new int[] { -android.R.attr.state_checked}  // unchecked
        };
        int[] backgroundColors = new int[] {
                context.getResources().getColor(R.color.colorPrimary),
                context.getResources().getColor(R.color.chip_background)
        };
        return new ColorStateList(backgroundStates, backgroundColors);
    }

    public static int HSBtoRGB(float hue, float saturation, float brightness) { // Method from java.awt.Color to get RGB color From HSB
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float)Math.floor(hue)) * 6.0f;
            float f = h - (float)java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
                case 0:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (t * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 1:
                    r = (int) (q * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 2:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (t * 255.0f + 0.5f);
                    break;
                case 3:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (q * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 4:
                    r = (int) (t * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 5:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (q * 255.0f + 0.5f);
                    break;
            }
        }
        return 0xff000000 | (r << 16) | (g << 8) | (b);
    }

    public static boolean checkEmail(String email){ // Check if string is a valid email address
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}