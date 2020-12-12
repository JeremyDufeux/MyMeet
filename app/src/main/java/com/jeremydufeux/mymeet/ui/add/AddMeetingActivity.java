package com.jeremydufeux.mymeet.ui.add;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.chip.Chip;
import com.jeremydufeux.mymeet.R;
import com.jeremydufeux.mymeet.databinding.ActivityAddMeetingBinding;
import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.dialog.DiscardDialog;
import com.jeremydufeux.mymeet.dialog.DurationPickerDialog;
import com.jeremydufeux.mymeet.event.DeleteParticipantEvent;
import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Participant;
import com.jeremydufeux.mymeet.model.Room;
import com.jeremydufeux.mymeet.service.MeetingApiService;
import com.jeremydufeux.mymeet.utils.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.view.View.NO_ID;
import static com.jeremydufeux.mymeet.utils.Tools.checkEmail;
import static com.jeremydufeux.mymeet.utils.Tools.createChipStateColors;
import static com.jeremydufeux.mymeet.utils.Tools.getCalendarFromTime;
import static com.jeremydufeux.mymeet.utils.Tools.getDateFromCal;
import static com.jeremydufeux.mymeet.utils.Tools.getTimeFromCal;

public class AddMeetingActivity extends AppCompatActivity {
    public static final String BUNDLE_EXTRA_MEETING_ID = "BUNDLE_EXTRA_MEETING_ID";
    public static final String BUNDLE_EXTRA_MEETING_ADDED_AT = "BUNDLE_EXTRA_MEETING_ADDED_AT";
    public static final String BUNDLE_EXTRA_MEETING_EDITED_AT = "BUNDLE_EXTRA_MEETING_EDITED_AT";

    private MeetingApiService mService;
    private ActivityAddMeetingBinding mBinding;
    private ListParticipantAdapter mAdapter;

    DatePickerDialog mDatePickerDialog;
    TimePickerDialog mTimePickerDialog;
    DurationPickerDialog mDurationPickerDialog;
    DiscardDialog mDiscardDialog;

    private boolean mEditMode; // false = Add Mode / true = Edit Mode

    private boolean[] mCheckFields; // Field needed to add Meeting :
    public static final int FIELD_SUBJECT               = 0;
    public static final int FIELD_DATE                  = 1;
    public static final int FIELD_TIME                  = 2;
    public static final int FIELD_DURATION              = 3;
    public static final int FIELD_ROOM                  = 4;
    public static final int FIELD_ROOM_FOUND            = 5;
    public static final int FIELD_PARTICIPANTS_AMOUNT   = 6;
    private boolean mCheckFieldsEditMode;

    private List<Room> mRoomsList;  // Values needed for Meeting:
    private Meeting mMeeting;
    private Calendar mCalendar;
    private Calendar mDuration;
    private Room mRoom;
    private List<Participant> mParticipantList;

    private final Intent resultIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddMeetingBinding.inflate(getLayoutInflater()); // Inflate activity_add_meeting with ViewBinding
        View view = mBinding.getRoot();
        setContentView(view);

        mService = DI.getMeetingApiService(); // Get service

        mCheckFields = new boolean[FIELD_PARTICIPANTS_AMOUNT +1]; // init array with all needed fields and add it to
        Arrays.fill(mCheckFields, false);

        mRoomsList = mService.getRoomList(); // Get Rooms
        mCalendar = Calendar.getInstance(); // Get date
        mDuration = getCalendarFromTime(1,0); // set default duration to 1h
        mParticipantList = new ArrayList<>(); // init participants list
        mMeeting = new Meeting("", mCalendar, mDuration, mParticipantList, mRoom); // init a meeting

