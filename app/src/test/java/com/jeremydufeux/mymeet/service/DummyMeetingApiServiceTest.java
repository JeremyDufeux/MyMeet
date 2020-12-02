package com.jeremydufeux.mymeet.service;

import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Participant;
import com.jeremydufeux.mymeet.model.Room;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.jeremydufeux.mymeet.utils.Tools.getCalendarFromDate;
import static com.jeremydufeux.mymeet.utils.Tools.getCalendarFromTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class DummyMeetingApiServiceTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetings() {
        List<Meeting> meetingList = service.getMeetingList();
        List<Meeting> expectedMeetingList = DummyMeetingGenerator.DUMMY_MEETING_LIST;

        assertEquals(meetingList, expectedMeetingList);
    }

    @Test
    public void getRooms() {
        List<Room> roomList = service.getRoomList();
        List<Room> expectedRoomList = DummyRoomGenerator.DUMMY_ROOM_LIST;

        assertEquals(roomList, expectedRoomList);
    }

    @Test
    public void addMeeting() {
        List<Meeting> meetingList = service.getMeetingList();
        List<Room> roomList = service.getRoomList();

        int meetingSize = meetingList.size();

        Meeting meeting = new Meeting(
                "Subject",
                Calendar.getInstance(),
                Calendar.getInstance(),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                roomList.get(0));

        service.addMeeting(meeting);

        assertEquals(meetingSize + 1, service.getMeetingList().size());
    }

    @Test
    public void deleteMeeting() {
        List<Meeting> meetingList = service.getMeetingList();
        Meeting meeting = meetingList.get(0);
        int meetingSize = meetingList.size();

        service.deleteMeeting(meeting);

        assertEquals(meetingSize - 1, service.getMeetingList().size());
        assertEquals(-1, service.getMeetingList().indexOf(meeting));
    }

    @Test
    public void updateMeeting() {
        List<Meeting> meetingList = service.getMeetingList();
        Meeting meeting = (Meeting) meetingList.get(0).clone();

        meeting.setSubject("Hello");
        assertNotEquals(meeting, meetingList.get(0));

        service.updateMeeting(meeting);

        assertEquals("Hello", service.getMeetingList().get(0).getSubject());
    }

    // All availability check is compared with the first of the list
    // Start on 1/12/20 at 12h00 with a duration of 1h10 in room 0

    @Test
    public void checkRoomAvailabilityBeforeMeeting() {
        Room room = service.getRoomList().get(0);
        Calendar start = getCalendarFromDate(2020,12,1,11,0);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityAfterMeeting() {
        Room room = service.getRoomList().get(0);
        Calendar start = getCalendarFromDate(2020,12,1,18,0);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityStartAtTheEndOfMeeting() {
        Room room = service.getRoomList().get(0);
        Calendar start = getCalendarFromDate(2020,12,1,13,0);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityEndOnMeetingStart() {
        Room room = service.getRoomList().get(0);
        Calendar start = getCalendarFromDate(2020,12,1,11,30);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityStartOnMeetingStart() {
        Room room = service.getRoomList().get(0);
        Calendar start = getCalendarFromDate(2020,12,1,12,0);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertFalse(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityStartOnMeetingStartInRoom2() {
        Room room = service.getRoomList().get(1);
        Calendar start = getCalendarFromDate(2020,12,1,12,0);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityEndOnMeetingEnd() {
        Room room = service.getRoomList().get(0);
        Calendar start = getCalendarFromDate(2020,12,1,12,30);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertFalse(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityEndOnMeetingEndInRoom2() {
        Room room = service.getRoomList().get(1);
        Calendar start = getCalendarFromDate(2020,12,1,12,30);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityEndDuringMeeting() {
        Room room = service.getRoomList().get(0);
        Calendar start = getCalendarFromDate(2020,12,1,11,30);
        Calendar duration = getCalendarFromTime(1,0);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertFalse(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityEndDuringMeetingInRoom2() {
        Room room = service.getRoomList().get(1);
        Calendar start = getCalendarFromDate(2020,12,1,11,30);
        Calendar duration = getCalendarFromTime(1,0);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityStartDuringMeeting() {
        Room room = service.getRoomList().get(0);
        Calendar start = getCalendarFromDate(2020,12,1,12,30);
        Calendar duration = getCalendarFromTime(1,0);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertFalse(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityStartDuringMeetingInRoom2() {
        Room room = service.getRoomList().get(1);
        Calendar start = getCalendarFromDate(2020,12,1,12,30);
        Calendar duration = getCalendarFromTime(1,0);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityIsDuringMeeting() {
        Room room = service.getRoomList().get(0);
        Calendar start = getCalendarFromDate(2020,12,1,12,10);
        Calendar duration = getCalendarFromTime(0,50);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertFalse(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityIsDuringMeetingInRoom2() {
        Room room = service.getRoomList().get(1);
        Calendar start = getCalendarFromDate(2020,12,1,12,10);
        Calendar duration = getCalendarFromTime(0,50);

        boolean isRoomAvailable = service.checkRoomAvailability("", room,start, duration);
        assertTrue(isRoomAvailable);
    }
}