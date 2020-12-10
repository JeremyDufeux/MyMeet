package com.jeremydufeux.mymeet.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class Meeting implements Cloneable, Comparable<Meeting>{
    private String mId;
    private String mSubject;
    private Calendar mStartDate;
    private Calendar mEndDate;
    private Calendar mDuration;
    private List<Participant> mParticipants;
    private Room mRoom;


    public Meeting(String subject, Calendar startDate, Calendar duration, List<Participant> participants, Room room) {
        mId = UUID.randomUUID().toString(); // Use UUID to fin a unique object id
        mSubject = subject;
        mStartDate = startDate;
        mDuration = duration;
        mParticipants = participants;
        mRoom = room;
        addDurationToEndDate(duration);
    }

    public void addDurationToEndDate(Calendar duration) {  // set EndDate with mDuration values
        mEndDate = (Calendar) mStartDate.clone();
        mEndDate.add(Calendar.HOUR_OF_DAY, duration.get(Calendar.HOUR_OF_DAY));
        mEndDate.add(Calendar.MINUTE, duration.get(Calendar.MINUTE));
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
        mDuration = duration;
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

    @Override
    public int compareTo(Meeting o) { // Implement Comparable to sort lists of meetings by date
        return this.getStartDate().compareTo(o.getStartDate());
    }

    @NonNull
    @Override
    public Object clone() {     // Implement Cloneable to have the possibility to edit it easily
        try {
            Meeting clone = (Meeting) super.clone();
            clone.mId = this.mId;
            clone.mSubject = this.mSubject;
            clone.mStartDate = (Calendar) this.mStartDate.clone();
            clone.mDuration = (Calendar) this.mDuration.clone();
            clone.mEndDate = (Calendar) this.mEndDate.clone();
            clone.mParticipants = new ArrayList<>(this.mParticipants);
            clone.mRoom = this.mRoom;
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return new InternalError("Clone Error");
        }
    }
}