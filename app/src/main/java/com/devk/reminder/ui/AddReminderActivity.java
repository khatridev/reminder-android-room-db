package com.devk.reminder.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.devk.reminder.R;

public class AddReminderActivity extends AppCompatActivity {

    private EditText mReminderText;
    private Button mSaveReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        mReminderText = findViewById(R.id.editReminderText);
        mSaveReminder = findViewById(R.id.btnReminderSave);

        mSaveReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(TextUtils.isEmpty(mReminderText.getText())){
                    setResult(RESULT_CANCELED,intent);
                }else{
                    String reminderText =mReminderText.getText().toString();
                    intent.putExtra("REMINDER",reminderText);
                    setResult(RESULT_OK,intent);
                }
                finish();
            }
        });


    }
}
