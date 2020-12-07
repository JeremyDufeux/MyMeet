package com.jeremydufeux.mymeet.model;

import java.util.UUID;

public class Room {
    private final String mId;
    private int mNumber;
    private int mColor;

    public Room(int number, int color) {
        mId = UUID.randomUUID().toString();
        mNumber = number;
        mColor = color;
    }

    public String getId() {
        return mId;
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
