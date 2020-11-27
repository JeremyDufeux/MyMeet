package com.jeremydufeux.mymeet.ui.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.jeremydufeux.mymeet.R;
import com.jeremydufeux.mymeet.databinding.ActivityListMeetingBinding;
import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.service.MeetingApiService;

import java.util.List;

public class ListMeetingActivity extends AppCompatActivity {
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

}