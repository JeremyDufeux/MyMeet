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
     * Find a free room depending on the date and time
     * @param date of meeting
     * @param duration of meeting
     * @return room id
     */
    Room findRoom(Calendar date, Calendar duration);

}
