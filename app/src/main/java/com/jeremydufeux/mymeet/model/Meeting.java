package com.jeremydufeux.mymeet.model;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class Meeting {
    private final String mId;
    private String mSubject;
    private Calendar mStartDate;
    private Calendar mEndDate;
    private Calendar mDuration;
    private List<Participant> mParticipants;
    private Room mRoom;


    public Meeting(String subject, Calendar startDate, Calendar duration, List<Participant> participants, Room room) {
        mId = UUID.randomUUID().toString();
        mSubject = subject;
        mStartDate = startDate;
        mDuration = duration;
        mParticipants = participants;
        mRoom = room;
        setDuration(duration);
    }

    public String getId() {
        return mId;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public Calendar getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Calendar startDate) {
        mStartDate = startDate;
    }

    public Calendar getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Calendar endDate) {
        mEndDate = endDate;
    }

    public Calendar getDuration() {
        return mDuration;
    }

    public void setDuration(Calendar duration) {
        mEndDate = (Calendar) mStartDate.clone();
        mEndDate.add(Calendar.HOUR_OF_DAY, duration.get(Calendar.HOUR_OF_DAY));
        mEndDate.add(Calendar.MINUTE, duration.get(Calendar.MINUTE));
    }

    public List<Participant> getParticipants() {
        return mParticipants;
    }

    public void setParticipants(List<Participant> participants) {
        mParticipants = participants;
    }

    public Room getRoom() {
        return mRoom;
    }

    public void setRoom(Room room) {
        mRoom = room;
    }

}