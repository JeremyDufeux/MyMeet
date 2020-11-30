package com.jeremydufeux.mymeet.ui.add;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jeremydufeux.mymeet.R;
import com.jeremydufeux.mymeet.databinding.ActivityAddMeetingBinding;
import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.dialog.DurationPickerDialog;
import com.jeremydufeux.mymeet.event.DeleteParticipantEvent;
import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Participant;
import com.jeremydufeux.mymeet.service.MeetingApiService;
import com.jeremydufeux.mymeet.utils.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.jeremydufeux.mymeet.utils.Tools.getCalendarFromTime;
import static com.jeremydufeux.mymeet.utils.Tools.getDateFromCal;
import static com.jeremydufeux.mymeet.utils.Tools.getTimeFromCal;

public class AddMeetingActivity extends AppCompatActivity {
    public static final String BUNDLE_EXTRA_MEETING_ID = "BUNDLE_EXTRA_MEETING_ID";
    private MeetingApiService mService;
    private ActivityAddMeetingBinding mBinding;
    private ListParticipantAdapter mAdapter;

    private boolean mEditMode; // false = Add Mode / true = Edit Mode

    private Meeting mMeeting;
    private Calendar mCalendar;
    private Calendar mDuration;
    private List<Participant> mParticipantList;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private DurationPickerDialog durationPickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mService = DI.getMeetingApiService();

        mCalendar = Calendar.getInstance();
        mDuration = getCalendarFromTime(1,0);
        mParticipantList = new ArrayList<>();

        setupDialogs();
        setupUi();
        setupListeners();
        checkForEditIntent();
        setupRecyclerView();
    }

    private void setupDialogs() {
        datePickerDialog = new DatePickerDialog(AddMeetingActivity.this, (datePicker, year, month, dayOfMonth) -> {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mBinding.addMeetingDateEt.setText(getTimeFromCal(mCalendar));
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog = new TimePickerDialog(AddMeetingActivity.this, (timePicker, hour, minute) -> {
            mCalendar.set(Calendar.HOUR_OF_DAY, hour);
            mCalendar.set(Calendar.MINUTE, minute);
            mBinding.addMeetingTimeEt.setText(getTimeFromCal(mCalendar));
        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);

        durationPickerDialog = new DurationPickerDialog();
        durationPickerDialog.setTitle(getString(R.string.select_meeting_duration));
        durationPickerDialog.setDurationSetListener((hour, minute) -> {
            mDuration.set(Calendar.HOUR_OF_DAY, hour);
            mDuration.set(Calendar.MINUTE, minute);
            mBinding.addMeetingDurationEt.setText(getTimeFromCal(mDuration));
        });
    }

    private void setupUi() {
        mBinding.addMeetingDateEt.setHint(getDateFromCal(mCalendar));
        mBinding.addMeetingTimeEt.setHint(getTimeFromCal(mCalendar));
        mBinding.addMeetingDurationEt.setHint(getTimeFromCal(mDuration));

        mBinding.addMeetingRoomNumberTv.setVisibility(View.INVISIBLE);
        mBinding.addMeetingRoomTv.setVisibility(View.INVISIBLE);
    }

    private void setupListeners(){
        mBinding.addMeetingDateEt.setOnClickListener(view -> {
            datePickerDialog.updateDate(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        mBinding.addMeetingTimeEt.setOnClickListener(view -> {
            timePickerDialog.updateTime(mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE));
            timePickerDialog.show();
        });

        mBinding.addMeetingDurationEt.setOnClickListener(view -> {
            durationPickerDialog.setHour(mDuration.get(Calendar.HOUR_OF_DAY));
            durationPickerDialog.setMinute(mDuration.get(Calendar.MINUTE));
            durationPickerDialog.show(getSupportFragmentManager(), null);
        });

        mBinding.addMeetingAddParticipantBtn.setOnClickListener(v -> {
            EditText participantEt = mBinding.addMeetingParticipantEt;
            String email = participantEt.getText().toString();
            if(!email.equals("")){
                Participant participant = new Participant(email);
                mParticipantList.add(participant);
                mAdapter.notifyItemInserted(mParticipantList.indexOf(participant));
            }
        });
    }

    private void checkForEditIntent() {
        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(BUNDLE_EXTRA_MEETING_ID)) {
            String id = getIntent().getExtras().getString(BUNDLE_EXTRA_MEETING_ID);

            for (Meeting meeting : mService.getMeetings()) {
                if (meeting.getId().equals(id)) {
                    mEditMode = true;
                    mMeeting = meeting;
                    break;
                }
            }
            loadData();
        }
    }

    private void loadData(){
        mCalendar = mMeeting.getDate();
        mDuration = mMeeting.getDuration();
        mParticipantList = mMeeting.getParticipants();

        mBinding.addMeetingSubjectTv.setText(mMeeting.getSubject());
        mBinding.addMeetingDateEt.setText(getDateFromCal(mMeeting.getDate()));
        mBinding.addMeetingTimeEt.setText(Tools.getTimeFromCal(mMeeting.getDate()));
        mBinding.addMeetingDurationEt.setText(Tools.getTimeFromCal(mMeeting.getDuration()));

        String roomString = String.format(Locale.getDefault(),"Room %d", mMeeting.getRoom().getNumber());
        mBinding.addMeetingRoomNumberTv.setText(roomString);
        mBinding.addMeetingRoomNumberTv.setVisibility(View.VISIBLE);
        mBinding.addMeetingRoomTv.setVisibility(View.VISIBLE);
    }

    private void setupRecyclerView() {
        mAdapter = new ListParticipantAdapter(mParticipantList);
        mBinding.addMeetingListParticipantsRv.setLayoutManager(new LinearLayoutManager(this));
        mBinding.addMeetingListParticipantsRv.setAdapter(mAdapter);
    }

    @Subscribe
    public void onDeleteParticipantEvent(DeleteParticipantEvent event){
        int index = mParticipantList.indexOf(event.participant);
        mParticipantList.remove(event.participant);
        mAdapter.notifyItemRemoved(index);
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