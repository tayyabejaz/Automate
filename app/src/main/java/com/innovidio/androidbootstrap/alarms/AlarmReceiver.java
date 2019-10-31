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


import android.app.*;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.activity.MainActivity;
import com.innovidio.androidbootstrap.db.dao.AlarmDao;
import com.innovidio.androidbootstrap.entity.Alarm;
import com.innovidio.androidbootstrap.repository.AlarmRepository;
import com.innovidio.androidbootstrap.viewmodel.AlarmViewModel;


import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.DaggerBroadcastReceiver;

import static com.innovidio.androidbootstrap.Constants.ALARM_ID;
import static com.innovidio.androidbootstrap.Constants.ALARM_MSG;
import static com.innovidio.androidbootstrap.Constants.ALARM_TYPE;
import static com.innovidio.androidbootstrap.Constants.id;


public class AlarmReceiver extends DaggerBroadcastReceiver {
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;

    @Inject
    AlarmRepository alarmRepository;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        int id = intent.getExtras().getInt(ALARM_ID, 0);
        String message = intent.getExtras().getString(ALARM_MSG, "Time to maintaining data");
        String type = intent.getExtras().getString(ALARM_TYPE, "Custom");
        int maintenanceId = intent.getExtras().getInt(ALARM_TYPE, 0);

        Log.e("alarmD", message);
        Toast.makeText(context, "alarm run", Toast.LENGTH_SHORT).show();
        List<Alarm> alarmList = alarmRepository.getAllAlarms();
        for (Alarm alarm : alarmList) {
            if (alarm.getAlarmID() ==  id && alarm.isActive()) {
                alarm.setExpired(true);
                runAlarm(context, alarm);
            }
        }

    }

    private void runAlarm(Context context, Alarm alarm) {
//        try {
//            Date curDate = new Date();
//            if (alarm.getExecutionTime().after(curDate)){
//                cancelAlarm(context, mReceivedID);
//            } else {
            showNotification(context, alarm);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void setAlarm(Context context, Calendar calendar, Alarm alarm) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        int ID =  alarm.getAlarmID();
        String message =  alarm.getAlarmMessage();
        String alarmType =  alarm.getAlarmType().name();

        // Put Reminder ID in Intent Extra
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(ALARM_ID, ID);
        intent.putExtra(ALARM_MSG, message);
        intent.putExtra(ALARM_TYPE, alarmType);

        mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Calculate notification time
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        long diffTime = calendar.getTimeInMillis() - currentTime;

        // Start alarm using notification time
        mAlarmManager.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + diffTime,
                mPendingIntent);

        // Restart alarm if device is rebooted
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void setRepeatAlarm(Context context, Calendar calendar, int ID, long RepeatTime) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Put Reminder ID in Intent Extra
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(ALARM_ID, ID);
        mPendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Calculate notification timein
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        long diffTime = calendar.getTimeInMillis() - currentTime;

        // Start alarm using initial notification time and repeat interval time
        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + diffTime,
                RepeatTime, mPendingIntent);

        // Restart alarm if device is rebooted
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void cancelAlarm(Context context, int ID) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Cancel Alarm using Reminder ID
        mPendingIntent = PendingIntent.getBroadcast(context, ID, new Intent(context, AlarmReceiver.class), 0);
        mAlarmManager.cancel(mPendingIntent);

        // Disable alarm
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void showNotification (Context context, Alarm alarm){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(ALARM_ID, alarm.getAlarmID());
        //editIntent.putExtra(ReminderEditActivity.EXTRA_REMINDER_ID, Integer.toString(mReceivedID));
        PendingIntent mClick = PendingIntent.getActivity(context, alarm.getAlarmID(), intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Action action = new NotificationCompat.Action(R.mipmap.ic_launcher_round, "View Details", mClick);

        // Create Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            CharSequence name = "Upcoming Notifications";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(alarm.getAlarmType().name())
                    .setContentText(alarm.getAlarmMessage())
                    .setChannelId(CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setLargeIcon(image)
//                    .setStyle(new NotificationCompat.BigPictureStyle()
//                            .bigPicture(image)
//                            .bigLargeIcon(null))
                    .setContentIntent(mClick)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(alarmSound)
                    .addAction(action)
                    .build();

            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);

            mNotificationManager.notify(alarm.getAlarmID(), notification);
        } else {

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(alarm.getAlarmType().name())
                    .setContentText(alarm.getAlarmMessage())
                    .setTicker(alarm.getAlarmMessage())
                  //  .setLargeIcon(image)
                  //  .setStyle(new NotificationCompat.BigPictureStyle()
                  //          .bigPicture(image)
                  //          .bigLargeIcon(null))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(mClick)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .addAction(action)
                    .setOnlyAlertOnce(true);
            NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nManager.notify(alarm.getAlarmID(), mBuilder.build());
        }

    }
}