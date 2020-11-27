package com.jeremydufeux.mymeet.di;

import com.jeremydufeux.mymeet.service.DummyMeetingApiService;
import com.jeremydufeux.mymeet.service.MeetingApiService;

public class DI {
    private static MeetingApiService service = new DummyMeetingApiService();

    public static MeetingApiService getMeetingApiService(){
        return service;
    }
}
