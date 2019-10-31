package com.innovidio.androidbootstrap.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.CustomDeleteDialog;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.adapter.GeneralCarSpinnerAdapter;
import com.innovidio.androidbootstrap.adapter.SingleReminderAdapter;
import com.innovidio.androidbootstrap.databinding.ActivityReminderBinding;
import com.innovidio.androidbootstrap.databinding.DialogAddReminderBinding;
import com.innovidio.androidbootstrap.entity.Alarm;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.interfaces.OnAlarmCardListener;
import com.innovidio.androidbootstrap.viewmodel.AlarmViewModel;
import com.innovidio.androidbootstrap.viewmodel.CarViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class ReminderActivity extends DaggerAppCompatActivity implements OnAlarmCardListener {

    @Inject
    AlarmViewModel alarmViewModel;

    @Inject
    CarViewModel carViewModel;

    private SingleReminderAdapter adapter;
    private ActivityReminderBinding binding;
    private GeneralCarSpinnerAdapter carAdapter;
    private int carID;
    private String sDate, sTime;
    private ArrayList<Car> carArrayList = new ArrayList<>();
    private List<Alarm> datalist = new ArrayList<>();
    private CustomDeleteDialog deleteDialog;
    private final Calendar todaysCalender = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reminder);
        initializeAdapter();
        checkScreenEmptyState();
    }

    private void initializeAdapter() {
        adapter = new SingleReminderAdapter(this, this, datalist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvReminderScreen.setLayoutManager(layoutManager);
        binding.rvReminderScreen.setAdapter(adapter);

        carAdapter = new GeneralCarSpinnerAdapter(this, carArrayList);
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkScreenEmptyState();

        alarmViewModel.getAllAlarms().observe(this, alarms -> {
            if (alarms != null) {
                datalist = alarms;
                adapter.updateList(datalist);
                checkScreenEmptyState();
            }
        });

        binding.fbAddReminder.setOnClickListener(v -> {
            showAddReminderDialog(null);
        });

        binding.ivRemindersBackBtn.setOnClickListener(view -> {
            finish();
        });

    }

    private void checkScreenEmptyState() {
        if (datalist.size() == 0) {
            binding.llEmptyState.setVisibility(View.VISIBLE);
        } else {
            binding.llEmptyState.setVisibility(View.GONE);
        }
    }

    private void showAddReminderDialog(Alarm alarm) {
        if (alarm == null) {
            Alarm alarm1 = new Alarm();
            DialogAddReminderBinding binding;
            binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_add_reminder, null, false);
            View dialogView = binding.getRoot();
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            final AlertDialog addReminderDialog = dialogBuilder.create();
            addReminderDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            addReminderDialog.setView(dialogView);
            addReminderDialog.show();

            binding.spinnerSelectCar.setAdapter(carAdapter);

            binding.spinnerSelectCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    carID = carArrayList.get(i).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
                todaysCalender.set(Calendar.YEAR, i);
                todaysCalender.set(Calendar.MONTH, i1);
                todaysCalender.set(Calendar.DAY_OF_MONTH, i2);
                sDate = UtilClass.updateDate(todaysCalender, binding.etDate);
            };

            binding.etDate.setOnClickListener(view -> {
                UtilClass.showDatePicker(this, todaysCalender, date);
            });

            TimePickerDialog.OnTimeSetListener time = (timePicker, i, i1) -> {
                todaysCalender.set(Calendar.HOUR_OF_DAY, i);
                todaysCalender.set(Calendar.MINUTE, i1);
                sTime = UtilClass.updateTime(todaysCalender, binding.etTime);
            };

            binding.etTime.setOnClickListener(view -> {
                UtilClass.showTimePicker(this, todaysCalender, time);
            });

            binding.btnSetReminder.setOnClickListener(view -> {
                alarm1.setExecutionTime(UtilClass.convertToDate(sDate, sTime));
                alarm1.setActive(true);
                alarm1.setAlarmType(Alarm.AlarmType.CUSTOM);
                alarm1.setCreationDate(new Date());
                alarm1.setAlarmMessage(binding.etReminderName.getText().toString());
                alarmViewModel.addAlarm(alarm1);
                datalist.add(alarm1);
                checkScreenEmptyState();
                addReminderDialog.dismiss();
            });
        }
    }

    private void createDeleteDialog(Alarm alarm) {
        deleteDialog = new CustomDeleteDialog(this, "Delete Reminder", "Are you sure you want to delete this reminder?", "No", "Yes", R.drawable.automate_delete_reminder_dialog_icon) {
            @Override
            public void onNegativeBtnClick(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveBtnClick(Dialog dialog) {
                alarmViewModel.deleteAlarm(alarm);
                dialog.dismiss();

            }
        };
    }

    @Override
    public void onDeleteClicked(Alarm alarm) {
        createDeleteDialog(alarm);
    }

    @Override
    public void onEditClicked(Alarm alarm) {
        showAddReminderDialog(alarm);
    }

    @Override
    public void onAlarmOnOffButton(Alarm alarm) {
        if (alarm.isActive()) {
            alarm.setActive(false);
            Toast.makeText(this, "Alarm is Active", Toast.LENGTH_SHORT).show();
        }
        else {
            alarm.setActive(true);
            Toast.makeText(this, "Alarm is not Active", Toast.LENGTH_SHORT).show();
        }
    }
}
