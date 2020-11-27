package com.jeremydufeux.mymeet.ui.add;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.jeremydufeux.mymeet.R;
import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.model.Meeting;

import java.util.List;

public class AddMeetingActivity extends AppCompatActivity {

    public static final String BUNDLE_EXTRA_MEETING_ID = "BUNDLE_EXTRA_MEETING_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(BUNDLE_EXTRA_MEETING_ID)) {
            Log.d("ContentValues", "Edit mode");
            long id = getIntent().getExtras().getLong(BUNDLE_EXTRA_MEETING_ID);
            List<Meeting> meetings = DI.getMeetingApiService().getMeetings();
            for (Meeting meeting : meetings) {
                if (meeting.getId() == id) {
                    Log.d("ContentValues", "Edit meeting : " + meeting.getSubject());
                }
            }
        }else{
            Log.d("ContentValues", "Add mode");
        }
    }

}