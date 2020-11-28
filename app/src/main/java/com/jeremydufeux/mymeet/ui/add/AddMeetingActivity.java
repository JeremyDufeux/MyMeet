package com.jeremydufeux.mymeet.ui.add;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.jeremydufeux.mymeet.databinding.ActivityAddMeetingBinding;
import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.dialog.DatePicker;
import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.service.MeetingApiService;
import com.jeremydufeux.mymeet.utils.Tools;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMeetingActivity extends AppCompatActivity implements android.app.DatePickerDialog.OnDateSetListener{
    public static final String BUNDLE_EXTRA_MEETING_ID = "BUNDLE_EXTRA_MEETING_ID";
    private MeetingApiService mService;
    private ActivityAddMeetingBinding mBinding;
    private Meeting mMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mService = DI.getMeetingApiService();

        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(BUNDLE_EXTRA_MEETING_ID)) {
            String id = getIntent().getExtras().getString(BUNDLE_EXTRA_MEETING_ID);

            for (Meeting meeting : mService.getMeetings()) {
                if (meeting.getId().equals(id)) {
                    mMeeting = meeting;
                    break;
                }
            }
            loadData();
        }else{
            loadHint();
        }
    }

    private void loadData(){
        mBinding.addMeetingSubjectTv.setText(mMeeting.getSubject());
        mBinding.addMeetingDateEt.setText(Tools.getDateFromCal(mMeeting.getDate()));
        mBinding.addMeetingDateEt.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePicker(mMeeting.getDate());
            datePicker.show(getSupportFragmentManager(), "date picker");
        });



    }
    private void loadHint() {
        mBinding.addMeetingDateEt.setHint(getDate());
    }



    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        String date = DateFormat.getDateInstance().format(cal.getTime());
        mBinding.addMeetingDateEt.setText(date);
    }

    private String getDate(){
        Calendar cal = Calendar.getInstance();
        return String.format(Locale.FRANCE, "%d/%d/%d", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
    }

}