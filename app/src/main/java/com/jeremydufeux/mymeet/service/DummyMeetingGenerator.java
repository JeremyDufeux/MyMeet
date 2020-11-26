package com.jeremydufeux.mymeet.service;



import android.graphics.Color;

import com.jeremydufeux.mymeet.model.Meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DummyMeetingGenerator {

    private static List<Meeting> DUMMY_MEETINGS;

    private static void createMeeting(){
        String datePattern = "yyyy/MM/dd HH:mm";
        String durationPattern = "HH:mm";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern, Locale.getDefault());
        SimpleDateFormat durationFormatter = new SimpleDateFormat(durationPattern, Locale.getDefault());

        float[] hsl = new float[3];
        hsl[0] = 0.25f;
        hsl[1] = 1;
        hsl[2] = 0.4f;
        int colorAmount = 3;
        int[] colors = new int[colorAmount];
        for (int i = 0; i < colorAmount; i++) {
            colors[i] = Color.HSVToColor(hsl);
            hsl[0] += i* (int)(360f/colorAmount);
        }

        try {
            DUMMY_MEETINGS.add(new Meeting(
                    System.currentTimeMillis(),
                    "Duck jokes",
                    dateFormatter.parse("2020/11/28 12:55"),
                    durationFormatter.parse("01:00"),
                    new ArrayList<>(Arrays.asList("Me", "Me", "Me")),
                    0, colors[0]));

            DUMMY_MEETINGS.add(new Meeting(
                    System.currentTimeMillis(),
                    "Pasta cooking",
                    dateFormatter.parse("2020/11/28 13:55"),
                    durationFormatter.parse("00:30"),
                    new ArrayList<>(Arrays.asList("Me", "Mo", "Mi")),
                    0, colors[1]));

            DUMMY_MEETINGS.add(new Meeting(
                    System.currentTimeMillis(),
                    "Big machines",
                    dateFormatter.parse("2020/11/29 18:00"),
                    durationFormatter.parse("04:30"),
                    new ArrayList<>(Arrays.asList("Me", "Mo", "Mi", "Po")),
                    2, colors[2]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static List<Meeting> generateMeetings() {
        createMeeting();
        return DUMMY_MEETINGS;
    }
}