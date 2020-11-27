package com.jeremydufeux.mymeet.model;

import java.util.Calendar;
import java.util.List;

public class Meeting {
    private long mId;
    private String mSubject;
    private Calendar mDate;
    private Calendar mDuration;
    private List<String> mParticipants;
    private Room mRoom;

    public Meeting(){

    }

    public Meeting(long id, String subject, Calendar date, Calendar duration, List<String> participants, Room room) {
        mId = id;
        mSubject = subject;
        mDate = date;
        mDuration = duration;
        mParticipants = participants;
        mRoom = room;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public Calendar getDate() {
        return mDate;
    }

    public void setDate(Calendar date) {
        mDate = date;
    }

    public Calendar getDuration() {
        return mDuration;
    }

    public void setDuration(Calendar duration) {
        mDuration = duration;
    }

    public List<String> getParticipants() {
        return mParticipants;
    }

    public void setParticipants(List<String> participants) {
        mParticipants = participants;
    }

    public Room getRoom() {
        return mRoom;
    }

    public void setRoom(Room room) {
        mRoom = room;
    }
}
