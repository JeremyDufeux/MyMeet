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
    private int mHour = 0;
    private int mMinute = 0;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) { // Create the duration picker dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_duration_picker, null); // inflate dialog_duration_picker.xml

        NumberPicker hoursPicker = mView.findViewById(R.id.dialog_duration_hour_np); // Get hours numberPicker and set his min and max values and default values
        hoursPicker.setMinValue(0);
        hoursPicker.setMaxValue(23);
        hoursPicker.setValue(mHour);


        NumberPicker minutesPicker = mView.findViewById(R.id.dialog_duration_minute_np); // setup minutes picker to display the array of minutes
        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(DISPLAYED_MINS.length-1);
        minutesPicker.setDisplayedValues(DISPLAYED_MINS);

        int minuteSelection = 0;
        for (int i = 0; i < DISPLAYED_MINS.length; i++) {                               // get index of selected minutes
            if(DISPLAYED_MINS[i].equals(String.format(Locale.getDefault(),"%02d", mMinute))){
                minuteSelection = i;
            }
        }
        minutesPicker.setValue(minuteSelection);

        builder.setPositiveButton(R.string.ok, (dialog, which) -> {                           // Set positive button to send selected values to listener
            mHour = hoursPicker.getValue();
            mMinute = minutesPicker.getValue()*10;

            valueChangeListener.onDurationSet(mHour, mMinute);
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
        });

        View header = inflater.inflate(R.layout.dialog_custom_header, null);      // Inflate custom header and set title
        TextView titleTv = header.findViewById(R.id.dialog_custom_header_title);
        titleTv.setText(R.string.select_meeting_duration);
        builder.setCustomTitle(header);

        builder.setView(mView);
        return builder.create();
    }

    @Override
    public void onStart() {
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawableResource(R.drawable.shape_dialog_borders);  // Add shape to window
        super.onStart();
    }

    public void setHour(int hour) {
        this.mHour = hour;
    }

    public void setMinute(int minute) {
        this.mMinute = minute;
    }

    public void setDurationSetListener(DurationPickerDialogListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public interface DurationPickerDialogListener {
        void onDurationSet(int hour, int minute);
    }
}
