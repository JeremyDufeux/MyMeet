package com.jeremydufeux.mymeet.model;

import android.graphics.Color;

import java.util.Date;
import java.util.List;

public class Meeting {
    private long mId;
    private String mSubject;
    private Date mDate;
    private Date mDuration;
    private List<String> mParticipants;
    private int mRoom;
    private Color mRoomColor;

    public Meeting(long id, String subject, Date date, Date duration, List<String> participants, int room, Color roomColor) {
        mId = id;
        mSubject = subject;
        mDate = date;
        mDuration = duration;
        mParticipants = participants;
        this.mRoom = room;
        mRoomColor = roomColor;
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

    public int getRoom() {
        return mRoom;
    }

    public void setRoom(int room) {
        mRoom = room;
    }

    public Color getRoomColor() {
        return mRoomColor;
    }

    public void setRoomColor(Color roomColor) {
        mRoomColor = roomColor;
    }
}
