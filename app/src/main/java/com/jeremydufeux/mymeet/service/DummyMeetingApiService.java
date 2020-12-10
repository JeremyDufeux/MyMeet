package com.jeremydufeux.mymeet.service;

import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Room;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService{
    public static final int ROOM_AMOUNT = 10;
    private final List<Room> mRoomList = DummyRoomGenerator.generateRooms(ROOM_AMOUNT);
    private final List<Meeting> mMeetingList = DummyMeetingGenerator.generateMeetings(mRoomList);

    @Override
    public List<Meeting> getMeetingList() {
        return mMeetingList;
    }

    @Override
    public List<Room> getRoomList() {
        return mRoomList;
    }

    @Override
    public void addMeeting(Meeting meeting) {   // Add meeting to list and sort
        mMeetingList.add(meeting);
        Collections.sort(mMeetingList);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetingList.remove(meeting);
    }

    @Override
    public void updateMeeting(Meeting meetingToUpdate) {    // the meeting should be a clone of the original with the same id
        for (Meeting meeting : mMeetingList) {
            if (meeting.getId().equals(meetingToUpdate.getId())) {
                mMeetingList.set(mMeetingList.indexOf(meeting), meetingToUpdate);
                break;
            }
        }
        Collections.sort(mMeetingList);                     // Sort list by date
    }

    public boolean checkRoomAvailability(String meetingId, Room room, Calendar A_Start, Calendar duration) {
        boolean roomAvailable = true;

        Calendar A_End = (Calendar) A_Start.clone();
        A_End.add(Calendar.HOUR_OF_DAY, duration.get(Calendar.HOUR_OF_DAY));
        A_End.add(Calendar.MINUTE, duration.get(Calendar.MINUTE));

        for(Meeting meeting : mMeetingList){
            if(!meetingId.equals(meeting.getId())) {                               // Avoid to check availability with the edited Meeting
                if (meeting.getRoom().getNumber() == room.getNumber()) {    // Check to compare meeting of the selected room

                    Calendar B_Start = meeting.getStartDate();
                    Calendar B_End = meeting.getEndDate();

                    if (A_Start.compareTo(B_Start) == 0) {                   // The meeting have the same start time than another
                        roomAvailable = false;
                    }
                    else if (A_End.compareTo(B_End) == 0) {                  // The meeting have the same end time than another
                        roomAvailable = false;
                    }
                    else if(A_Start.after(B_Start) && A_End.before(B_End)){  // The meeting start after and end before than another
                        roomAvailable = false;
                    }
                    else if (A_Start.before(B_Start)) {
                        if (A_End.after(B_Start)) {                          // The meeting end during another / But can end at the beginning of another one
                            if(!(A_End.compareTo(B_Start) == 0)) {
                                roomAvailable = false;
                            }
                        }
                    }
                    else if (A_Start.before(B_End)) {
                        if (A_End.after(B_End)) {                           // The meeting start during another
                            roomAvailable = false;
                        }
                    }
                }
            }
        }

        return roomAvailable;
    }
}
