package com.jeremydufeux.mymeet.model;

import java.util.UUID;

public class Room {
    private final String mId;
    private int mNumber;
    private String mImageUrl;

    public Room(int number, String imageUrl) {
        mId = UUID.randomUUID().toString();
        mNumber = number;
        mImageUrl = imageUrl;
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

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
