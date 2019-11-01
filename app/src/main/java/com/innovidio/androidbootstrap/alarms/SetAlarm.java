package com.innovidio.androidbootstrap.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.innovidio.androidbootstrap.entity.Alarm;
import com.innovidio.androidbootstrap.entity.Car;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

public class SetAlarm {

    public static void addReminder(Context context,Alarm alarm) {
        Calendar mCalendar =  toCalendar(alarm.getExecutionTime());
        new AlarmReceiver().setAlarm(context, mCalendar, alarm);
    }

    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Date addTimeInDate(int minutes, int hours,int days, int months, int years){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
      //  c.add(Calendar.DATE, 1);
        c.add(Calendar.YEAR, years);
        c.add(Calendar.MONTH, months);
        c.add(Calendar.DAY_OF_MONTH, days);
        c.add(Calendar.HOUR_OF_DAY, hours);
        c.add(Calendar.MINUTE, minutes);
        dt = c.getTime();
        return dt;
    }

    public static void addAlarm(Context context){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.MONTH,5);
        cal.set(Calendar.YEAR,2011);
        cal.set(Calendar.DAY_OF_MONTH,29);
        cal.set(Calendar.HOUR_OF_DAY,17);
        cal.set(Calendar.MINUTE,30);


        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1253, intent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent );
        Toast.makeText(context, "Alarm Set.", Toast.LENGTH_LONG).show();
    }

    //   Calendar mCalendar = Calendar.getInstance();
    // mCalendar.set(Calendar.MONTH, 1);
    // mCalendar.set(Calendar.YEAR, 1);
    // mCalendar.set(Calendar.DAY_OF_MONTH,1);
    // mCalendar.set(Calendar.HOUR_OF_DAY, 17);
    //    mCalendar.set(Calendar.MINUTE, 1);
    //  mCalendar.set(Calendar.SECOND, 0);
}
