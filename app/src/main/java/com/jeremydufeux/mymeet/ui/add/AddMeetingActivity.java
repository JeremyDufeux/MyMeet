package com.jeremydufeux.mymeet.ui.add;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jeremydufeux.mymeet.databinding.ActivityAddMeetingBinding;
import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.dialog.DurationPickerDialog;
import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.service.MeetingApiService;
import com.jeremydufeux.mymeet.utils.Tools;

import java.util.Calendar;

import static com.jeremydufeux.mymeet.utils.Tools.getCalendarFromTime;
import static com.jeremydufeux.mymeet.utils.Tools.getDateFromCal;
import static com.jeremydufeux.mymeet.utils.Tools.getTimeFromCal;

public class AddMeetingActivity extends AppCompatActivity {
    public static final String BUNDLE_EXTRA_MEETING_ID = "BUNDLE_EXTRA_MEETING_ID";
    private MeetingApiService mService;
    private ActivityAddMeetingBinding mBinding;
    private Meeting mMeeting;
    private boolean mEditMode;
    private Calendar mCalendar;
    private Calendar mDuration;

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

        setupDialogs();
        setupUi();

        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(BUNDLE_EXTRA_MEETING_ID)) {
            String id = getIntent().getExtras().getString(BUNDLE_EXTRA_MEETING_ID);

            for (Meeting meeting : mService.getMeetings()) {
                if (meeting.getId().equals(id)) {
                    mEditMode = true;
                    mMeeting = meeting;
                    mCalendar = mMeeting.getDate();
                    mDuration = mMeeting.getDuration();
                    break;
                }
            }
            loadData();
        }
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
    }

    private void loadData(){
        mBinding.addMeetingSubjectTv.setText(mMeeting.getSubject());
        mBinding.addMeetingDateEt.setText(getDateFromCal(mMeeting.getDate()));
        mBinding.addMeetingTimeEt.setText(Tools.getTimeFromCal(mMeeting.getDate()));
        mBinding.addMeetingDurationEt.setText(Tools.getTimeFromCal(mMeeting.getDuration()));
    }

}