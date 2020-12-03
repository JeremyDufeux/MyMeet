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

    private List<Room> roomList;
    private HashMap<Integer, Boolean> roomSelection;
    private Calendar dateSelection;

    private Chip chipAll;
    private List<Chip> chipList;


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_filter, null);

        roomList = DI.getMeetingApiService().getRoomList();
        roomSelection = new HashMap<>();

        CalendarView calendarView = mView.findViewById(R.id.dialog_filter_cal);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> dateSelection = getCalendarFromDate(year, month, dayOfMonth, 0, 0));

        ChipGroup chipGroup = mView.findViewById(R.id.dialog_filter_rooms_cpg);
        chipList = new ArrayList<>();
        
        chipAll = new Chip(getActivity());
        chipAll.setText(R.string.all);
        chipAll.setTag("all");
        chipAll.setCheckable(true);
        chipAll.setChecked(true);
        chipAll.setChipBackgroundColor(createChipStateColors(getActivity()));
        chipAll.setOnCheckedChangeListener(this);
        chipGroup.addView(chipAll);

        for (int i = 0; i < roomList.size(); i++) {
            Room room = roomList.get(i);
            chipList.add(new Chip(getActivity()));
            String roomTitle = String.format(Locale.getDefault(), "%s %d", getString(R.string.room), room.getNumber());
            chipList.get(i).setText(roomTitle);
            chipList.get(i).setTag(room.getNumber());
            chipList.get(i).setCheckable(true);
            chipList.get(i).setChipBackgroundColor(createChipStateColors(getActivity()));
            chipList.get(i).setOnCheckedChangeListener(this);
            chipGroup.addView(chipList.get(i));
            roomSelection.put(room.getNumber(), true);
        }

        builder.setPositiveButton(R.string.ok, (dialog, which) -> onSetListener.onFilterSet(dateSelection, roomSelection));
        builder.setNegativeButton(R.string.cancel, null);
        builder.setNeutralButton(R.string.clear_filters, (dialog, which) -> onSetListener.onClearFilter());

        View header = inflater.inflate(R.layout.dialog_custom_header, null);
        TextView titleTv = header.findViewById(R.id.dialog_custom_header_title);
        titleTv.setText(R.string.filter_dialog_title);
        builder.setCustomTitle(header);

        builder.setView(mView);
        return builder.create();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getTag().equals("all")){
            if(isChecked){
                for (int i = 0; i < roomList.size(); i++) {
                    chipList.get(i).setChecked(false);
                    roomSelection.put(roomList.get(i).getNumber(), true);
                }
            }
        } else {
            if(chipAll.isChecked() && isChecked){
                chipAll.setChecked(false);
                for (Room room : roomList){
                    roomSelection.put(room.getNumber(), false);                    
                }
            }
            roomSelection.put((Integer)buttonView.getTag(), isChecked);
        }
    }

    @Override
    public void onStart() {
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawableResource(R.drawable.dialog_shape);
        super.onStart();
    }

    public void setFilterListener(FilterDialogListener onSetListener) {
        this.onSetListener = onSetListener;
    }

    public interface FilterDialogListener {
        void onFilterSet(Calendar dateSelection, HashMap<Integer, Boolean> roomSelection);
        void onClearFilter();
    }
}
