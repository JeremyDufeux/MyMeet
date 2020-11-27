package com.jeremydufeux.mymeet.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jeremydufeux.mymeet.R;
import com.jeremydufeux.mymeet.databinding.MeetingItemBinding;
import com.jeremydufeux.mymeet.event.DeleteMeetingEvent;
import com.jeremydufeux.mymeet.event.OpenMeetingEvent;
import com.jeremydufeux.mymeet.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListMeetingAdapter extends RecyclerView.Adapter<ListMeetingAdapter.MeetingHolder> {
    private List<Meeting> mMeetingList;

    public ListMeetingAdapter(List<Meeting> meetingList) {
        mMeetingList = meetingList;
    }

    @NonNull
    @Override
    public MeetingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_item, parent, false);
        return new MeetingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingHolder holder, int position) {
        Meeting meeting = mMeetingList.get(position);

        Glide.with(holder.mBinding.meetingItemRoomIm.getContext())
                .load(meeting.getRoom().getImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mBinding.meetingItemRoomIm);

        holder.mBinding.meetingItemRoomNumberTv.setText(String.format(Locale.FRANCE, "%d", meeting.getRoom().getNumber()));
        holder.mBinding.meetingItemSubjectTv.setText(meeting.getSubject());
        holder.mBinding.meetingItemTimeTv.setText(getTime(meeting.getDate()));
        holder.mBinding.meetingItemParticipantTv.setText(getParticipantString(meeting.getParticipants()));

        holder.mBinding.meetingItem.setOnClickListener(view -> EventBus.getDefault().post(new OpenMeetingEvent(meeting)));
        holder.mBinding.meetingItemRemoveIv.setOnClickListener(view -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting)));
    }

    private String getParticipantString(List<String> participants) {
        StringBuilder string = new StringBuilder();
        String prefix = "";
        for (String participant : participants) {
            string.append(prefix);
            prefix = ", ";
            string.append(participant);
        }

        return string.toString();
    }

    private String getTime(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.format(Locale.FRANCE, "%d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }

    public class MeetingHolder extends RecyclerView.ViewHolder{
        public MeetingItemBinding mBinding;

        public MeetingHolder(View itemView) {
            super(itemView);
            mBinding = MeetingItemBinding.bind(itemView);
        }
    }
}
