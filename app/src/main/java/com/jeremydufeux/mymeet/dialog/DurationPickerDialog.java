package com.jeremydufeux.mymeet.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.jeremydufeux.mymeet.R;

import java.util.Locale;
import java.util.Objects;

public class DurationPickerDialog extends DialogFragment {
    private final static String[] DISPLAYED_MINS = { "0", "10", "20", "30", "40", "50" };
    private DurationPickerDialogListener valueChangeListener;
    private int hour = 0;
    private int minute = 0;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_duration_picker, null);

        NumberPicker numberPicker1 = mView.findViewById(R.id.dialog_duration_hour_np);
        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(23);
        numberPicker1.setValue(hour);


        NumberPicker numberPicker2 = mView.findViewById(R.id.dialog_duration_minute_np);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(DISPLAYED_MINS.length-1);
        numberPicker2.setDisplayedValues(DISPLAYED_MINS);

        int minuteSelection = 0;
        for (int i = 0; i < DISPLAYED_MINS.length; i++) {
            if(DISPLAYED_MINS[i].equals(String.format(Locale.getDefault(),"%02d", minute))){
                minuteSelection = i;
            }
        }
        numberPicker2.setValue(minuteSelection);

        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            hour = numberPicker1.getValue();
            minute = numberPicker2.getValue()*10;

            valueChangeListener.onDurationSet(hour, minute);
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
        });

        View header = inflater.inflate(R.layout.dialog_custom_header, null);
        TextView titleTv = header.findViewById(R.id.dialog_custom_header_title);
        titleTv.setText(R.string.select_meeting_duration);
        builder.setCustomTitle(header);

        builder.setView(mView);
        return builder.create();
    }

    @Override
    public void onStart() {
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawableResource(R.drawable.shape_dialog_borders);
        super.onStart();
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setDurationSetListener(DurationPickerDialogListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public interface DurationPickerDialogListener {
        void onDurationSet(int hour, int minute);
    }
}
