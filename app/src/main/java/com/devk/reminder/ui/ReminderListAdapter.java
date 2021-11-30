package com.devk.reminder.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devk.mapper.data.entity.Reminder;
import com.devk.reminder.R;

import java.util.List;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ReminderViewHolder> {

    private final LayoutInflater mInflater;
    private List<Reminder> mReminders; // Cached copy of words

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_reminder_item,parent,false);
        return new ReminderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
        if(mReminders != null){
            Reminder reminder = mReminders.get(position);
            holder.reminderTextView.setText(reminder.Reminder);

        }else{
            holder.reminderTextView.setText("No reminder yet");
        }
    }

    @Override
    public int getItemCount() {
        if(mReminders != null){
            return mReminders.size();
        }else return 0;
    }

    void setmReminders(List<Reminder> reminders){
        mReminders = reminders;
        notifyDataSetChanged();
    }

    class ReminderViewHolder extends RecyclerView.ViewHolder{
        private final TextView reminderTextView;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            reminderTextView = itemView.findViewById(R.id.tvReminder);
        }

    }

    public ReminderListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }
}
