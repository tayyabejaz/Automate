package com.innovidio.androidbootstrap.alarms;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SetAlarm {
    private void addReminder(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        int mHour=0,mMinute = 0;

//        String mDate = releaseDate.get(Calendar.DATE) + "/" + (releaseDate.get(Calendar.MONTH)+1) + "/" + releaseDate.get(Calendar.YEAR);
//        if(SharedPreferenceHelper.getInstance().getStringValue(Constants.MOVIE_TIME,"").equalsIgnoreCase("")){
//            Calendar myCal = Calendar.getInstance();
//            mHour = myCal.get(Calendar.HOUR_OF_DAY);
//            mMinute = myCal.get(Calendar.MINUTE);
//        }else{
//            String time[] = SharedPreferenceHelper.getInstance().getStringValue(Constants.MOVIE_TIME,"").split(":");
//            mHour = Integer.parseInt(time[0]);
//            mMinute =Integer.parseInt(time[1]);
//        }
//        String mTime = mHour+":"+mMinute;

     //   int ID = rb.addReminder(new Reminder(moviesResponse.getTitle(), mDate, mTime, "true", "1", "Day",String.valueOf( moviesResponse.getId()), "true", moviesResponse.getReleaseDate(), moviesResponse.getPosterPath(), "true"));

        // Set up calender for creating the notification
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.MONTH, 1);
        mCalendar.set(Calendar.YEAR, 1);
        mCalendar.set(Calendar.DAY_OF_MONTH,1);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);

        final long milDay = 86400000L;

        new AlarmReceiver().setRepeatAlarm(context, mCalendar, 1000, milDay);
//        binding.ivAddReminder.setCardBackgroundColor(getResources().getColor(R.color.colorBlackAlpha));
//        binding.ivBell.setImageDrawable(getResources().getDrawable(R.drawable.notification_bell_selected));
//        Toasty.success(this,"Reminder Added").show();
    }
}
