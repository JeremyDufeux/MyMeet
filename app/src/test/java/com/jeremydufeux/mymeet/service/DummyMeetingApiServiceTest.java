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

    // All availability check is compared with an event added just before
    // Start on 20/01/1990 at 10h00 with a duration of 1h30 in room 0
    @Test
    public void checkRoomAvailabilityBeforeMeeting() {
        Room room = service.getRoomList().get(0);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,9,0);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room, start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityAfterMeeting() {
        Room room = service.getRoomList().get(0);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,12,0);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room, start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityStartAtTheEndOfMeeting() {
        Room room = service.getRoomList().get(0);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,11,30);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room, start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityEndOnMeetingStart() {
        Room room = service.getRoomList().get(0);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,9,30);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room, start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityStartOnMeetingStart() {
        Room room = service.getRoomList().get(0);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,10,0);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room, start, duration);
        assertFalse(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityStartOnMeetingStartInRoom2() {
        Room room1 = service.getRoomList().get(0);
        Room room2 = service.getRoomList().get(1);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room1);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,10,0);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room2, start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityEndOnMeetingEnd() {
        Room room = service.getRoomList().get(0);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,11,0);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room, start, duration);
        assertFalse(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityEndOnMeetingEndInRoom2() {
        Room room1 = service.getRoomList().get(0);
        Room room2 = service.getRoomList().get(1);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room1);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,11,0);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room2, start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityEndDuringMeeting() {
        Room room = service.getRoomList().get(0);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,9,40);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room, start, duration);
        assertFalse(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityEndDuringMeetingInRoom2() {
        Room room1 = service.getRoomList().get(0);
        Room room2 = service.getRoomList().get(1);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room1);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,9,40);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room2, start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityStartDuringMeeting() {
        Room room = service.getRoomList().get(0);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,11,20);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room, start, duration);
        assertFalse(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityStartDuringMeetingInRoom2() {
        Room room1 = service.getRoomList().get(0);
        Room room2 = service.getRoomList().get(1);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room1);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,11,20);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room2, start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityIsDuringMeeting() {
        Room room = service.getRoomList().get(0);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,10,30);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room, start, duration);
        System.out.println(isRoomAvailable);
        assertFalse(isRoomAvailable);
    }

    @Test
    public void checkRoomAvailabilityIsDuringMeetingInRoom2() {
        Room room1 = service.getRoomList().get(0);
        Room room2 = service.getRoomList().get(1);

        Meeting meeting = new Meeting(
                "Subject",
                getCalendarFromDate(1990,1,20,10,0),
                getCalendarFromTime(1,30),
                new ArrayList<>(Arrays.asList(new Participant("jeremy.dufeux@gmail.com"), new Participant("jeremy.dufeux@gmail.com"))),
                room1);
        service.addMeeting(meeting);

        Calendar start = getCalendarFromDate(1990,1,20,10,30);
        Calendar duration = getCalendarFromTime(0,30);

        boolean isRoomAvailable = service.checkRoomAvailability("", room2, start, duration);
        assertTrue(isRoomAvailable);
    }

    @Test
    public void before() {

        Calendar A = getCalendarFromDate(1990,1,20,10,30);
        Calendar B = getCalendarFromDate(1990,1,20,18,30);

        assertTrue(A.before(B));
    }
}