package com.jeremydufeux.mymeet.service;

import com.jeremydufeux.mymeet.model.Meeting;

import java.util.Date;
import java.util.List;

public interface MeetingApiService {

    /**
     * Get all meeting
     * @return list of meetings
     */
    List<Meeting> getMeeting();

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
     * Find a free room depending on the date and time
     * @param date of meeting
     * @param duration of meeting
     * @return room id
     */
    int findRoom(Date date, Date duration);

}
