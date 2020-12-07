package com.jeremydufeux.mymeet.event;

import com.jeremydufeux.mymeet.model.Meeting;

public class OpenMeetingEvent {

    /**
     * Meeting to delete
     */
    public Meeting meeting;

    /**
     * Constructor.
     * @param meeting the meeting to open
     */
    public OpenMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
