package com.devk.reminder.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.devk.reminder.data.dao.ReminderDAO;
import com.devk.mapper.data.entity.Reminder;
import com.devk.reminder.data.dao.ReminderDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Reminder.class}, version = 2, exportSchema = false)
public abstract class ReminderDatabase extends RoomDatabase {
    // gets called from Repo
    public abstract ReminderDAO reminderDAO();


    private static final String REMINDER_DB_NAME = "reminder_table";
    // code to synchronise the db access
    private static volatile ReminderDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =  Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ReminderDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ReminderDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ReminderDatabase.class, REMINDER_DB_NAME)
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE "+ REMINDER_DB_NAME + " ADD COLUMN DateInfo TEXT");
        database.execSQL("ALTER TABLE "+ REMINDER_DB_NAME + " ADD COLUMN TimeInfo TEXT");
        database.execSQL("ALTER TABLE "+ REMINDER_DB_NAME + " ADD COLUMN PlaceInfo TEXT");
        }
    };
}