        setupUi();
        setupListeners();
        setupDialogs();
        checkForEditIntent();
        setupActionBar();
        setupRecyclerView();
    }

    private void setupUi() { // set all fields with default data:
        mBinding.addMeetingDateEt.setHint(getDateFromCal(mCalendar));
        mBinding.addMeetingTimeEt.setHint(getTimeFromCal(mCalendar));
        mBinding.addMeetingDurationEt.setHint(getTimeFromCal(mDuration));

        for (Room room : mRoomsList){ // Add Chip to ChipGroup that represent rooms
            Chip chip = new Chip(this);
            String roomTitle = String.format(Locale.getDefault(), "%s %d", getString(R.string.room), room.getNumber());
            chip.setText(roomTitle);
            chip.setId(mRoomsList.indexOf(room));  // set roomList index as id
            chip.setCheckable(true);
            chip.setChipBackgroundColor(createChipStateColors(this)); // set set checked color to match app primaryColor via tools method
            mBinding.addMeetingRoomsCpg.addView(chip);
        }

        hideAvailabilityMessage(); // Hide room availability message
    }

    private void setupListeners(){
        mBinding.addMeetingSubjectTv.setOnEditorActionListener((v, actionId, event) -> { // Listen to Done keyboard action to clear subject field focus
            if(actionId == EditorInfo.IME_ACTION_DONE) mBinding.addMeetingSubjectTv.clearFocus();
            return false;
        });

        mBinding.addMeetingDateEt.setOnClickListener(view -> openCalendarDialog()); // listen date EditText to open calendar dialog
        mBinding.addMeetingTimeEt.setOnClickListener(view -> openTimeDialog()); // listen time EditText to open time dialog
        mBinding.addMeetingDurationEt.setOnClickListener(view -> openDurationDialog()); // listen duration EditText to open duration dialog

        mBinding.addMeetingAddParticipantBtn.setOnClickListener(v -> addParticipant()); // add participant when "+" button clicked
        mBinding.addMeetingParticipantEt.setOnEditorActionListener((v, actionId, event) -> { // Listen to Done keyboard action to add participant
            if(actionId == EditorInfo.IME_ACTION_DONE) addParticipant();
            return false;
        });

        mBinding.addMeetingRoomsCpg.setOnCheckedChangeListener((chipGroup, checkedId) -> { // When a Chip is clicked
            if(checkedId != NO_ID) {
                mCheckFields[FIELD_ROOM] = true;    // set room check field to true
                mRoom = mRoomsList.get(checkedId);  // get selected room via id, who correspond to list index
                checkAvailability();                // check room availability
            } else {                                // if no chip selected
                mCheckFields[FIELD_ROOM] = false;   // set room check field to false
                hideAvailabilityMessage();          // Hide availability message
            }
        });
    }

    private void setupDialogs(){
        mDatePickerDialog = new DatePickerDialog(AddMeetingActivity.this, (datePicker, year, month, dayOfMonth) -> {    // Create calendar picker dialog and create his listener
            mCalendar.set(Calendar.YEAR, year);                                                                                 // set selected year to mCalendar value
            mCalendar.set(Calendar.MONTH, month);                                                                               // set selected year to mCalendar value
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);                                                                   // set selected day to mCalendar value
            mBinding.addMeetingDateEt.setText(getDateFromCal(mCalendar));                                                       // set selected date to EditText via tools method
            mCheckFields[FIELD_DATE] = true;                                                                                    // set corresponding check field to true
            mCheckFieldsEditMode = true;                                                                                        // set mCheckFieldsEditMode to true, used in edit mode
            checkAvailability();                                                                                                // check room availability
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));                  // set default values to calendar picker

        mTimePickerDialog = new TimePickerDialog(AddMeetingActivity.this, (timePicker, hour, minute) -> {               // Create time picker dialog and create his listener
            mCalendar.set(Calendar.HOUR_OF_DAY, hour);                                                                          // set selected hour to mCalendar value
            mCalendar.set(Calendar.MINUTE, minute);                                                                             // set selected minute to mCalendar value
            mCalendar.set(Calendar.SECOND, 0);                                                                                  // set mCalendar second to 0
            mCalendar.set(Calendar.MILLISECOND, 0);                                                                             // set mCalendar millisecond to 0
            mBinding.addMeetingTimeEt.setText(getTimeFromCal(mCalendar));                                                       // set selected time to EditText via tools method
            mCheckFields[FIELD_TIME] = true;                                                                                    // set corresponding check field to true
            mCheckFieldsEditMode = true;                                                                                        // set mCheckFieldsEditMode to true, used in edit mode
            checkAvailability();                                                                                                // check room availability
        }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);                              // set default values to calendar picker

        mDurationPickerDialog = new DurationPickerDialog();                                                                     // Create duration picker dialog
        mDurationPickerDialog.setDurationSetListener((hour, minute) -> {                                                        // set listener
            mDuration.set(Calendar.HOUR_OF_DAY, hour);                                                                          // set selected hour to mDuration value
            mDuration.set(Calendar.MINUTE, minute);                                                                             // set selected minute to mDuration value
            mBinding.addMeetingDurationEt.setText(getTimeFromCal(mDuration));                                                   // set selected time to EditText via tools method
            mCheckFields[FIELD_DURATION] = true;                                                                                // set corresponding check field to true
            mCheckFieldsEditMode = true;                                                                                        // set mCheckFieldsEditMode to true, used in edit mode
            checkAvailability();                                                                                                // check room availability
        });

        mDiscardDialog = new DiscardDialog();
        mDiscardDialog.setDiscardDialogListener(this::finish);
    }

    private void openCalendarDialog(){
        mDatePickerDialog.updateDate(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)); // Update time
        mDatePickerDialog.show();        // Show dialog
    }

    private void openTimeDialog(){
        mTimePickerDialog.updateTime(mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE)); // update dialog values
        mTimePickerDialog.show();        // Show dialog
    }

    private void openDurationDialog(){
        mDurationPickerDialog.setHour(mDuration.get(Calendar.HOUR_OF_DAY));        // update dialog values
        mDurationPickerDialog.setMinute(mDuration.get(Calendar.MINUTE));
        mDurationPickerDialog.show(getSupportFragmentManager(), null);        // Show dialog
    }

    private void openDiscardDialog(){
        int score = checkField();                                               // Check field and get the fill score
        if((mEditMode && mCheckFieldsEditMode) || (!mEditMode && score>0)){     // Open dialog if edit mode and fields was changed or when in add mode if a field was edited
            mDiscardDialog.show(getSupportFragmentManager(), null);
        } else {                                                                // If nothing change finish activity
            finish();
        }
    }

    private void checkAvailability(){
        if(mCheckFields[FIELD_DATE] && mCheckFields[FIELD_TIME] && mCheckFields[FIELD_DURATION] && mCheckFields[FIELD_ROOM]) {  // Check if all needed fields are fill
            if (mService.checkRoomAvailability(mMeeting.getId(), mRoom, mCalendar, mDuration)) {                                // Check room availability via service and change availability message text and image depending of result
                mBinding.addMeetingAddRoomAvailabilityIm.setImageResource(R.drawable.ic_check);
                mBinding.addMeetingAddRoomAvailabilityTv.setText(getString(R.string.available_text));
                mCheckFields[FIELD_ROOM_FOUND] = true;                                                                          // set room found check field to true
            } else {
                mBinding.addMeetingAddRoomAvailabilityIm.setImageResource(R.drawable.ic_close);
                mBinding.addMeetingAddRoomAvailabilityTv.setText(getString(R.string.not_available_text));
                mCheckFields[FIELD_ROOM_FOUND] = false;                                                                         // set room found check field to false
            }
            showAvailabilityMessage();                                                                                          // Display availability message
        }
    }

    private void showAvailabilityMessage() {
        mBinding.addMeetingAddRoomAvailabilityIm.setVisibility(View.VISIBLE);
        mBinding.addMeetingAddRoomAvailabilityTv.setVisibility(View.VISIBLE);
    }

    private void hideAvailabilityMessage() {
        mBinding.addMeetingAddRoomAvailabilityIm.setVisibility(View.INVISIBLE);
        mBinding.addMeetingAddRoomAvailabilityTv.setVisibility(View.INVISIBLE);
    }

    private void addParticipant(){
        EditText participantEt = mBinding.addMeetingParticipantEt;
        String email = participantEt.getText().toString();                             // Get editText value
        if(checkEmail(email)){                                                         // if string match e-mail address pattern, via tool method
            for(Participant participant : mParticipantList){
                if(participant.getEmail().equals(email)){
                    Toast.makeText(this, getString(R.string.toast_email_in_the_list), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            Participant participant = new Participant(email);                          // create a new participant and add it to the list
            mParticipantList.add(participant);
            mAdapter.notifyItemInserted(mParticipantList.indexOf(participant));
            participantEt.setText("");
            closeKeyboard();
            mBinding.addMeetingListParticipantsRv.smoothScrollToPosition(mParticipantList.size());
            mCheckFieldsEditMode = true;
        } else {                                                                       // if string doesn't match e-mail address pattern, show a toast message
            Toast.makeText(this, getString(R.string.toast_email_field), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkForEditIntent() {
        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(BUNDLE_EXTRA_MEETING_ID)) {  // If activity has an intent get the meeting to edit it
            String id = getIntent().getExtras().getString(BUNDLE_EXTRA_MEETING_ID);

            List<Meeting> meetingList = mService.getMeetingList();
            for (Meeting meeting : meetingList) {
                if (meeting.getId().equals(id)) {
                    mEditMode = true;
                    mMeeting = (Meeting) meeting.clone();         // Clone the Meeting to have the possibility to discard changes
                    break;
                }
            }
            loadData();
        }
    }

    private void loadData(){            // call if edit mode, load all Meeting data in ui
        mCalendar = mMeeting.getStartDate();
        mDuration = mMeeting.getDuration();
        mRoom = mMeeting.getRoom();
        mParticipantList = mMeeting.getParticipants();

        mBinding.addMeetingSubjectTv.setText(mMeeting.getSubject());
        mBinding.addMeetingDateEt.setText(getDateFromCal(mCalendar));
        mBinding.addMeetingTimeEt.setText(Tools.getTimeFromCal(mCalendar));
        mBinding.addMeetingDurationEt.setText(Tools.getTimeFromCal(mDuration));

        if(mRoomsList.indexOf(mRoom) == mRoomsList.size()-1){ // Create a dummy chip to avoid the last one is cut when scrolled to
            Chip chip = new Chip(this);
            chip.setVisibility(View.INVISIBLE);
            mBinding.addMeetingRoomsCpg.addView(chip);
        }

        mBinding.addMeetingRoomsCpg.post(() -> { // Scroll to selected Room Chip via his index in the list
            Chip chip = mBinding.addMeetingRoomsCpg.findViewById(mRoomsList.indexOf(mRoom));
            mBinding.addMeetingRoomsCpg.check(chip.getId());
            mBinding.addMeetingRoomsScv.smoothScrollTo(chip.getLeft(), 0);
        });

        showAvailabilityMessage();

        Arrays.fill(mCheckFields, true);  // set all check fields to true
    }

    private int checkField(){
        if(!mBinding.addMeetingSubjectTv.getText().toString().equals("")){                      // Check if subject edit text is fill
            mCheckFields[FIELD_SUBJECT] = true;
        }
        // Check if at least 2 participant are in the list
        mCheckFields[FIELD_PARTICIPANTS_AMOUNT] = mParticipantList.size() >= 2;
        if(!mBinding.addMeetingSubjectTv.getText().toString().equals(mMeeting.getSubject())){   // Check if subject field is different than original, for edit mode
            mCheckFieldsEditMode = true;
        }
        if(mEditMode && !mRoom.getId().equals(mMeeting.getRoom().getId())){                     // Check if selected room is different than original, for edit mode
            mCheckFieldsEditMode = true;
        }

        int checkScore = 0;                      // Init score to 0
        for(boolean checkField : mCheckFields) { // For all field ok increment score
            if (checkField) {
                checkScore++;
            }
        }
        return checkScore;
    }

    private boolean save() {
        if(checkField() != mCheckFields.length){        // If all mandatory fields are not set, show toast and quit save method
            Toast.makeText(this, getString(R.string.fill_all_fields_message), Toast.LENGTH_SHORT).show();
            return false;
        }

        mMeeting.setSubject(mBinding.addMeetingSubjectTv.getText().toString());      // Update local Meeting with all data needed
        mMeeting.setStartDate(mCalendar);
        mMeeting.setEndDate(mDuration);
        mMeeting.setRoom(mRoom);
        mMeeting.setParticipants(mParticipantList);

        if (mEditMode){                                                             // If Edit mode : update Meeting to service and send his index to listMeetingActivity
            mService.updateMeeting(mMeeting);
            resultIntent.putExtra(BUNDLE_EXTRA_MEETING_EDITED_AT, mService.getMeetingList().indexOf(mMeeting));
        } else {                                                                    // If add mode : add Meeting to service and send his index to listMeetingActivity
            mService.addMeeting(mMeeting);
            resultIntent.putExtra(BUNDLE_EXTRA_MEETING_ADDED_AT, mService.getMeetingList().indexOf(mMeeting));
        }
        setResult(RESULT_OK, resultIntent);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {     // Assign menu with save icon to activity
        getMenuInflater().inflate(R.menu.add_meeting_action_bar_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // When action bar icon clicked
        switch (item.getItemId()) {
            case android.R.id.home : {                    // return to listMeetingActivity
                openDiscardDialog();
                return true;
            }
            case R.id.add_meeting_save : {               // Save Meeting
                if(save()){
                    finish();
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() { // Display arrow in action bar an set title for the current mode
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        if(mEditMode) {
            actionBar.setTitle(getString(R.string.edit_meeting_title));
        } else {
            actionBar.setTitle(getString(R.string.add_meeting_title));
        }
    }

    private void setupRecyclerView() { // setup recycler view with participant list
        mAdapter = new ListParticipantAdapter(mParticipantList);
        mBinding.addMeetingListParticipantsRv.setLayoutManager(new LinearLayoutManager(this));
        mBinding.addMeetingListParticipantsRv.setAdapter(mAdapter);
    }

    @Subscribe
    public void onDeleteParticipantEvent(DeleteParticipantEvent event) {
        int index = mParticipantList.indexOf(event.participant);
        mParticipantList.remove(event.participant);
        mAdapter.notifyItemRemoved(index);
        mCheckFieldsEditMode = true;
    }

    private void closeKeyboard() {  // Get input manager and hide keyboard
        View view = getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
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