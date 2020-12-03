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
    public static List<Meeting> DUMMY_MEETING_LIST;

    public static List<Meeting> generateMeetings(List<Room> rooms) {
        DUMMY_MEETING_LIST = new ArrayList<>();
        DUMMY_MEETING_LIST.add(new Meeting(
                "Duck jokes",
                getCalendarFromDate(2019,11,1,12,0),
                getCalendarFromTime(1,0),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(0)));

        DUMMY_MEETING_LIST.add(new Meeting(
                "Cooking pasta art",
                getCalendarFromDate(2020,11,1,14,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(0)));

        DUMMY_MEETING_LIST.add(new Meeting(
                "Horror movies",
                getCalendarFromDate(2020,11,3,14,0),
                getCalendarFromTime(2,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(2)));

        DUMMY_MEETING_LIST.add(new Meeting(
                "Heavy machines",
                getCalendarFromDate(2020,11,29,18,0),
                getCalendarFromTime(4,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(4)));

        DUMMY_MEETING_LIST.add(new Meeting(
                "US election",
                getCalendarFromDate(2020,10,30,11,0),
                getCalendarFromTime(2,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(8)));

        DUMMY_MEETING_LIST.add(new Meeting(
                "Dogs vs cats",
                getCalendarFromDate(2021,11,11,9,0),
                getCalendarFromTime(1,0),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                rooms.get(9)));

        return DUMMY_MEETING_LIST;
    }

}