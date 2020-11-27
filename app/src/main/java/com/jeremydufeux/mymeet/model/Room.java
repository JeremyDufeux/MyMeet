package com.jeremydufeux.mymeet.model;

public class Room {
    private long mId;
    private int mNumber;
    private int mColor;

    public Room(long id, int number, int color) {
        mId = id;
        mNumber = number;
        mColor = color;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }
}
