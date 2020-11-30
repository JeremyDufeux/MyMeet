package com.jeremydufeux.mymeet.service;


import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Participant;
import com.jeremydufeux.mymeet.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.jeremydufeux.mymeet.utils.Tools.getCalendarFromDate;
import static com.jeremydufeux.mymeet.utils.Tools.getCalendarFromTime;

public class DummyMeetingGenerator {
    private static List<Meeting> DUMMY_MEETINGS;

    public static List<Meeting> generateMeetings(List<Room> rooms) {
        DUMMY_MEETINGS = new ArrayList<>();
        DUMMY_MEETINGS.add(new Meeting(
                "Duck jokes",
                getCalendarFromDate(2020,11,28,12,55),
                getCalendarFromTime(1,0),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(new Random().nextInt(rooms.size()))));

        DUMMY_MEETINGS.add(new Meeting(
                "Pasta cooking",
                getCalendarFromDate(2020,12,28,13,55),
                getCalendarFromTime(0,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(new Random().nextInt(rooms.size()))));

        DUMMY_MEETINGS.add(new Meeting(
                "Big machines",
                getCalendarFromDate(2020,11,29,18,0),
                getCalendarFromTime(4,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(new Random().nextInt(rooms.size()))));

        DUMMY_MEETINGS.add(new Meeting(
                "Trump's tweets",
                getCalendarFromDate(2020,11,30,11,0),
                getCalendarFromTime(2,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(new Random().nextInt(rooms.size()))));

        DUMMY_MEETINGS.add(new Meeting(
                "Dogs and cats war",
                getCalendarFromDate(2021,12,11,9,0),
                getCalendarFromTime(1,0),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(new Random().nextInt(rooms.size()))));

        return DUMMY_MEETINGS;
    }

}