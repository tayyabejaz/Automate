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

import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.activity.MainActivity;
import com.innovidio.androidbootstrap.entity.Alarm;
import com.innovidio.androidbootstrap.viewmodel.AlarmViewModel;


import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static com.innovidio.androidbootstrap.Constants.ALARM_ID;
import static com.innovidio.androidbootstrap.Constants.id;


public class AlarmReceiver extends WakefulBroadcastReceiver {
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;

    @Inject
    AlarmViewModel alarmViewModel;

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getExtras().getInt(ALARM_ID, 0);

      alarmViewModel.getAlarmById(id).observeForever( new Observer<Alarm>() {
          @Override
          public void onChanged(Alarm alarm) {
              if (alarm!=null){
                  if (alarm.isActive()){
                      runAlarm(context);
                  }
              }

          }
      });

    }

    private void runAlarm(Context context){
        try {
            int mReceivedID= 100 ;//= Integer.parseInt(intent.getStringExtra(ReminderEditActivity.EXTRA_REMINDER_ID));
            String mTitle = "Title";
            String mPosterPath = "image url";
            String mReleaseDate = "reminder.getmReleaseDate()";

            boolean isActive = true;
            if (isActive) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d = null;
                try {
                    d = sdf.parse(mReleaseDate);
                    if (d.before(Calendar.getInstance().getTime())) {
                        cancelAlarm(context, mReceivedID);
                    } else {
                        new showNotification(context, 100, mTitle, mReleaseDate, mPosterPath).execute();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setAlarm(Context context, Calendar calendar, int ID) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Put Reminder ID in Intent Extra
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(ALARM_ID, ID);

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

    private class showNotification extends AsyncTask<Void, Void, Bitmap> {

        private Context context;
        private int mReceivedID;
        private String mPosterPath;
        private CharSequence mTitle;
        private String mReleaseDate;

        showNotification(Context context, int mReceivedID, String mTitle, String mReleaseDate, String mPosterPath) {
            this.context = context;
            this.mReceivedID = mReceivedID;
            this.mTitle = mTitle;
            this.mReleaseDate = mReleaseDate;
            this.mPosterPath = mPosterPath;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
                Bitmap image = null;

                try {
                    URL url = new URL(mPosterPath);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException ne) {
                    System.out.println(ne);
                }


            return image;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            super.onPostExecute(image);
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(ALARM_ID, mReceivedID);
            //editIntent.putExtra(ReminderEditActivity.EXTRA_REMINDER_ID, Integer.toString(mReceivedID));
            PendingIntent mClick = PendingIntent.getActivity(context, mReceivedID, intent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Action action = new NotificationCompat.Action(R.mipmap.ic_launcher_round, "View Details", mClick);

            // Create Notification
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String CHANNEL_ID = "my_channel_01";// The id of the channel.
                CharSequence name = "Upcoming Movies";// The user-visible name of the channel.
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setContentTitle(mTitle)
                        .setContentText("Releasing on " + mReleaseDate)
                        .setChannelId(CHANNEL_ID)
                        .setAutoCancel(true)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(image)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(image)
                                .bigLargeIcon(null))
                        .setContentIntent(mClick)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setSound(alarmSound)
                        .addAction(action)
                        .build();

                NotificationManager mNotificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.createNotificationChannel(mChannel);

                mNotificationManager.notify(mReceivedID, notification);
            } else {

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(mTitle)
                        .setContentText("Releasing on " + mReleaseDate)
                        .setTicker(mTitle)
                        .setLargeIcon(image)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(image)
                                .bigLargeIcon(null))
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(mClick)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .addAction(action)
                        .setOnlyAlertOnce(true);
                NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                nManager.notify(mReceivedID, mBuilder.build());
            }
        }
    }
}