package com.jeremydufeux.mymeet.ui.add;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jeremydufeux.mymeet.R;
import com.jeremydufeux.mymeet.databinding.ParticipantItemBinding;
import com.jeremydufeux.mymeet.event.DeleteParticipantEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ListParticipantAdapter extends RecyclerView.Adapter<ListParticipantAdapter.ParticipantHolder> {
    private List<String> mParticipantsList;

    public ListParticipantAdapter(List<String> participantsList) {
        mParticipantsList = participantsList;
    }

    @NonNull
    @Override
    public ParticipantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_item, parent, false);
        return new ParticipantHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantHolder holder, int position) {
        String participant = mParticipantsList.get(position);

        holder.mBinding.participantItemEmailEt.setText(participant);

        holder.mBinding.participantItemRemoveBtn.setOnClickListener(v -> EventBus.getDefault().post(new DeleteParticipantEvent(participant)));
    }

    @Override
    public int getItemCount() {
        return mParticipantsList.size();
    }

    public class ParticipantHolder extends RecyclerView.ViewHolder{
        ParticipantItemBinding mBinding;

        public ParticipantHolder(View itemView) {
            super(itemView);
            mBinding = ParticipantItemBinding.bind(itemView);
        }
    }
}
