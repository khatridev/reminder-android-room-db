package com.devk.reminder.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.devk.reminder.data.ReminderRepo;
import com.devk.mapper.data.entity.Reminder;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel {

    private ReminderRepo mReminderRepo;

    private LiveData<List<Reminder>> mAllReminders;

    public ReminderViewModel(@NonNull Application application) {
        super(application);

        mReminderRepo = new ReminderRepo(application);
        mAllReminders = mReminderRepo.getRemindersFromDB();

    }

    public LiveData<List<Reminder>> getReminders() { return mAllReminders; }

    public void insertReminder(Reminder reminder) { mReminderRepo.insertReminder(reminder); ; }


}
