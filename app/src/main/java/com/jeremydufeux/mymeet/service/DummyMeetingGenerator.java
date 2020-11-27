package com.jeremydufeux.mymeet.service;



import android.graphics.Color;
import android.util.Log;

import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class DummyMeetingGenerator {
    private static List<Meeting> DUMMY_MEETINGS;

    public static List<Meeting> generateMeetings(List<Room> rooms) {
        DUMMY_MEETINGS = new ArrayList<>();
        String datePattern = "yyyy/MM/dd HH:mm";
        String durationPattern = "HH:mm";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern, Locale.getDefault());
        SimpleDateFormat durationFormatter = new SimpleDateFormat(durationPattern, Locale.getDefault());

        try {
            DUMMY_MEETINGS.add(new Meeting(
                    System.currentTimeMillis(),
                    "Duck jokes",
                    dateFormatter.parse("2020/11/28 12:55"),
                    durationFormatter.parse("01:00"),
                    new ArrayList<>(Arrays.asList("jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com")),
                    rooms.get(new Random().nextInt(rooms.size()))));

            DUMMY_MEETINGS.add(new Meeting(
                    System.currentTimeMillis(),
                    "Pasta cooking",
                    dateFormatter.parse("2020/11/28 13:55"),
                    durationFormatter.parse("00:30"),
                    new ArrayList<>(Arrays.asList("jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com")),
                    rooms.get(new Random().nextInt(rooms.size()))));

            DUMMY_MEETINGS.add(new Meeting(
                    System.currentTimeMillis(),
                    "Big machines",
                    dateFormatter.parse("2020/11/29 18:00"),
                    durationFormatter.parse("04:30"),
                    new ArrayList<>(Arrays.asList("jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com")),
                    rooms.get(new Random().nextInt(rooms.size()))));

            DUMMY_MEETINGS.add(new Meeting(
                    System.currentTimeMillis(),
                    "Trump's language",
                    dateFormatter.parse("2020/11/30 11:00"),
                    durationFormatter.parse("02:30"),
                    new ArrayList<>(Arrays.asList("jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com")),
                    rooms.get(new Random().nextInt(rooms.size()))));

            DUMMY_MEETINGS.add(new Meeting(
                    System.currentTimeMillis(),
                    "Dogs and cats war",
                    dateFormatter.parse("2020/12/11 09:00"),
                    durationFormatter.parse("01:00"),
                    new ArrayList<>(Arrays.asList("jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com")),
                    rooms.get(new Random().nextInt(rooms.size()))));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return DUMMY_MEETINGS;
    }
}