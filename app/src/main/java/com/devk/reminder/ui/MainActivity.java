package com.devk.reminder.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.devk.reminder.R;
import com.devk.mapper.data.entity.Reminder;
import com.devk.reminder.data.viewmodel.ReminderViewModel;
import com.devk.reminder.ui.ReminderListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddReminderDialog.OnCompleteListener {

    private ReminderViewModel mReminderViewModel;

    public static final int NEW_REMINDER_ACTIVITY_REQUEST_CODE = 1;

    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton  = findViewById(R.id.fab);

        RecyclerView recyclerView = findViewById(R.id.reminer_recycler_view);
        final ReminderListAdapter reminderListAdapter = new ReminderListAdapter(this);
        recyclerView.setAdapter(reminderListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mReminderViewModel = ViewModelProviders.of(MainActivity.this).get(ReminderViewModel.class);

        mReminderViewModel.getReminders().observe(this, new Observer<List<Reminder>>() {
            @Override
            public void onChanged(List<Reminder> reminders) {
                Toast.makeText(MainActivity.this,"Total : "+reminders.size(),Toast.LENGTH_LONG).show();
                reminderListAdapter.setmReminders(reminders);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,AddReminderActivity.class);
//                startActivityForResult(intent,NEW_REMINDER_ACTIVITY_REQUEST_CODE);


                AddReminderDialog addReminderDialog = AddReminderDialog.newInstance();
                addReminderDialog.show(MainActivity.this.getSupportFragmentManager(),"Add_Reminder_Dialog");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onComplete(Reminder rem) {

        Reminder reminder = new Reminder(rem.Reminder,rem.IsDateTimeInfoSet,rem.DateInfo,rem.TimeInfo,rem.IsPlaceInfoSet,rem.PlaceInfo);
        mReminderViewModel.insertReminder(reminder);
    }
}
