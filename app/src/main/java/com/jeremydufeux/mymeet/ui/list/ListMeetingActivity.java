package com.jeremydufeux.mymeet.ui.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jeremydufeux.mymeet.R;
import com.jeremydufeux.mymeet.databinding.ActivityListMeetingBinding;
import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.event.DeleteMeetingEvent;
import com.jeremydufeux.mymeet.event.OpenMeetingEvent;
import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class ListMeetingActivity extends AppCompatActivity {
    private static final String TAG = "ContentValues";
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
        //mBinding.listMeetingsRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mBinding.listMeetingsRv.setAdapter(mAdapter);
    }

    private void setupFab() {
    }

    @Subscribe
    public void onDeleteMeetingEvent(DeleteMeetingEvent event){
        int index = mMeetingList.indexOf(event.meeting);
        DI.getMeetingApiService().deleteMeeting(event.meeting);
        mAdapter.notifyItemRemoved(index);
    }

    @Subscribe
    public void onOpenMeetingEvent(OpenMeetingEvent event){
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