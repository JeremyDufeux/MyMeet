package com.jeremydufeux.mymeet.service;

import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.model.Meeting;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DummyMeetingApiServiceTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getMeetingApiService();
    }

    @Test
    public void getMeetings() {
        assertEquals(5, service.getMeetings().size());
    }

    @Test
    public void getRooms() {
        assertEquals(10, service.getRooms().size());
    }

    @Test
    public void addMeeting() {
        List<Meeting> meetingList = service.getMeetings();

    }

    @Test
    public void deleteMeeting() {
    }

    @Test
    public void updateMeeting() {
    }

    @Test
    public void checkRoomAvailability() {
    }
}