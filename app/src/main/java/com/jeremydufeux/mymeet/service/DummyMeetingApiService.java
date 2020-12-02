package com.jeremydufeux.mymeet.service;

import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Room;

import java.util.Calendar;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService{
    public static final int ROOM_AMOUNT = 10;
    private List<Room> rooms = DummyRoomGenerator.generateRooms(ROOM_AMOUNT);
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

    public boolean checkRoomAvailability(Room room, Calendar A_Start, Calendar duration) {
        boolean roomAvailable = true;

        Calendar A_End = (Calendar) A_Start.clone();
        A_End.add(Calendar.HOUR_OF_DAY, duration.get(Calendar.HOUR_OF_DAY));
        A_End.add(Calendar.MINUTE, duration.get(Calendar.MINUTE));

        for(Meeting meeting : meetings){
            if(meeting.getRoom().getNumber() == room.getNumber()){
                Calendar B_Start = meeting.getStartDate();
                Calendar B_End = meeting.getEndDate();

                if(A_Start.compareTo(B_Start) == 0){
                    // The meeting have the same start time than another
                    roomAvailable = false;
                }
                else if (A_End.compareTo(B_End) == 0) {
                    // The meeting have the same end time than another
                    roomAvailable = false;
                }
                else if (B_Start.after(A_Start)) {
                    if (B_Start.before(A_End) && !(B_Start.compareTo(A_End) == 0)) {
                        // The meeting end during another / But can end at the beginning of another one
                        roomAvailable = false;
                    }
                }
                else if (B_End.after(A_Start)) {
                    if (B_End.before(A_End)) {
                        // The meeting start during another
                        roomAvailable = false;
                    }
                }
            }
        }

        return roomAvailable;
    }
}
