package com.jeremydufeux.mymeet.model;

import android.graphics.Color;

import java.util.Date;
import java.util.Date;
import java.util.List;

public class Meeting {
    private long mId;
    private String mSubject;
    private Date mDate;
    private Date mDuration;
    private List<String> mParticipants;
    private Room mRoom;

    public Meeting(long id, String subject, Date date, Date duration, List<String> participants, Room room) {
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getDuration() {
        return mDuration;
    }

    public void setDuration(Date duration) {
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
