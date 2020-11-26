package com.jeremydufeux.mymeet.service;

import com.jeremydufeux.mymeet.model.Meeting;

import java.util.Date;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService{
    @Override
    public List<Meeting> getMeeting() {
        return null;
    }

    @Override
    public void addMeeting(Meeting meeting) {

    }

    @Override
    public void deleteMeeting(Meeting meeting) {

    }

    @Override
    public int findRoom(Date date, Date duration) {
        return 0;
    }
}
