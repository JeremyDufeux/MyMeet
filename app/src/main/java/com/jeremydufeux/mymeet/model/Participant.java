package com.jeremydufeux.mymeet.model;

import java.util.UUID;

public class Participant {
    private final String mId;
    private String mEmail;

    public Participant(String email) {
        mId = UUID.randomUUID().toString();
        mEmail = email;
    }

    public String getId() {
        return mId;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }
}
