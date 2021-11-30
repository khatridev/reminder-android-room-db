package com.devk.reminder.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.devk.reminder.data.ReminderDatabase;
import com.devk.reminder.data.dao.ReminderDAO;
import com.devk.mapper.data.entity.Reminder;

import java.util.List;

public class ReminderRepo {
    private ReminderDAO mReminderDAO;
    private LiveData<List<Reminder>> mAllReminders;

    public ReminderRepo(Application application){
        ReminderDatabase reminderDatabase = ReminderDatabase.getDatabase(application);
        mReminderDAO = reminderDatabase.reminderDAO();
        mAllReminders = mReminderDAO.getAllReminders();
    }

    // function to get all the reminders from db , observed by LiveData
    public LiveData<List<Reminder>> getRemindersFromDB(){
        return mAllReminders;
    }

    // Function to add a reminder to db
    public void insertReminder(Reminder reminder){
        ReminderDatabase.databaseWriteExecutor.execute(()->{
            mReminderDAO.insert(reminder);
        });
    }

}
