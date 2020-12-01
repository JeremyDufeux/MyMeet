package com.jeremydufeux.mymeet.service;

import android.util.Log;

import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Room;

import java.util.Calendar;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService{
    private List<Room> rooms = DummyRoomGenerator.generateRooms();
    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings(rooms);

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public List<Room> getRooms() {
        return rooms;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void updateMeeting(Meeting meeting) {
        meetings.set(meetings.indexOf(meeting), meeting);
    }

    @Override
    public boolean checkRoomAvailability(Room room, Calendar start, Calendar duration) {
        boolean roomAvailable = true;

        Calendar end = (Calendar) start.clone();
        end.add(Calendar.HOUR_OF_DAY, duration.get(Calendar.HOUR_OF_DAY));
        end.add(Calendar.MINUTE, duration.get(Calendar.MINUTE));

        for(Meeting meeting : meetings){
            if(meeting.getRoom().getNumber() == room.getNumber()){

                Log.d("Debug", "checkRoomAvailability: room     " + room.getNumber());
                Log.d("Debug", "checkRoomAvailability: A start    " + start.getTime());
                Log.d("Debug", "checkRoomAvailability: B start    " + meeting.getStartDate().getTime());

                Log.d("Debug", "checkRoomAvailability: A duration " + duration.getTime());
                Log.d("Debug", "checkRoomAvailability: B duration " + meeting.getDuration().getTime());

                Log.d("Debug", "checkRoomAvailability: A end      " + end.getTime());
                Log.d("Debug", "checkRoomAvailability: B end      " + meeting.getEndDate().getTime());

                // si ( b.start est après a.start et avant a.end ) ou si (b.end est après a.start et avant a.end)  => false
                if((meeting.getStartDate().after(start) && meeting.getStartDate().before(end)) || (meeting.getEndDate().after(start) && meeting.getEndDate().before(end))){
                    roomAvailable = false;
                }
            }
        }

        return roomAvailable;
    }
}
