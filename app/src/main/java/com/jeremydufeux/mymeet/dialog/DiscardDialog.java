package com.jeremydufeux.mymeet.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.jeremydufeux.mymeet.R;

import java.util.Objects;

public class DiscardDialog extends DialogFragment {
    private DiscardDialogListener responseListener;

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        builder.setMessage(R.string.discard_dialog_message); // TODO

        builder.setPositiveButton(R.string.yes, (dialog, which) -> responseListener.onPositiveClicked());
        builder.setNegativeButton(R.string.cancel, null);

        View header = inflater.inflate(R.layout.dialog_custom_header, null);
        TextView titleTv = header.findViewById(R.id.dialog_custom_header_title);
        titleTv.setText(R.string.discard_changes);
        builder.setCustomTitle(header);

        return builder.create();
    }

    @Override
    public void onStart() {
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawableResource(R.drawable.shape_dialog_borders);
        super.onStart();
    }

    public void setDiscardDialogListener(DiscardDialogListener responseListener) {
        this.responseListener = responseListener;
    }

    public interface DiscardDialogListener {
        void onPositiveClicked();
    }
}
