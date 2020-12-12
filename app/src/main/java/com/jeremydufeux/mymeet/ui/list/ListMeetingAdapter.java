package com.jeremydufeux.mymeet.ui.list;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jeremydufeux.mymeet.R;
import com.jeremydufeux.mymeet.databinding.ItemMeetingBinding;
import com.jeremydufeux.mymeet.event.DeleteMeetingEvent;
import com.jeremydufeux.mymeet.event.OpenMeetingEvent;
import com.jeremydufeux.mymeet.model.Meeting;
import com.jeremydufeux.mymeet.model.Participant;
import com.jeremydufeux.mymeet.utils.Tools;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Locale;

public class ListMeetingAdapter extends RecyclerView.Adapter<ListMeetingAdapter.MeetingHolder> {
    private List<Meeting> mMeetingList;

    public ListMeetingAdapter(List<Meeting> meetingList) {
        mMeetingList = meetingList;
    }

    public void setMeetingList(List<Meeting> meetingList) {
        mMeetingList = meetingList;
    }

    @NonNull
    @Override
    public MeetingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meeting, parent, false);
        return new MeetingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingHolder holder, int position) { // Set Meeting data for each view holder
        Meeting meeting = mMeetingList.get(position);

        holder.mBinding.meetingItemRoomIm.getBackground().setColorFilter(meeting.getRoom().getColor(), PorterDuff.Mode.SRC_ATOP);           // Set room color via color filter background

        holder.mBinding.meetingItemRoomNumberTv.setText(String.format(Locale.getDefault(), "%d", meeting.getRoom().getNumber()));   // set room number
        holder.mBinding.meetingItemSubjectTv.setText(meeting.getSubject());                                                                 // set subject
        holder.mBinding.meetingItemDateTv.setText(Tools.getDateFromCal(meeting.getStartDate()));                                            // set date

        String timeString = String.format(Locale.getDefault(), "%s – %s",Tools.getTimeFromCal(meeting.getStartDate()), Tools.getTimeFromCal(meeting.getEndDate()));
        holder.mBinding.meetingItemTimeTv.setText(timeString);                                                                              // set time with start time and end time
        holder.mBinding.meetingItemParticipantTv.setText(getParticipantString(meeting.getParticipants()));                                  // set all participants email

        holder.mBinding.meetingItem.setOnClickListener(view -> EventBus.getDefault().post(new OpenMeetingEvent(meeting)));                  // listener to open meeting in edit mode
        holder.mBinding.meetingItemRemoveIv.setOnClickListener(view -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting)));        // listener to remove meeting
    }

    private String getParticipantString(List<Participant> participants) { // Create a single string from participant list
        StringBuilder string = new StringBuilder();
        String prefix = "";
        for (Participant participant : participants) {
            string.append(prefix);
            prefix = ", ";
            string.append(participant.getEmail());
        }

        return string.toString();
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }

    public class MeetingHolder extends RecyclerView.ViewHolder{
        ItemMeetingBinding mBinding;

        public MeetingHolder(View itemView) {
            super(itemView);
            mBinding = ItemMeetingBinding.bind(itemView);
        }
    }
}
