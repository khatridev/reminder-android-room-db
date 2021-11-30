package com.devk.reminder.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.devk.mapper.data.entity.Reminder;

import java.util.List;

@Dao
public interface ReminderDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Reminder reminder);

    @Query("DELETE FROM reminder_table")
    void deleteAll();

    @Query("SELECT * from reminder_table")
    LiveData<List<Reminder>> getAllReminders();
}
