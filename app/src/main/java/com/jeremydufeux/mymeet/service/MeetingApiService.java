package com.jeremydufeux.mymeet.service;

import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Room;

import java.util.Calendar;
import java.util.List;

public interface MeetingApiService {

    /**
     * Get all meeting
     * @return list of meetings
     */
    List<Meeting> getMeetings();

    /**
     * Get all room
     * @return list of rooms
     */
    List<Room> getRooms();

    /**
     * Add meeting
     * @param meeting to add
     */
    void addMeeting(Meeting meeting);

    /**
     * Delete meeting
     * @param meeting to delete
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Update meeting
     * @param meeting to update
     */
    void updateMeeting(Meeting meeting);

    /**
     * Check the room availability depending on the date and time
     * @param room the selected room
     * @param date of meeting
     * @param duration of meeting
     * @return the availability
     */
    boolean checkRoomAvailability(Room room, Calendar date, Calendar duration);

}
