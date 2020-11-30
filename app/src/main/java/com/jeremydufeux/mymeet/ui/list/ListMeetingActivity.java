package com.jeremydufeux.mymeet.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jeremydufeux.mymeet.databinding.ActivityListMeetingBinding;
import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.event.DeleteMeetingEvent;
import com.jeremydufeux.mymeet.event.OpenMeetingEvent;
import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.service.MeetingApiService;
import com.jeremydufeux.mymeet.ui.add.AddMeetingActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static com.jeremydufeux.mymeet.ui.add.AddMeetingActivity.BUNDLE_EXTRA_MEETING_ADDED_AT;
import static com.jeremydufeux.mymeet.ui.add.AddMeetingActivity.BUNDLE_EXTRA_MEETING_EDITED_AT;
import static com.jeremydufeux.mymeet.ui.add.AddMeetingActivity.BUNDLE_EXTRA_MEETING_ID;

public class ListMeetingActivity extends AppCompatActivity {
    public static final int ADD_MEETING_ADD_REQUEST_CODE = 1;
    public static final int ADD_MEETING_EDIT_REQUEST_CODE = 2;
    private MeetingApiService mApiService;
    private ActivityListMeetingBinding mBinding;
    private ListMeetingAdapter mAdapter;
    private List<Meeting> mMeetingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityListMeetingBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        setupApiService();
        setupRecyclerView();
        setupFab();
    }

    private void setupApiService() {
        mApiService = DI.getMeetingApiService();
        mMeetingList = mApiService.getMeetings();
    }

    private void setupRecyclerView() {
        mAdapter = new ListMeetingAdapter(mMeetingList);
        mBinding.listMeetingsRv.setLayoutManager(new LinearLayoutManager(this));
        mBinding.listMeetingsRv.setAdapter(mAdapter);
    }

    private void setupFab() {
        mBinding.listMeetingsFab.setOnClickListener(view -> {
            Intent addMeetingActivityIntent = new Intent(ListMeetingActivity.this, AddMeetingActivity.class);
            startActivityForResult(addMeetingActivityIntent, ADD_MEETING_ADD_REQUEST_CODE);
        });
    }

    @Subscribe
    public void onDeleteMeetingEvent(DeleteMeetingEvent event){
        int index = mMeetingList.indexOf(event.meeting);
        DI.getMeetingApiService().deleteMeeting(event.meeting);
        mAdapter.notifyItemRemoved(index);
    }

    @Subscribe
    public void onOpenMeetingEvent(OpenMeetingEvent event){
        Intent editMeetingActivityIntent = new Intent(ListMeetingActivity.this, AddMeetingActivity.class);
        editMeetingActivityIntent.putExtra(BUNDLE_EXTRA_MEETING_ID, event.meeting.getId());
        startActivityForResult(editMeetingActivityIntent, ADD_MEETING_EDIT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int position;
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case ADD_MEETING_ADD_REQUEST_CODE:
                    assert data != null;
                    position = data.getIntExtra(BUNDLE_EXTRA_MEETING_ADDED_AT, 0);
                    mAdapter.notifyItemInserted(position);
                    mBinding.listMeetingsRv.smoothScrollToPosition(position);
                    break;
                case ADD_MEETING_EDIT_REQUEST_CODE:
                    assert data != null;
                    position = data.getIntExtra(BUNDLE_EXTRA_MEETING_EDITED_AT, 0);
                    mAdapter.notifyItemChanged(position);
                    mBinding.listMeetingsRv.smoothScrollToPosition(position);
                    break;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}