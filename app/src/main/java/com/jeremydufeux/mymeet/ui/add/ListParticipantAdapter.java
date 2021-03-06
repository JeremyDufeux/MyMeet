package com.jeremydufeux.mymeet.ui.add;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jeremydufeux.mymeet.R;
import com.jeremydufeux.mymeet.databinding.ItemParticipantBinding;
import com.jeremydufeux.mymeet.event.DeleteParticipantEvent;
import com.jeremydufeux.mymeet.model.Participant;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ListParticipantAdapter extends RecyclerView.Adapter<ListParticipantAdapter.ParticipantHolder> {
    private final List<Participant> mParticipantsList;

    public ListParticipantAdapter(List<Participant> participantsList) {
        mParticipantsList = participantsList;
    }

    @NonNull
    @Override
    public ParticipantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_participant, parent, false);
        return new ParticipantHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantHolder holder, int position) { // Set Meeting data for each view holder
        Participant participant = mParticipantsList.get(position);

        holder.mBinding.participantItemEmailEt.setText(participant.getEmail());

        holder.mBinding.participantItemRemoveBtn.setOnClickListener(v -> EventBus.getDefault().post(new DeleteParticipantEvent(participant)));
    }

    @Override
    public int getItemCount() {
        return mParticipantsList.size();
    }

    public class ParticipantHolder extends RecyclerView.ViewHolder{
        ItemParticipantBinding mBinding;

        public ParticipantHolder(View itemView) {
            super(itemView);
            mBinding = ItemParticipantBinding.bind(itemView);
        }
    }
}
