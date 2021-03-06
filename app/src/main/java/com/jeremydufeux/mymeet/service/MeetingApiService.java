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
    List<Meeting> getMeetingList();

    /**
     * Get all room
     * @return list of rooms
     */
    List<Room> getRoomList();

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
     * @param meetingId the id of the meeting edited or created
     * @param room the selected room
     * @param startDate of meeting
     * @param duration of meeting
     * @return the availability
     */
    boolean checkRoomAvailability(String meetingId, Room room, Calendar startDate, Calendar duration);

}
