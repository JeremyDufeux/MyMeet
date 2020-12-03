package com.jeremydufeux.mymeet.ui.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jeremydufeux.mymeet.R;
import com.jeremydufeux.mymeet.databinding.ActivityListMeetingBinding;
import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.dialog.FilterDialog;
import com.jeremydufeux.mymeet.event.DeleteMeetingEvent;
import com.jeremydufeux.mymeet.event.OpenMeetingEvent;
import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Room;
import com.jeremydufeux.mymeet.service.MeetingApiService;
import com.jeremydufeux.mymeet.ui.add.AddMeetingActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.jeremydufeux.mymeet.ui.add.AddMeetingActivity.BUNDLE_EXTRA_MEETING_ADDED_AT;
import static com.jeremydufeux.mymeet.ui.add.AddMeetingActivity.BUNDLE_EXTRA_MEETING_EDITED_AT;
import static com.jeremydufeux.mymeet.ui.add.AddMeetingActivity.BUNDLE_EXTRA_MEETING_ID;
import static com.jeremydufeux.mymeet.utils.Tools.isSameDay;

public class ListMeetingActivity extends AppCompatActivity {
    public static final int ADD_MEETING_ADD_REQUEST_CODE = 1;
    public static final int ADD_MEETING_EDIT_REQUEST_CODE = 2;
    private MeetingApiService mApiService;
    private ActivityListMeetingBinding mBinding;
    private ListMeetingAdapter mAdapter;
    private List<Room> mRoomList;
    private List<Meeting> mMeetingList;
    private List<Meeting> mFilteredMeetingList;

    private HashMap<String, Boolean> roomFilter;
    private Calendar dateFilter;

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
        mMeetingList = mApiService.getMeetingList();
        mFilteredMeetingList = new ArrayList<>();
        mRoomList = mApiService.getRoomList();
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

    private void openFilterDialog(){
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.setDateSelection(dateFilter);
        filterDialog.setRoomSelection(roomFilter);
        filterDialog.setFilterListener(new FilterDialog.FilterDialogListener() {
            @Override
            public void onFilterSet(Calendar dateSelection, HashMap<String, Boolean> roomSelection) {
                roomFilter = roomSelection;
                dateFilter = dateSelection;
                filterMeetings();
            }

            @Override
            public void onClearFilter() {
                removeListFilters();
            }
        });
        filterDialog.show(getSupportFragmentManager(), null);
    }

    private void filterMeetings(){
        mFilteredMeetingList.clear();
        if(dateFilter!=null){
            for(Meeting meeting : mMeetingList){
                if(isSameDay(meeting.getStartDate(), dateFilter)){
                    if(Objects.requireNonNull(roomFilter.get("all")) || Objects.requireNonNull(roomFilter.get(Integer.toString(meeting.getRoom().getNumber())))) {
                        mFilteredMeetingList.add(meeting);
                    }
                }
            }
        } else if(!Objects.requireNonNull(roomFilter.get("all"))){
            for(Meeting meeting : mMeetingList) {
                if(Objects.requireNonNull(roomFilter.get(Integer.toString(meeting.getRoom().getNumber())))) {
                    mFilteredMeetingList.add(meeting);
                }
            }
        }

        mAdapter = new ListMeetingAdapter(mFilteredMeetingList);
        mBinding.listMeetingsRv.setAdapter(mAdapter);
    }

    private void removeListFilters(){
        roomFilter = null;
        dateFilter = null;
        mAdapter = new ListMeetingAdapter(mMeetingList);
        mBinding.listMeetingsRv.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_meetings_action_bar_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.list_meetings_filter) {
            openFilterDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
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