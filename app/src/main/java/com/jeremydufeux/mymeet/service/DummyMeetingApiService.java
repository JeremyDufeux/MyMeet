package com.jeremydufeux.mymeet.service;

import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Room;

import java.util.Date;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService{
    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();
    private List<Room> rooms = DummyRoomGenerator.generateRooms();

    @Override
    public List<Meeting> getMeeting() {
        return meetings;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public Room findRoom(Date date, Date duration) {
        return rooms.get(0);
    }
}