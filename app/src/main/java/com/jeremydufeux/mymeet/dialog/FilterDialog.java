package com.jeremydufeux.mymeet.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.jeremydufeux.mymeet.R;
import com.jeremydufeux.mymeet.di.DI;
import com.jeremydufeux.mymeet.model.Room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.jeremydufeux.mymeet.utils.Tools.createChipStateColors;
import static com.jeremydufeux.mymeet.utils.Tools.getCalendarFromDate;

public class FilterDialog extends DialogFragment implements Chip.OnCheckedChangeListener{
    private FilterDialogListener onSetListener;

    private List<Room> mRoomList;
    private HashMap<String, Boolean> mRoomSelection;
    private Calendar mDateSelection;

    private Chip mChipAll;
    private List<Chip> mChipList;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_filter, null);

        mRoomList = DI.getMeetingApiService().getRoomList();                                 // Get room list
        if(mRoomSelection==null) {                                                           // Create a Hashpmap to store room number as a key string and selection as boolean
            mRoomSelection = new HashMap<>();
            setRoomSelectionToAll();                                                         // Select all rooms
        }

        CalendarView calendarView = mView.findViewById(R.id.dialog_filter_cal);              // get Calendar view and set listener to grab selected date
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> mDateSelection = getCalendarFromDate(year, month, dayOfMonth, 0, 0));
        if(mDateSelection!=null) calendarView.setDate(mDateSelection.getTimeInMillis());   // if date selection already set, select it in calendar

        ChipGroup chipGroup = mView.findViewById(R.id.dialog_filter_rooms_cpg);             // get chipGroup
        mChipList = new ArrayList<>();
        
        mChipAll = new Chip(getActivity());                                                 // Create chip that represent all rooms and select it depending of mRoomSelection
        mChipAll.setText(R.string.all);
        mChipAll.setTag("all");
        mChipAll.setCheckable(true);
        mChipAll.setChecked(Objects.requireNonNull(mRoomSelection.get("all")));
        mChipAll.setChipBackgroundColor(createChipStateColors(getActivity()));
        mChipAll.setOnCheckedChangeListener(this);
        chipGroup.addView(mChipAll);

        for (int i = 0; i < mRoomList.size(); i++) {                                        // Create chip for each room and select it depending of mRoomSelection
            Room room = mRoomList.get(i);
            mChipList.add(new Chip(getActivity()));
            String roomTitle = String.format(Locale.getDefault(), "%s %d", getString(R.string.room), room.getNumber());
            mChipList.get(i).setText(roomTitle);
            mChipList.get(i).setTag(room.getNumber());
            mChipList.get(i).setCheckable(true);
            mChipList.get(i).setChecked(Objects.requireNonNull(mRoomSelection.get(Integer.toString(room.getNumber()))));
            mChipList.get(i).setChipBackgroundColor(createChipStateColors(getActivity()));
            mChipList.get(i).setOnCheckedChangeListener(this);
            chipGroup.addView(mChipList.get(i));
        }

        builder.setPositiveButton(R.string.ok, (dialog, which) -> onSetListener.onFilterSet(mDateSelection, mRoomSelection)); // Set positive button to send filter to listener
        builder.setNegativeButton(R.string.cancel, null);
        builder.setNeutralButton(R.string.clear_filters, (dialog, which) -> onSetListener.onClearFilter());                   // Set neutral button to clear filter to listener

        View header = inflater.inflate(R.layout.dialog_custom_header, null);      // Inflate custom header and set title
        TextView titleTv = header.findViewById(R.id.dialog_custom_header_title);
        titleTv.setText(R.string.filter_dialog_title);
        builder.setCustomTitle(header);

        builder.setView(mView);
        return builder.create();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getTag().equals("all")){
            if(isChecked){                                  // If "all" Chip is checked, uncheck all room Chip
                for (int i = 0; i < mRoomList.size(); i++) {
                    mChipList.get(i).setChecked(false);
                }
                setRoomSelectionToAll();                    // change Hashmap selection
            }
        } else {
            if(mChipAll.isChecked() && isChecked){          // if "all" checked, uncheck it and turn his value to false in the hashmap
                mChipAll.setChecked(false);
                mRoomSelection.put("all", false);
            }
            mRoomSelection.put(buttonView.getTag().toString(), isChecked); // Set checked room to true in the hashmap
        }
    }

    private void setRoomSelectionToAll(){ // set hashmap key "all" to true and all the other one to false
        mRoomSelection.put("all", true);
        for (int i = 0; i < mRoomList.size(); i++) {
            mRoomSelection.put(Integer.toString(mRoomList.get(i).getNumber()), false);
        }
    }

    public void setRoomSelection(HashMap<String, Boolean> roomSelection) {
        mRoomSelection = roomSelection;
    }

    public void setDateSelection(Calendar dateSelection) {
        mDateSelection = dateSelection;
    }

    @Override
    public void onStart() {
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawableResource(R.drawable.shape_dialog_borders);  // Add shape to window
        super.onStart();
    }

    public void setFilterListener(FilterDialogListener onSetListener) {
        this.onSetListener = onSetListener;
    }

    public interface FilterDialogListener {
        void onFilterSet(Calendar dateSelection, HashMap<String, Boolean> roomSelection);
        void onClearFilter();
    }
}