package com.devk.reminder.ui;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.devk.reminder.R;
import com.devk.mapper.data.entity.Reminder;
import com.devk.reminder.data.viewmodel.AddReminderDialogViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddReminderDialog extends DialogFragment implements View.OnClickListener {

    private RadioButton radBtnTime;
    private RadioButton radBtnPlace;

    private ConstraintLayout includeType;

    private AddReminderDialogViewModel mViewModel;

    private View view;
    private LayoutInflater _inflater;

    private TextView etTime, etDate, etPlace;

    private EditText etReminder;
    private Button btnReminderSave;

    private OnCompleteListener mListener;

    public static AddReminderDialog newInstance() {
        return new AddReminderDialog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.add_reminder_dialog_fragment, container, false);
        _inflater = inflater;

        radBtnTime = view.findViewById(R.id.rbTime);
        radBtnPlace = view.findViewById(R.id.rbPlace);
        includeType = view.findViewById(R.id.includeReminderType);

        etDate = view.findViewById(R.id.et_date_selection);
        etTime = view.findViewById(R.id.et_time_selection);
        etPlace = view.findViewById(R.id.et_place_selection);


        etReminder = view.findViewById(R.id.editReminderText);
        btnReminderSave = view.findViewById(R.id.btnReminderSave);

        radBtnTime.setOnClickListener(this);
        radBtnPlace.setOnClickListener(this);

        etDate.setOnClickListener(this);
        etTime.setOnClickListener(this);

        btnReminderSave.setOnClickListener(this);

        radBtnTime.setChecked(true);
        setDefaultDateAndTime();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.mListener = (OnCompleteListener) getActivity();
        } catch (final ClassCastException e) {
            throw new ClassCastException(getActivity().getLocalClassName() + " must implement OnCompleteListener");
        }
    }

    private void setDefaultDateAndTime() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));

        etTime.setText(Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddReminderDialogViewModel.class);
        // TODO: Use the ViewModel

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbTime:
                if (radBtnTime.isChecked()) {
                    includeType.removeAllViews();
                    setDefaultDateAndTime();
                    includeType.addView(getTimeInputLayout());
                }
                break;
            case R.id.rbPlace:
                if (radBtnPlace.isChecked()) {
                    includeType.removeAllViews();
                    includeType.addView(getPlaceInputLayout());
                }
                break;

            case R.id.et_date_selection:
                getDatePicker();
                break;

            case R.id.et_time_selection:
                getTimePicker();
                break;

            case R.id.btnReminderSave:
                saveDataToDB();
                break;
        }
    }

    private void saveDataToDB() {

        if (checkIfDataFilled()) {
            Reminder reminder = getDataToSave();
            this.mListener.onComplete(reminder);
            dismiss();
        }
    }

    private Reminder getDataToSave() {

        Reminder reminder = new Reminder();

        String reminderText = etReminder.getText().toString();
        boolean isTimeChecked = radBtnTime.isChecked();
        String dateInfo = etDate.getText().toString();
        String timeInfo = etTime.getText().toString();

        boolean isPlaceChecked = radBtnPlace.isChecked();

        String placeInfo = "";
        if (isPlaceChecked)
            placeInfo = etPlace.getText().toString();

        reminder.Reminder = reminderText;
        reminder.IsDateTimeInfoSet = isTimeChecked;
        reminder.IsPlaceInfoSet = isPlaceChecked;
        reminder.DateInfo = dateInfo;
        reminder.TimeInfo = timeInfo;
        reminder.PlaceInfo = placeInfo;

        return reminder;

    }

    private boolean checkIfDataFilled() {
        if (TextUtils.isEmpty(etReminder.getText())) {
            return false;
        } else if (radBtnTime.isChecked() && (TextUtils.isEmpty(etDate.getText()) || TextUtils.isEmpty((etTime.getText())))) {
            return false;
        } else if (radBtnPlace.isChecked() && TextUtils.isEmpty(etPlace.getText())) {
            return false;
        }
        return true;
    }

    private View getTimeInputLayout() {
        return _inflater.inflate(R.layout.time_input_layout, null);
    }

    private View getPlaceInputLayout() {
        return _inflater.inflate(R.layout.place_input_layout, null);
    }

    private void getDatePicker() {
        final Calendar myCalendar = Calendar.getInstance();


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);

                etDate.setText(simpleDateFormat.format(myCalendar.getTime()));
            }
        };

        new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void getTimePicker() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        etTime.setText(hourOfDay + ":" + minute);
                    }
                }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, false);
        timePickerDialog.show();

    }

    public static interface OnCompleteListener {
        public abstract void onComplete(Reminder reminder);
    }
}
