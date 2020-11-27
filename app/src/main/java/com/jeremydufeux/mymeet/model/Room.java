package com.jeremydufeux.mymeet.model;

public class Room {
    private long mId;
    private int mNumber;
    private String mImageUrl;

    public Room(long id, int number, String imageUrl) {
        mId = id;
        mNumber = number;
        mImageUrl = imageUrl;
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

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
