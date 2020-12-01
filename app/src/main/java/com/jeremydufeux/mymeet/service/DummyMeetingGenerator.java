package com.jeremydufeux.mymeet.service;


import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Participant;
import com.jeremydufeux.mymeet.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jeremydufeux.mymeet.utils.Tools.getCalendarFromDate;
import static com.jeremydufeux.mymeet.utils.Tools.getCalendarFromTime;

public class DummyMeetingGenerator {

    public static List<Meeting> generateMeetings(List<Room> rooms) {
        List<Meeting> dummyMeetings = new ArrayList<>();
        dummyMeetings.add(new Meeting(
                "Duck jokes",
                getCalendarFromDate(2020,12,1,12,0),
                getCalendarFromTime(1,0),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(0)));

        dummyMeetings.add(new Meeting(
                "Pasta cooking",
                getCalendarFromDate(2020,12,1,13,30),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(2)));

        dummyMeetings.add(new Meeting(
                "Big machines",
                getCalendarFromDate(2020,11,29,18,0),
                getCalendarFromTime(4,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(4)));

        dummyMeetings.add(new Meeting(
                "American elections",
                getCalendarFromDate(2020,11,30,11,0),
                getCalendarFromTime(2,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(8)));

        dummyMeetings.add(new Meeting(
                "Dogs and cats war",
                getCalendarFromDate(2021,12,11,9,0),
                getCalendarFromTime(1,0),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(9)));

        return dummyMeetings;
    }

}