package com.jeremydufeux.mymeet.service;


import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class DummyMeetingGenerator {
    private static List<Meeting> DUMMY_MEETINGS;

    public static List<Meeting> generateMeetings(List<Room> rooms) {
        DUMMY_MEETINGS = new ArrayList<>();
        DUMMY_MEETINGS.add(new Meeting(
                "Duck jokes",
                calDateConverter(2020,11,28,12,55),
                calDurationConverter(1,0),
                new ArrayList<>(Arrays.asList("jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com")),
                rooms.get(new Random().nextInt(rooms.size()))));

        DUMMY_MEETINGS.add(new Meeting(
                "Pasta cooking",
                calDateConverter(2020,12,28,13,55),
                calDurationConverter(0,30),
                new ArrayList<>(Arrays.asList("jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com")),
                rooms.get(new Random().nextInt(rooms.size()))));

        DUMMY_MEETINGS.add(new Meeting(
                "Big machines",
                calDateConverter(2020,11,29,18,0),
                calDurationConverter(4,30),
                new ArrayList<>(Arrays.asList("jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com")),
                rooms.get(new Random().nextInt(rooms.size()))));

        DUMMY_MEETINGS.add(new Meeting(
                "Trump's language",
                calDateConverter(2020,11,30,11,0),
                calDurationConverter(2,30),
                new ArrayList<>(Arrays.asList("jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com")),
                rooms.get(new Random().nextInt(rooms.size()))));

        DUMMY_MEETINGS.add(new Meeting(
                "Dogs and cats war",
                calDateConverter(2021,12,11,9,0),
                calDurationConverter(1,0),
                new ArrayList<>(Arrays.asList("jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com", "jeremy.dufeux@gmail.com")),
                rooms.get(new Random().nextInt(rooms.size()))));

        return DUMMY_MEETINGS;
    }

    private static Calendar calDateConverter(int year, int month, int day, int hour, int minute){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        return cal;
    }
    private static Calendar calDurationConverter(int hour, int minute){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 0);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minute);
        return cal;
    }
}