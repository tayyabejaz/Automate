/*
 * Copyright 2015 Blanyal D'Souza.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.innovidio.androidbootstrap.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.Observer;

import com.innovidio.androidbootstrap.entity.Alarm;
import com.innovidio.androidbootstrap.repository.AlarmRepository;
import com.innovidio.androidbootstrap.viewmodel.AlarmViewModel;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.android.DaggerBroadcastReceiver;


public class BootReceiver extends DaggerBroadcastReceiver {

    @Inject
    AlarmRepository alarmRepository;
    private String mTime;
    private String mDate;
    private String mRepeatNo;
    private String mRepeatType;
    private boolean mActive;
    private String mRepeat;
    private String[] mDateSplit;
    private String[] mTimeSplit;
    private int mYear, mMonth, mHour, mMinute, mDay, mReceivedID;
    private long mRepeatTime;

    private Calendar mCalendar;
    private AlarmReceiver mAlarmReceiver;

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;

    @Inject
    AlarmViewModel alarmViewModel;


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            //   ReminderDatabase rb = new ReminderDatabase(context);
            mCalendar = Calendar.getInstance();
            mAlarmReceiver = new AlarmReceiver();

            alarmViewModel.getAllAlarms().observeForever(new Observer<List<Alarm>>() {
                @Override
                public void onChanged(List<Alarm> alarms) {
                    if (alarms != null) {
                        setAlarms(context, alarms);
                    }

                }
            });
        }
    }

    private void setAlarms(Context context, List<Alarm> alarmList){
       // List<Alarm> reminders=null;// = rb.getAllReminders();
        for (Alarm rm : alarmList) {
            mReceivedID = rm.getAlarmID();
           // mRepeat = rm.getRepeat();
          //  mRepeatNo = rm.getRepeatNo();
          //  mRepeatType = rm.getRepeatType();
            mActive = rm.isActive();
//            mDate = rm.getDate();
//            mTime = rm.getTime();

            mDateSplit = mDate.split("/");
            mTimeSplit = mTime.split(":");

            mDay = Integer.parseInt(mDateSplit[0]);
            mMonth = Integer.parseInt(mDateSplit[1]);
            mYear = Integer.parseInt(mDateSplit[2]);
            mHour = Integer.parseInt(mTimeSplit[0]);
            mMinute = Integer.parseInt(mTimeSplit[1]);

            mCalendar.set(Calendar.MONTH, --mMonth);
            mCalendar.set(Calendar.YEAR, mYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
            mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
            mCalendar.set(Calendar.MINUTE, mMinute);
            mCalendar.set(Calendar.SECOND, 0);

            // Cancel existing notification of the reminder by using its ID
            // mAlarmReceiver.cancelAlarm(context, mReceivedID);

            // Check repeat type
            if (mRepeatType.equals("Minute")) {
                mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
            } else if (mRepeatType.equals("Hour")) {
                mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
            } else if (mRepeatType.equals("Day")) {
                mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
            } else if (mRepeatType.equals("Week")) {
                mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
            } else if (mRepeatType.equals("Month")) {
                mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
            }

            // Create a new notification
            if (mActive) {
                if (mRepeat.equals("true")) {
                    mAlarmReceiver.setRepeatAlarm(context, mCalendar, mReceivedID, mRepeatTime);
                } else if (mRepeat.equals("false")) {
                  //  mAlarmReceiver.setAlarm(context, mCalendar, mReceivedID);
                }
            }
        }
    }
}