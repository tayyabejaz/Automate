package com.innovidio.androidbootstrap.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.CustomDeleteDialog;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.adapter.SingleReminderAdapter;
import com.innovidio.androidbootstrap.alarms.SetAlarm;
import com.innovidio.androidbootstrap.databinding.ActivityReminderBinding;
import com.innovidio.androidbootstrap.databinding.DialogAddReminderBinding;
import com.innovidio.androidbootstrap.entity.Alarm;
import com.innovidio.androidbootstrap.interfaces.OnAlarmCardListener;
import com.innovidio.androidbootstrap.viewmodel.AlarmViewModel;
import com.innovidio.androidbootstrap.viewmodel.CarViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
    private String sDate, sTime;
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
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkScreenEmptyState();
        alarmViewModel.getAllAlarms().observe(this, alarms -> {
            if (alarms != null) {
                datalist = alarms;
                Collections.sort(datalist, (o1, o2) -> o2.getCreationDate().compareTo(o1.getCreationDate()));
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
        DialogAddReminderBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_add_reminder, null, false);
        View dialogView = binding.getRoot();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final AlertDialog addReminderDialog = dialogBuilder.create();
        addReminderDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        addReminderDialog.setView(dialogView);
        addReminderDialog.show();
        if (alarm == null) {
            alarm = new Alarm();
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

            Alarm finalAlarm = alarm;
            binding.btnSetReminder.setOnClickListener(view -> {
                finalAlarm.setExecutionTime(UtilClass.convertToDate(sDate, sTime));
                finalAlarm.setActive(true);
                finalAlarm.setAlarmType(Alarm.AlarmType.CUSTOM);
                finalAlarm.setCreationDate(new Date());
                finalAlarm.setExpired(false);
                finalAlarm.setAlarmMessage(binding.etReminderName.getText().toString());
                alarmViewModel.addAlarm(finalAlarm);
                datalist.add(finalAlarm);
                checkScreenEmptyState();
                addReminderDialog.dismiss();

                alarmViewModel.getRecentAlarm().observe(this, alarm1 -> SetAlarm.addReminder(ReminderActivity.this, alarm1));
            });
        } else {
            binding.etDate.setText(alarm.getExecutionDateInString());
            binding.etTime.setText(alarm.getExecutionTimeInString());
            binding.etReminderName.setText(alarm.getAlarmMessage());

            Alarm finalAlarm1 = alarm;
            binding.btnSetReminder.setOnClickListener(view -> {
                alarmViewModel.updateAlarm(finalAlarm1);
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
        deleteDialog.createDialog();
        deleteDialog.showDialog();
    }

    @Override
    public void onDeleteClicked(Alarm alarm) {

        Toast.makeText(this, "Delete Dialog Clicked", Toast.LENGTH_SHORT).show();
        createDeleteDialog(alarm);
    }

    @Override
    public void onEditClicked(Alarm alarm) {
        Toast.makeText(this, "Edit Dialog CLicked", Toast.LENGTH_SHORT).show();
        showAddReminderDialog(alarm);
    }

    @Override
    public void onAlarmOnOffButton(Alarm alarm) {
        for (int i = 0; i < datalist.size(); i++) {
            if (alarm.getAlarmID() == datalist.get(i).getAlarmID()) {
                if (alarm.isActive()) {
                    datalist.get(i).setActive(false);
                    Toast.makeText(this, "Alarm is Active", Toast.LENGTH_SHORT).show();

                } else {
                    datalist.get(i).setActive(true);
                    Toast.makeText(this, "Alarm is not Active", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }
}
