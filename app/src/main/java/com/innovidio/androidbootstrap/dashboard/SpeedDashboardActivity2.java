//package com.innovidio.androidbootstrap.dashboard;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.IntentSender;
//import android.graphics.Color;
//import android.graphics.drawable.Drawable;
//import android.location.GpsStatus;
//import android.location.Location;
//import android.location.LocationManager;
//import android.media.MediaPlayer;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.BatteryManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.SystemClock;
//import android.provider.Settings;
//import android.util.Log;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.animation.Animation;
//import android.view.animation.ScaleAnimation;
//import android.widget.Chronometer;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//import androidx.core.graphics.drawable.DrawableCompat;
//import androidx.databinding.DataBindingUtil;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
//
//import com.github.anastr.speedviewlib.PointerSpeedometer;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.PendingResult;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResult;
//import com.google.android.gms.location.LocationSettingsStates;
//import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.google.android.material.snackbar.Snackbar;
//import com.innovidio.androidbootstrap.Constants;
//import com.innovidio.androidbootstrap.R;
//import com.innovidio.androidbootstrap.activity.MainActivity;
//import com.innovidio.androidbootstrap.databinding.ActivitySpeedDashboardBinding;
//import com.innovidio.androidbootstrap.entity.Trip;
//import com.innovidio.androidbootstrap.service.FloatingViewService;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//import java.util.TimeZone;
//
//public class SpeedDashboardActivity2 extends AppCompatActivity implements GpsStatus.Listener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
//    private static final String TAG = "SpeedDashboardActivity";
//    private static final String CHANNEL_ID = "channelId";
//    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 1;
//    public static String currentSpeed;
//    public boolean isTimeSet = false;
//    ActivitySpeedDashboardBinding binding;
//    SpeedDashboardModel speedDashboardModel;
//    int maximumspeed = 280;
//    String maxSpeed, avgSpeed, distance;
//    long[] vibrate = {500, 1000};
//    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//    //  HistorySaveDialog historySaveDialog;
//    boolean isClickHudBtn = false;
//    Notification.Builder notification;
//    GoogleApiClient googleApiClient;
//    ScaleAnimation scaleAnimation;
//    boolean isminimizepressed = false;
//    int tempvalue = 0;
//    boolean alreadyinitialized = false;
//    CountDownTimer countDownTimer;
//    boolean isFinished = true;
//    boolean notificationalarm = false;
//    int speedLimit;
//    int Notificationid = 1;
//    boolean speedlimitdialogclicked = false;
//    MediaPlayer mp;
//    boolean isPlaing = false;
//    Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//    int counter = 0;
//    LocationViewModel locationViewModel;
//    Location previouslocation = new Location("previous");
//    boolean isstart = true;
//    String speedUnits;
//    String distanceUnitsfromdatabase;
// //   DatabaseReference usertripsdatabase;
//    private LocationManager mLocationManager;
//    //  private DataManager dataManagerLocal = new DataManager();
//    private DataManager data;
//    String date;
//    long dateinmillis;
// //   DatabaseReference usercompleteprofile;
//    private String carname;
//    String userid;
//    String distanceUnits;
//    String carfueleconomyperkm;
//
//    // Timer timer;
//    //  Location locationp=new Location("Enterprise building , thokr lahore pakistn");
//    private BroadcastReceiver mBatteryLevelReciver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            //int rawLevel = intent.getIntExtra("level", -1);
//            int rawLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//            //int scale = intent.getIntExtra("scale", -1);
//            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//            int level = -1;
//            if (rawLevel >= 0 && scale > 0) {
//                level = (rawLevel * 100) / scale;
//            }
//            binding.battery.setText("" + level + "%");
//        }
//
//    };
//
//    public static void cancelNotification(Context ctx, int notifyId) {
//        String ns = Context.NOTIFICATION_SERVICE;
//        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
//        try {
//            nMgr.cancel(notifyId);
//            nMgr.cancelAll();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        Log.d(TAG, "onNewIntent: ");
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //  requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_speed_dashboard);
//        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//
//        PointerSpeedometer pointerSpeedometer = (PointerSpeedometer) findViewById(R.id.iv_imageSpeedometer);
//        pointerSpeedometer.setMaxSpeed(maximumspeed);
//       // PointerSpeedometer pointerSpeedometer = (PointerSpeedometer) findViewById(R.id.iv_imageSpeedometer);
//
//        carfueleconomyperkm = SharedPreferenceHelper.getInstance().getStringValue(Constants.carfueleconomyperkm , "0.7");
//
//    //    todo i commented this code for latter check
////        userid = SharedPreferenceHelper.getInstance().getStringValue(Constants.userid , "");
////        usercompleteprofile = FirebaseDatabase.getInstance().getReference("userscompleteprofile").child(userid);
////
////        usercompleteprofile.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                if (dataSnapshot.exists())
////                {
////                    UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
////
////                    distanceUnitsfromdatabase = userInformation.getDistance();
////                   // Log.d("distanceunitsfromfireba" , distanceUnitsfromdatabase);
////                }
////                else
////                {
////                    distanceUnits = "KM";
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////                    distanceUnits = "KM";
////            }
////        });
//        Log.d("speedunits: " , SharedPreferenceHelper.getInstance().getStringValue(Constants.speedunits , ""));
//        Log.d("distanceunits: " , SharedPreferenceHelper.getInstance().getStringValue(Constants.distanceunits , ""));
//
//        //binding.ivImageSpeedometer.setMaxSpeed(maximumspeed);
//        speedLimit = SharedPreferenceHelper.getInstance().getIntegerValue(Constants.SPEED_LIMIT, 0);
//
//        if (speedLimit == 0)
//        {
//            binding.textAlarm.setVisibility(View.GONE);
//            binding.textSpeedlimit.setVisibility(View.GONE);
//        }
//        else
//        {
//            binding.textAlarm.setVisibility(View.VISIBLE);
//            binding.textSpeedlimit.setVisibility(View.VISIBLE);
//        }
//
//        if (SharedPreferenceHelper.getInstance().getStringValue(Constants.speedunits, "KM/hr").equals("KM/hr")) {
//            binding.textAlarm.setText(/*"Speed Limit " + */String.valueOf(speedLimit) + "KMPH");
//        } else if (SharedPreferenceHelper.getInstance().getStringValue(Constants.speedunits, "KM/hr").equals("M/hr")) {
//            binding.textAlarm.setText(/*"Speed Limit " + */String.valueOf(speedLimit) + "MPH");
//        }
//        //  Toast.makeText(this, "Limit alarm value: "+FuturisticSettingsActivity.limitalarmpressed, Toast.LENGTH_SHORT).show();
//        Snackbar snackbar = Snackbar
//                .make(binding.maincontainer, "The data will appear as soon as we detect your movement", Snackbar.LENGTH_LONG);
//        snackbar.show();
//
//        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//        Date date1 = new Date();
//        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//        try {
//            date1 = (Date)formatter.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long output=date1.getTime()/1000L;
//        String str= Long.toString(output);
//        dateinmillis = Long.parseLong(str) * 1000;
//
//        Log.d("datedateinmillis" , ""+dateinmillis);
//
//        if (Build.VERSION.SDK_INT >= 21) {
//
//
//            Drawable drawable1 = getResources().getDrawable(R.drawable.minimize);
//            DrawableCompat.setTint(drawable1, ContextCompat.getColor(this, R.color.whiteColor));
//            binding.minimizeIcon.setImageDrawable(drawable1);
//        } else {
//            Drawable drawable1 = ContextCompat.getDrawable(SpeedDashboardActivity2.this, R.drawable.minimize);
//            Drawable wrappedDrawable1 = null;
//            try {
//                wrappedDrawable1 = DrawableCompat.wrap(drawable1);
//            } catch (NullPointerException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            DrawableCompat.setTint(wrappedDrawable1, ContextCompat.getColor(SpeedDashboardActivity2.this, R.color.whiteColor));
//            binding.minimizeIcon.setImageDrawable(wrappedDrawable1);
//        }
//        // binding.chronometer.setBase(System.currentTimeMillis());
//
//
//        String userid;
//        userid = SharedPreferenceHelper.getInstance().getStringValue(Constants.userid, "");
//       // carname = SharedPreferenceHelper.getInstance().getStringValue(AppConstant.carnameselectedfortrip, "");
//        // todo get car
//        carname = "WagonR";//DriveFragment.carchoosenbyuser;
//       // Toast.makeText(this, ""+carname, Toast.LENGTH_SHORT).show();
//        //usertripsdatabase = FirebaseDatabase.getInstance().getReference("usertrips").child(userid).child(carname);
//
//        speedDashboardModel = new SpeedDashboardModel();
//
//        try {
//            mp = new MediaPlayer();
//            mp = MediaPlayer.create(getApplicationContext(), notification1);
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (ClassCastException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        data = new DataManager();
//
//        binding.stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finishDriveDialog();
//            }
//        });
//
//        if (SharedPreferenceHelper.getInstance().getStringValue(Constants.speedunits, "KM/hr").equals("KM/hr")) {
//            speedDashboardModel.setSpeedView("0");
//            speedDashboardModel.setUnitView("KMPH");
//            speedDashboardModel.setDistanceView("0 M");
//            speedDashboardModel.setMaxSpeedView("0 KMPH");
//            speedDashboardModel.setAvgSpeedView("0 KMPH");
//        } else {
//            speedDashboardModel.setSpeedView("0");
//            speedDashboardModel.setUnitView("MPH");
//            speedDashboardModel.setDistanceView("0 M");
//            speedDashboardModel.setMaxSpeedView("0 MPH");
//            speedDashboardModel.setAvgSpeedView("0 MPH");
//        }
//
//        if (SharedPreferenceHelper.getInstance().getStringValue(Constants.speedunits, "KM/hr").equals("KM/hr"))
//        {
//            binding.speedUnit.setText("KMPH");
//        }
//        else
//        {
//            binding.speedUnit.setText("MPH");
//        }
//
//        binding.chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
//            boolean isPair = true;
//
//            @Override
//            public void onChronometerTick(Chronometer chrono) {
//                long time;
//                //  Log.d("basetimebefore", "chrono.getBase(): " + chrono.getBase());
//                //  Log.d("baserealtimebefore", "SystemClock.elapsedRealtime(): " + SystemClock.elapsedRealtime());
//                // chrono.setBase(System.currentTimeMillis());
//                time = SystemClock.elapsedRealtime() - chrono.getBase();
//                Log.d("basetime", "" + chrono.getBase() + "\t" + "realtime" + "" + SystemClock.elapsedRealtime() + "\t" + "finaltime" + time);
//                locationViewModel.setChronoTimerMutableLiveData(time);
//            }
//        });
//
//        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
//        locationViewModel.getLocationMutableLiveData().observe(this, new Observer<DataManager>() {
//            @Override
//            public void onChanged(@Nullable DataManager dataManager) {
//                //  dataManagerLocal = dataManager;
//
//                /*if (mp!=null && mp.isPlaying())
//                {
//                    mp.stop();
//                }*/
//
//                if (isstart) {
//                    binding.chronometer.start();
//                    isstart = false;
//                }
//
//
//                long time = 0;
//                double distancecovered = 0;
//                try {
//                    time = dataManager.getTime();
//                    distancecovered = dataManager.getDistance();
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                double average;
//
//                if (time <= 0) {
//                    average = 0.0;
//                } else {
//
//                    average = (distancecovered / (time / 1000)) * 3.6;
//                }
//
//
//                double maxSpeedTemp = 0;
//                try {
//                    maxSpeedTemp = dataManager.getMaxSpeed();
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                double distanceTemp = 0;
//                try {
//                    distanceTemp = dataManager.getDistance();
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                double averageTemp = average;
//
//                // Log.d("saad", "before update: " + averageTemp);
//
//                if (SharedPreferenceHelper.getInstance().getStringValue(Constants.speedunits, "KM/hr").equals("KM/hr")) {
//                    speedUnits = "KMPH";
//                    /*if (distanceTemp <= 1000.0) {
//                        distanceUnits = "m";
//                    } else {
//                        distanceTemp /= 1000.0;
//                        distanceUnits = "km";
//                    }*/
//                    try {
//                        if (distanceUnitsfromdatabase.equals("KM")) {
//                            distanceTemp = distanceTemp * 0.001;
//                            distanceUnits = "KM";
//                        } else if (distanceUnitsfromdatabase.equals("Miles")) {
//                            distanceTemp = distanceTemp * 0.000621371;
//                            distanceUnits = "M";
//                        }
//                        else
//                        {
//                            distanceTemp = distanceTemp * 0.001;
//                            distanceUnits = "KM";
//                        }
//                    }
//                    catch (NullPointerException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//
//
//
//                } else if (SharedPreferenceHelper.getInstance().getStringValue(Constants.speedunits, "KM/hr").equals("M/hr")) {
//                    try {
//                        maxSpeedTemp *= 0.62137119;
//                        if (distanceUnitsfromdatabase.equals("KM")) {
//                            distanceTemp = distanceTemp * 0.001;
//                            distanceUnits = "KM";
//                        } else if (distanceUnitsfromdatabase.equals("Miles")) {
//                            distanceTemp = distanceTemp * 0.000621371;
//                            distanceUnits = "M";
//                        }
//                        else
//                        {
//                            distanceTemp = distanceTemp * 0.001;
//                            distanceUnits = "KM";
//                        }
//
//                        // distanceTemp = distanceTemp / 1000.0 * 0.62137119;
//                        averageTemp *= 0.62137119;
//                        speedUnits = "MPH";
//                    }
//                    catch (NullPointerException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//
//                   // distanceUnits = "mi";
//                } /*else {
//                    maxSpeedTemp *= 0.5399568;
//                    distanceTemp = distanceTemp / 1000.0 * 0.5399568;
//                    averageTemp *= 0.5399568;
//                    speedUnits = "knot";
//                    distanceUnits = "kn";
//                }*/
//
//
//                speedDashboardModel.setUnitView(speedUnits);
//
//                Log.d("maxspeed", maxSpeedTemp + "\t" + "speedavg" + averageTemp + "\t" + "distance" + distanceTemp);
//
////                maxSpeed = new SpannableString(String.format("%.0f", maxSpeedTemp) + speedUnits);
////              //  maxSpeed.setSpan(new RelativeSizeSpan(0.5f), maxSpeed.length() - 4, maxSpeed.length(), 0);
////                speedDashboardModel.setMaxSpeedView(maxSpeed.toString());
////               // binding.maxSpeedView.setText(maxSpeed);
////                //  SharedPreferenceHelper.getInstance().setStringValue(AppConstant.maxspeed , maxSpeed.toString());
////
////                avgSpeed = new SpannableString(String.format("%.0f", averageTemp) + speedUnits);
////                //avgSpeed.setSpan(new RelativeSizeSpan(0.5f), avgSpeed.length() - 4, avgSpeed.length(), 0);
////                speedDashboardModel.setAvgSpeedView(avgSpeed.toString());
////               // binding.avgSpeedView.setText(avgSpeed);
////
////                distance = new SpannableString(String.format("%.3f", distanceTemp) + distanceUnits);
////               // distance.setSpan(new RelativeSizeSpan(0.5f), distance.length() - 2, distance.length(), 0);
////                speedDashboardModel.setDistanceView(distance.toString());
//
//                maxSpeed = String.format("%.0f", maxSpeedTemp) + " " + speedUnits;
//                speedDashboardModel.setMaxSpeedView(maxSpeed);
//
//                // avgSpeed =  String.format("%.0f", averageTemp)+ speedUnits;
//                try {
//                    avgSpeed = dataManager.getTotalAvgSpeed() + " " + speedUnits;
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                    avgSpeed = "0";
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                speedDashboardModel.setAvgSpeedView(avgSpeed);
//
//                distance = String.format("%.2f", distanceTemp) + " " + distanceUnits;
//                speedDashboardModel.setDistanceView(distance);
//
//
//                binding.setViewModel(speedDashboardModel);
//                // binding.distanceView.setText(distance);
//
//                // Log.d("maxspeed" , maxSpeed +"\t"+ "speedavg" + avgSpeed+ "\t"+ "distance" + distance);
//
//
//                try {
//                    currentSpeed = dataManager.getCurrentSpeed();
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                if (currentSpeed != null) {
//                    binding.currentSpeed.setText(currentSpeed);
//                    //binding.ivImageSpeedometer.speedTo(Integer.parseInt(currentSpeed));
//                    //binding.ivImageSpeedometer.speedTo(Integer.parseInt(currentSpeed));
//                    pointerSpeedometer.speedTo(Integer.parseInt(currentSpeed));
//                } else {
//                    binding.currentSpeed.setText("0");
//                   // binding.ivImageSpeedometer.speedTo(0);
//                   // binding.ivImageSpeedometer.speedTo(0);
//                    pointerSpeedometer.speedTo(0);
//                }
//
//                if (currentSpeed != null && Integer.parseInt(currentSpeed) < speedLimit) {
//                    binding.currentSpeed.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.speedUnit.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.textTime.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.battery.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.textMaxspeed.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.maxSpeedView.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.avgSpeedView.setTextColor(getResources().getColor(R.color.whiteColor));
//                    //  binding.textView10.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.textDistance.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.distanceView.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.textAlarm.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.hud.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.textAveragespeed.setTextColor(getResources().getColor(R.color.whiteColor));
//                    //  binding.stop.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.textDuration.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.chronometer.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.textCurrentspeed.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.textSpeedlimit.setTextColor(getResources().getColor(R.color.whiteColor));
//                    binding.stop.setTextColor(getResources().getColor(R.color.blackColor));
//                    binding.fuelup.setTextColor(getResources().getColor(R.color.blackColor));
//                    if (Build.VERSION.SDK_INT >= 21) {
//                        Drawable drawable = getResources().getDrawable(R.drawable.minimize);
//                        //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                        DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity2.this, R.color.whiteColor));
//                        binding.minimizeIcon.setImageDrawable(drawable);
//                    } else {
//
//                        Drawable drawable = ContextCompat.getDrawable(SpeedDashboardActivity2.this, R.drawable.minimize);
//                        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
//                        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(SpeedDashboardActivity2.this, R.color.whiteColor));
//                        binding.minimizeIcon.setImageDrawable(drawable);
//                    }
//                }
//                /*if (mp!=null && mp.isPlaying())
//                {
//                    if (currentSpeed!=null && Integer.parseInt(currentSpeed) < speedLimit)
//                    {
//                        mp.stop();
//                    }
//                }
//
//                if (Integer.parseInt(currentSpeed) < speedLimit && mp.isPlaying() && mp!=null)
//                {
//                    mp.stop();
//                }*/
//
//
//            }
//        });
//
//
//        locationViewModel.getSpeedexceedMutableLiveData().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                if (s != null) {
//                   // if (SetSpeedLimit.speedalarmon) {
//                        if (currentSpeed != null && Integer.parseInt(currentSpeed) > speedLimit) {
//                            if (mp != null && !mp.isPlaying()) {
//                                //  mp.setLooping(true);
//                                mp.start();
//                            }
//
//                            binding.currentSpeed.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.speedUnit.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.textTime.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.battery.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.textMaxspeed.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.maxSpeedView.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.avgSpeedView.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            //   binding.textView10.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.textDistance.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.distanceView.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.textAlarm.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.hud.setTextColor(getResources().getColor(R.color.imagecolor1));
//
//                            binding.textAveragespeed.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.textDuration.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.chronometer.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.textCurrentspeed.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.textSpeedlimit.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.stop.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            binding.fuelup.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            //  binding.stop.setTextColor(getResources().getColor(R.color.imagecolor1));
//                            if (Build.VERSION.SDK_INT >= 21) {
//                                Drawable drawable = getResources().getDrawable(R.drawable.minimize);
//                                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity2.this, R.color.imagecolor1));
//                                binding.minimizeIcon.setImageDrawable(drawable);
//                            } else {
//
//                                Drawable drawable = ContextCompat.getDrawable(SpeedDashboardActivity2.this, R.drawable.minimize);
//                                Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
//                                DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(SpeedDashboardActivity2.this, R.color.imagecolor1));
//                                binding.minimizeIcon.setImageDrawable(drawable);
//                            }
//
//
//                        } else {
//                            if (mp != null && mp.isPlaying()) {
//                                mp.stop();
//                            }
//                            binding.currentSpeed.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.speedUnit.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.textTime.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.battery.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.textMaxspeed.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.maxSpeedView.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.avgSpeedView.setTextColor(getResources().getColor(R.color.whiteColor));
//                            //  binding.textView10.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.textDistance.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.distanceView.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.textAlarm.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.hud.setTextColor(getResources().getColor(R.color.whiteColor));
//                            //  binding.stop.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.textAveragespeed.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.textDuration.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.chronometer.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.textCurrentspeed.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.textSpeedlimit.setTextColor(getResources().getColor(R.color.whiteColor));
//                            binding.stop.setTextColor(getResources().getColor(R.color.blackColor));
//                            binding.fuelup.setTextColor(getResources().getColor(R.color.blackColor));
//
//                            if (Build.VERSION.SDK_INT >= 21) {
//                                Drawable drawable = getResources().getDrawable(R.drawable.minimize);
//                                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity2.this, R.color.whiteColor));
//                                binding.minimizeIcon.setImageDrawable(drawable);
//                            } else {
//
//                                Drawable drawable = ContextCompat.getDrawable(SpeedDashboardActivity2.this, R.drawable.minimize);
//                                Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
//                                DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(SpeedDashboardActivity2.this, R.color.whiteColor));
//                                binding.minimizeIcon.setImageDrawable(drawable);
//                            }
//                        }
//                   // }
//                }
//
//            }
//        });
//        //  Toast.makeText(this, "Activity main created", Toast.LENGTH_SHORT).show();
//
//
//        // if (SettingsActivity.ischecked)
//        {
//
//
//        }
//        if (SharedPreferenceHelper.getInstance().getStringValue(Constants.METER_UNIT, "km/h").equals("KM/hr")) {
//            binding.speedUnit.setText("km/h");
//        } else if (SharedPreferenceHelper.getInstance().getStringValue(Constants.METER_UNIT, "km/h").equals("M/hr")) {
//            binding.speedUnit.setText("mph");
//        }
//
//
//        //  modelspeed = ViewModelProviders.of(this).get(SharedViewModel.class);
//
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//        //  GoogleAnalyticsLogs.getInstance().logEvent(this, 3, SpeedDashboardActivity2.this.getClass().getSimpleName());
//
//
//        //  binding.raySpeedometer.setMaxSpeed(maximumspeed);
//
//
//        // counter ++;
//        // Toast.makeText(this, ""+counter, Toast.LENGTH_SHORT).show();
//
//        // if (counter == 1)
//        //  {
//        // Toast.makeText(this, "In 1", Toast.LENGTH_SHORT).show();
////            speedDashboardModel.setSpeedView("0");
////            speedDashboardModel.setUnitView("km/h");
////            speedDashboardModel.setDistanceView("0 M");
////            speedDashboardModel.setMaxSpeedView("0 km/h");
////            speedDashboardModel.setAvgSpeedView("0 km/h");
//        // speedDashboardModel.setTitle(SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_NAME, "Digital"));
//
//        //  }
//
//
//        // hudAnimationButton();
//        // setVisibility();
//        // setTheme();
//        // futuristicvisibility();
//        setcurrentTime();
//
//        binding.setViewModel(speedDashboardModel);
////        binding.setActivity(this);
//        // historySaveDialog = new HistorySaveDialog(this);
//
//        //setValue();
//
//
//        data.setFirstTime(true);
//
//        //  binding.stop.setOnClickListener(this);
//        binding.hud.setOnClickListener(this);
//        binding.minimizeIcon.setOnClickListener(this);
//        binding.textAlarm.setOnClickListener(this);
//        binding.fuelup.setOnClickListener(this);
//
//
//        runningCountDownTimer();
//        animate();
//    }
//
//    public void hudAnimationButton() {
//
//        scaleAnimation = new ScaleAnimation(1.0f, 1.15f, 1.0f, 1.15f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        scaleAnimation.setDuration(500);
//        scaleAnimation.setRepeatCount(Animation.INFINITE);
//        scaleAnimation.setRepeatMode(Animation.REVERSE);
//        binding.hud.startAnimation(scaleAnimation);
//
//
//    }
//
//
//    public void onClick(View view) {
//        switch (view.getId()) {
////            case R.id.save_track_btn:
////                {
////                HistoryManager.getInstance(this).saveHistoryData(maxSpeed, avgSpeed, distance);
//////                historySaveDialog.showDialog();
////                startActivity(new Intent(this, HistoryActivity.class));
////                finish();
////
////                break;
////            }
////            case R.id.kmph_btn:
////                SharedPreferenceHelper.getInstance().setStringValue(AppConstant.METER_UNIT, "km/h");
////                SpeedMeterManager.getInstance().setKmphBtnValue(binding.kmphBtn, binding.mphBtn/*, binding.knotBtn*/);
////                break;
////            case R.id.mph_btn:
////                SharedPreferenceHelper.getInstance().setStringValue(AppConstant.METER_UNIT, "mph");
////                SpeedMeterManager.getInstance().setMphBtnValue(binding.kmphBtn, binding.mphBtn/*, binding.knotBtn*/);
////                break;
//
//            case R.id.fuelup:
//                // todo add fuel dialog here
////                if (SharedPreferenceHelper.getInstance().getStringValue(Constants.anycaradded , "").equals("yes"))
////                {
////                    FuelUpDialog fuelUpDialog = new FuelUpDialog(SpeedDashboardActivity.this);
////                    fuelUpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
////                    fuelUpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////                    fuelUpDialog.getWindow().setGravity(Gravity.BOTTOM);
////                    fuelUpDialog.show();
////                }
////                else
////                {
////                    Toast.makeText(SpeedDashboardActivity.this, "Please add your car first to fuel up", Toast.LENGTH_SHORT).show();
////
////                    ApiCarAddDialog apiCarAddDialog1 = new ApiCarAddDialog(SpeedDashboardActivity.this);
////                    apiCarAddDialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
////                    apiCarAddDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
////                    apiCarAddDialog1.getWindow().setGravity(Gravity.BOTTOM);
////                    apiCarAddDialog1.show();
////                }
//
//                break;
//
//            case R.id.minimizeIcon:
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
//
//                    //If the draw over permission is not available open the settings screen
//                    //to grant the permission.
//
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                            Uri.parse("package:" + getPackageName()));
//                    startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
//                } else {
//
//                    // FirebaseJobManager.getInstance().setReminderJob();
//                    initializeView();
//                }
//
//                break;
//
//         /*   case R.id.knot_btn:
//                SharedPreferenceHelper.getInstance().setStringValue(AppConstant.METER_UNIT, "knot");
//                SpeedMeterManager.getInstance().setKnotBtnValue(binding.kmphBtn, binding.mphBtn*//*, binding.knotBtn*//*);
//                break;*/
//            case R.id.hud:
//                // if (!SharedPreferenceHelper.getInstance().getStringValue(AppConstant.VEHICLE_TYPE, "cycle").equals("cycle"))
//                // {
//                if (!isClickHudBtn) {
//                    isClickHudBtn = true;
//                    //  binding.hudBtn.setImageResource(R.drawable.normal_view);
//                    setHudView();
//                } else {
//                    isClickHudBtn = false;
//                    //  binding.hudBtn.setImageResource(R.drawable.hud_new);
//                    setNormalView();
//
//
//                }
//                // }
//
//                break;
//
//            /*case R.id.stop:
//                historySaveDialog.createDialog();
//                historySaveDialog.showDialog();
//
//                *//*SharedPreferenceHelper.getInstance().setStringValue(AppConstant.avgspeedfuturistic , "NotClicked");
//                SharedPreferenceHelper.getInstance().setStringValue(AppConstant.maxspeedfuturistic , "NotClicked");
//                SharedPreferenceHelper.getInstance().setStringValue(AppConstant.distaancefuturistic , "NotClicked");
//                SharedPreferenceHelper.getInstance().setStringValue(AppConstant.speedlimitfuturistic , "NotClicked");
//                SharedPreferenceHelper.getInstance().setStringValue(AppConstant.limitalarmfuturistic , "NotClicked");
//                SharedPreferenceHelper.getInstance().setStringValue(AppConstant.timefuturistic , "NotClicked");
//                SharedPreferenceHelper.getInstance().setStringValue(AppConstant.unitfuturistic , "NotClicked");
//                SharedPreferenceHelper.getInstance().setStringValue(AppConstant.minimizefuturistic , "NotClicked");
//                SharedPreferenceHelper.getInstance().setStringValue(AppConstant.batteryfuturistic , "NotClicked");*//*
//
//                FuturisticSettingsActivity.avgclicked = false;
//                FuturisticSettingsActivity.maxclicked = false;
//                FuturisticSettingsActivity.distancepressed = false;
//                FuturisticSettingsActivity.speedlimitpressed = false;
//                FuturisticSettingsActivity.limitalarmpressed = false;
//                FuturisticSettingsActivity.timepressed = false;
//                FuturisticSettingsActivity.unitpressed = false;
//                FuturisticSettingsActivity.minimizepressed = false;
//                FuturisticSettingsActivity.batterypressed = false;
//                break;*/
//
//            case R.id.text_alarm:
//                // todo check it please
////                NewSpeedLimitDialog newSpeedLimitDialog = new NewSpeedLimitDialog(SpeedDashboardActivity.this);
////                newSpeedLimitDialog.show();
//
//                break;
//        }
//    }
//
//
//    private void setNormalView() {
//        binding.textAlarm.setScaleX(1);
//        binding.textTime.setScaleX(1);
//        binding.battery.setScaleX(1);
//        binding.maxSpeedView.setScaleX(1);
//        binding.textMaxspeed.setScaleX(1);
//        binding.avgSpeedView.setScaleX(1);
//        binding.distanceView.setScaleX(1);
//        binding.currentSpeed.setScaleX(1);
//        binding.speedUnit.setScaleX(1);
//        binding.hud.setScaleX(1);
//        binding.minimizeIcon.setScaleX(1);
//        binding.ivImageSpeedometer.setScaleX(1);
//        binding.textDistance.setScaleX(1);
//        binding.stop.setScaleX(1);
//
//        binding.textAveragespeed.setScaleX(1);
//        binding.textDuration.setScaleX(1);
//        binding.chronometer.setScaleX(1);
//        binding.textCurrentspeed.setScaleX(1);
//        binding.textSpeedlimit.setScaleX(1);
//        binding.fuelup.setScaleX(1);
//    }
//
//    private void setHudView() {
//        binding.textAlarm.setScaleX(-1);
//        binding.textTime.setScaleX(-1);
//        binding.battery.setScaleX(-1);
//        binding.textMaxspeed.setScaleX(-1);
//        binding.maxSpeedView.setScaleX(-1);
//        binding.avgSpeedView.setScaleX(-1);
//        binding.textDistance.setScaleX(-1);
//        binding.distanceView.setScaleX(-1);
//        binding.currentSpeed.setScaleX(-1);
//        binding.speedUnit.setScaleX(-1);
//        binding.minimizeIcon.setScaleX(-1);
//        binding.ivImageSpeedometer.setScaleX(-1);
//        binding.hud.setScaleX(-1);
//        binding.stop.setScaleX(-1);
//
//        binding.textAveragespeed.setScaleX(-1);
//        binding.textDuration.setScaleX(-1);
//        binding.chronometer.setScaleX(-1);
//        binding.textCurrentspeed.setScaleX(-1);
//        binding.textSpeedlimit.setScaleX(-1);
//        binding.fuelup.setScaleX(-1);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//
//        //   statusCheck();
//
//        int locationmode = 0;
//        try {
//            locationmode = getLocationMode(SpeedDashboardActivity2.this);
//
//        } catch (Settings.SettingNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        try {
//            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//
//                showGpsDisabledDialog();
//            } else if (locationmode != 3) {
//                // int locationmode = getLocationMode(SpeedDashboardSimpleActivity.this);
////                if (locationmode!=3)
////                {
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(SpeedDashboardActivity2.this);
//                builder1.setMessage("Please enable Location Mode with High Accuracy to proceed");
//                builder1.setCancelable(false);
//
//                builder1.setPositiveButton(
//                        "Yes",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                                dialog.cancel();
//                            }
//                        });
//
//                builder1.setNegativeButton(
//                        "No",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                                Intent i = new Intent(SpeedDashboardActivity2.this, MainActivity.class);
//                                startActivity(i);
//                                finish();
//                            }
//                        });
//
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
//                //  Toast.makeText(getApplicationContext(), "Please enable Location Mode with High Accuracy to proceed", Toast.LENGTH_SHORT).show();
//                //  startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                // }
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        if (data == null) {
//            data = new DataManager();
//        }
//
//        batteryLevel();
//    }
//
//    @Override
//    protected void onPause() {
//
//        //  Toast.makeText(getApplicationContext(), "on Pause called", Toast.LENGTH_SHORT).show();
//        //unregisterReceiver(mBatteryLevelReciver);
//        // Toast.makeText(this, "unregistered receiver", Toast.LENGTH_SHORT).show();
//        /*if (mBatteryLevelReciver == null)
//        {
//            Toast.makeText(this, "Not unregistering receiver as it is already null", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Toast.makeText(this, "Unregistering Receiver", Toast.LENGTH_SHORT).show();
//            unregisterReceiver(mBatteryLevelReciver);
//            mBatteryLevelReciver = null;
//        }*/
//        super.onPause();
//        unregisterReceiver(mBatteryLevelReciver);
//
//    }
//
//    @Override
//    public void onDestroy() {
//        //   Toast.makeText(getApplicationContext(), "Speed Activity onDestroy called", Toast.LENGTH_SHORT).show();
//
////        if (isminimizepressed == true) {
////            Toast.makeText(getApplicationContext(), "Not stopping the Gps Service", Toast.LENGTH_SHORT).show();
////        } else if (isminimizepressed == false) {
////
////            // stopService(new Intent(getBaseContext(), GpsServices.class));
////
////        }
//
//        super.onDestroy();
//    }
//
//    public void showGpsDisabledDialog() {
//
//
//        if (googleApiClient == null) {
//            googleApiClient = new GoogleApiClient.Builder(this)
//                    .addApi(LocationServices.API).addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this).build();
//            googleApiClient.connect();
//            LocationRequest locationRequest = LocationRequest.create();
//            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            locationRequest.setInterval(30 * 1000);
//            locationRequest.setFastestInterval(5 * 1000);
//            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                    .addLocationRequest(locationRequest);
//
//            // **************************
//            builder.setAlwaysShow(true); // this is the key ingredient
//            // **************************
//
//            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
//                    .checkLocationSettings(googleApiClient, builder.build());
//            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//                @Override
//                public void onResult(LocationSettingsResult result) {
//                    final Status status = result.getStatus();
//                    final LocationSettingsStates state = result
//                            .getLocationSettingsStates();
//                    switch (status.getStatusCode()) {
//                        case LocationSettingsStatusCodes.SUCCESS:
//                            // All location settings are satisfied. The client can
//                            // initialize location
//                            // requests here.
//                            break;
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                            // Location settings are not satisfied. But could be
//                            // fixed by showing the user
//                            // a dialog.
//                            try {
//                                // Show the dialog by calling
//                                // startResolutionForResult(),
//                                // and check the result in onActivityResult().
//                                status.startResolutionForResult(SpeedDashboardActivity2.this, 1000);
//                            } catch (IntentSender.SendIntentException e) {
//                                // Ignore the error.
//                            }
//                            break;
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                            // Location settings are not satisfied. However, we have
//                            // no way to fix the
//                            // settings so we won't show the dialog.
//                            break;
//                    }
//                }
//            });
//        }
//
//
//
//
//        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(getResources().getString(R.string.please_enable_gps));
//        builder.setPositiveButton("accept", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
//            }
//        });
//        builder.show();*/
//       /* Dialog dialog = new Dialog(this, getResources().getString(R.string.gps_disabled), getResources().getString(R.string.please_enable_gps));
//
//        dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        dialog.show();*/
//    }
//
//    @Override
//    public void onGpsStatusChanged(int event) {
//
//        switch (event) {
//            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
//                break;
//
//            case GpsStatus.GPS_EVENT_STOPPED:
//                if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                    showGpsDisabledDialog();
//                }
//                break;
//            case GpsStatus.GPS_EVENT_FIRST_FIX:
//                break;
//        }
//
//    }
//
////    @Override
////    public void onLocationChanged(Location location) {
////        // Toast.makeText(getApplicationContext(), "Locaton is changing",Toast.LENGTH_SHORT).show();
////        binding.chronometer.start();
////        currentLat = location.getLatitude();
////        currentLon = location.getLongitude();
////
////        if (data.isFirstTime()) {
////            lastLat = currentLat;
////            lastLon = currentLon;
////            data.setFirstTime(false);
////        }
////
////        lastlocation.setLatitude(lastLat);
////        lastlocation.setLongitude(lastLon);
////        double distance = location.distanceTo(lastlocation);
////
////
////        //  Log.d("talal", "activity distance: " + distance);
////        if (location.getAccuracy() < distance) {
////            //Log.d("talal", "activity distance: " + distance);
////            data.addDistance(distance);
////
////            lastLat = currentLat;
////            lastLon = currentLon;
////        }
////
////
////        if (location.hasSpeed()) {
////            //  Toast.makeText(getApplicationContext(),"location has speed",Toast.LENGTH_SHORT).show();
////            if (!isTimeSet) {
////                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US); //dd-MM-yyyy
////                Calendar calender = Calendar.getInstance();
////                TimeZone ccme = calender.getTimeZone();
////                timeFormat.setTimeZone(ccme);
////                startTime = timeFormat.format(new Date());
////                isTimeSet = true;
////            }
////            data.setCurSpeed(location.getSpeed() * 3.6);
////
////            if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h").equals("km/h")) {
////                currentSpeed = String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6) /*+ "km/h"*/;
////            } else if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h").equals("mph")) {
////                currentSpeed = String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6 * 0.62137119)/* + "mi/h"*/;
////            } else {
////                currentSpeed = String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6 * 0.5399568) /*+ "kn"*/;
////            }
////
////
////            //  if (SharedPreferenceHelper.getInstance().getBooleanValue(AppConstant.SPEED_ALERT_ON_OFF, false)) {
////            if (SetSpeedLimitFuturistic.alarmon == true) {
////                speedLimit = SharedPreferenceHelper.getInstance().getIntegerValue(AppConstant.SPEED_LIMIT, 0);
////                if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.SPEED_LIMIT_METER_TYPE, "km/h").equals("km/h")) {
////
////                    if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h").equals("km/h")) {
////
////                    } else if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h").equals("mph")) {
////                        speedLimit *= 0.62137119;
////                    } else {
////                        speedLimit *= 0.5399568;
////                    }
////                } else if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h").equals("mph")) {
////                    if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h").equals("km/h")) {
////                        speedLimit /= 0.62137119;
////                    } else if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h").equals("mph")) {
////
////                    } else {
////                        speedLimit *= 0.868976;
////                    }
////
////                } else {
////                    if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h").equals("km/h")) {
////                        speedLimit /= 1.852;
////                    } else if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h").equals("mph")) {
////                        speedLimit /= 1.15078;
////                    } else {
////
////                    }
////                }
////
////
////                if (speedLimit > Integer.parseInt(currentSpeed) && speedLimit != 0) {
////
////
////                    createNotification(String.valueOf(speedLimit));
////                  //  Toast.makeText(getApplicationContext(), "Max speed reached", Toast.LENGTH_SHORT).show();
////                }
////
////                if (Integer.parseInt(currentSpeed) < speedLimit) {
////                    if (mp.isPlaying())
////                        mp.stop();
////                }
////
////                if (Integer.parseInt(currentSpeed) > speedLimit && speedLimit != 0 && isFinished) {
////                    // Toast.makeText(getApplicationContext(),"Speed Limit Exceeded",Toast.LENGTH_SHORT).show();
////                    if (!mp.isPlaying() && !isPlaing) {
////                        mp.setLooping(true);
////                        mp.start();
////                        isPlaing = true;
////                    }
////
////                    if (!speedlimitdialogclicked) {
////                        createNotification(String.valueOf(speedLimit));
////
////
////                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SpeedDashboardActivity.this);
////                        builder1.setMessage("Your Safety is Important\nYour are currently over your speed limit");
////                        builder1.setCancelable(true);
////
////                        builder1.setPositiveButton(
////                                "Ignore",
////                                new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int id) {
////                                        dialog.cancel();
////                                        countDownTimer.start();
////                                        notificationalarm = true;
////                                        if (notificationalarm) {
////                                            createNotification(String.valueOf(speedLimit));
////                                        }
////
////                                        Snackbar snackbar = Snackbar
////                                                .make(binding.mainContainer, "We will not disturb you for next 15 minutes", Snackbar.LENGTH_LONG);
////                                        snackbar.show();
////
////                                    }
////                                });
////
////                        builder1.setNegativeButton(
////                                "Dismiss",
////                                new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int id) {
////                                        cancelNotification(SpeedDashboardActivity.this, Notificationid);
////                                        dialog.dismiss();
////                                        notificationalarm = false;
////                                        countDownTimer.start();
////
////                                        if (mp.isPlaying()) {
////                                            mp.stop();
////
////                                        }
////
////                                        Snackbar snackbar = Snackbar
////                                                .make(binding.mainContainer, "We will not disturb you for next 15 minutes", Snackbar.LENGTH_LONG);
////                                        snackbar.show();
////                                    }
////                                });
////
////                        if (notificationalarm) {
////                            createNotification(String.valueOf(speedLimit));
////                        }
////
////                        AlertDialog alert11 = builder1.create();
////
////                        if (alert11.isShowing()) {
////                            alert11.dismiss();
////                        } else {
////                            alert11.show();
////                            speedlimitdialogclicked = true;
////
////                        }
////                    } else {
////
////
////                    }
////
////
////                }
////
////
////            }
////            modelspeed.select(new SpeedDashboadFrafmentModel(currentSpeed, SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h")));
////            binding.currentSpeed.setText(currentSpeed);
////            binding.raySpeedometer.speedTo(Integer.parseInt(currentSpeed));
////            //  EventBus.getDefault().post(new SpeedDashboadFrafmentModel(currentSpeed, SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h"), location));
////            // SharedPreferenceHelper.getInstance().setStringValue(AppConstant.currentspeed , currentSpeed);
////            //  EventBus.getDefault().post(new ServiceSpeedModel(currentSpeed));
////            speedDashboardModel.setSpeedView(currentSpeed);
////            binding.setViewModel(speedDashboardModel);
////
////        }
////        UpdateDataListener listener = this;
////        listener.update();
////
////    }
//
//
//    @Override
//    public void onBackPressed() {
//      //  super.onBackPressed();
//        finishDriveDialog();
////        historySaveDialog.createDialog();
////        historySaveDialog.showDialog();
//        // finish();
//        /*SharedPreferenceHelper.getInstance().setStringValue(AppConstant.avgspeedfuturistic , "NotClicked");
//        SharedPreferenceHelper.getInstance().setStringValue(AppConstant.maxspeedfuturistic , "NotClicked");
//        SharedPreferenceHelper.getInstance().setStringValue(AppConstant.distaancefuturistic , "NotClicked");
//        SharedPreferenceHelper.getInstance().setStringValue(AppConstant.speedlimitfuturistic , "NotClicked");
//        SharedPreferenceHelper.getInstance().setStringValue(AppConstant.limitalarmfuturistic , "NotClicked");
//        SharedPreferenceHelper.getInstance().setStringValue(AppConstant.timefuturistic , "NotClicked");
//        SharedPreferenceHelper.getInstance().setStringValue(AppConstant.unitfuturistic , "NotClicked");
//        SharedPreferenceHelper.getInstance().setStringValue(AppConstant.minimizefuturistic , "NotClicked");
//        SharedPreferenceHelper.getInstance().setStringValue(AppConstant.batteryfuturistic , "NotClicked");
//*/
//
//       /* FuturisticSettingsActivity.avgclicked = false;
//        FuturisticSettingsActivity.maxclicked = false;
//        FuturisticSettingsActivity.distancepressed = false;
//        FuturisticSettingsActivity.speedlimitpressed = false;
//        FuturisticSettingsActivity.limitalarmpressed = false;
//        FuturisticSettingsActivity.timepressed = false;
//        FuturisticSettingsActivity.unitpressed = false;
//        FuturisticSettingsActivity.minimizepressed = false;
//        FuturisticSettingsActivity.batterypressed = false;*/
//    }
//
//
//   /* @Override
//    public void saveData() {
//        HistoryManager.getInstance(this).saveHistoryData(maxSpeed, avgSpeed, distance);
//        startActivity(new Intent(this, HistoryActivity.class));
//        finish();
//
//    }
//
//    @Override
//    public void dontSaveData() {
//
////        Intent intent = new Intent(this, FirstActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        startActivity(intent);
//        // ProcessPhoenix.triggerRebirth(this, intent);
//        Intent intent = new Intent(this, FirstActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//       // Runtime.getRuntime().exit(0);
//    }*/
//
//    private void createNotification(String speedLimit) {
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            NotificationChannel chan1 = new NotificationChannel(CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT);
//            chan1.setLightColor(Color.GREEN);
//            chan1.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//            assert manager != null;
//            manager.createNotificationChannel(chan1);
//
//            notification = new Notification.Builder(this, chan1.getId());
//
//        } else {
//            notification = new Notification.Builder(this);
//
//        }
//
//
//        notification.setContentTitle(getString(R.string.running))
//                .setContentText("Speed limit across the " + speedLimit)
//                .setVibrate(vibrate)
//                .setSound(uri)
//                .setSmallIcon(R.drawable.ic_directions_car);
//
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(Notificationid, notification.build());
//
//
//    }
//
////    @Override
////    public void update() {
////
////        double maxSpeedTemp = data.getMaxSpeed();
////        double distanceTemp = data.getDistance();
////        double averageTemp;
////       /* if (SharedPreferenceHelper.getInstance().getBooleanValue("auto_average", false)) {
////            averageTemp = data.getAverageSpeedMotion();
////        } else {*/
////        averageTemp = data.getAverageSpeed();
//////        }
////        // Log.d("saad", "before update: " + averageTemp);
////        String speedUnits;
////        String distanceUnits;
////        if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h").equals("km/h")) {
////            speedUnits = "km/h";
////            if (distanceTemp <= 1000.0) {
////                distanceUnits = "m";
////            } else {
////                distanceTemp /= 1000.0;
////                distanceUnits = "km";
////            }
////        } else if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h").equals("mph")) {
////            maxSpeedTemp *= 0.62137119;
////            distanceTemp = distanceTemp / 1000.0 * 0.62137119;
////            averageTemp *= 0.62137119;
////            speedUnits = "mi/h";
////            distanceUnits = "mi";
////        } else {
////            maxSpeedTemp *= 0.5399568;
////            distanceTemp = distanceTemp / 1000.0 * 0.5399568;
////            averageTemp *= 0.5399568;
////            speedUnits = "knot";
////            distanceUnits = "kn";
////        }
////
////
////        speedDashboardModel.setUnitView(speedUnits);
////
////        maxSpeed = new SpannableString(String.format("%.0f", maxSpeedTemp) + speedUnits);
////        maxSpeed.setSpan(new RelativeSizeSpan(0.5f), maxSpeed.length() - 4, maxSpeed.length(), 0);
////        speedDashboardModel.setMaxSpeedView(maxSpeed.toString());
////        //  SharedPreferenceHelper.getInstance().setStringValue(AppConstant.maxspeed , maxSpeed.toString());
////
////        avgSpeed = new SpannableString(String.format("%.0f", averageTemp) + speedUnits);
////        avgSpeed.setSpan(new RelativeSizeSpan(0.5f), avgSpeed.length() - 4, avgSpeed.length(), 0);
////        speedDashboardModel.setAvgSpeedView(avgSpeed.toString());
////        //   SharedPreferenceHelper.getInstance().setStringValue(AppConstant.avgspeed , avgSpeed.toString());
////
////        distance = new SpannableString(String.format("%.3f", distanceTemp) + distanceUnits);
////        distance.setSpan(new RelativeSizeSpan(0.5f), distance.length() - 2, distance.length(), 0);
////        speedDashboardModel.setDistanceView(distance.toString());
////        binding.setViewModel(speedDashboardModel);
////        //  SharedPreferenceHelper.getInstance().setStringValue(AppConstant.distance , distance.toString());
////
////        //  modelspeed.select(new SpeedDashboadFrafmentModel(currentSpeed, SharedPreferenceHelper.getInstance().getStringValue(AppConstant.METER_UNIT, "km/h")));
////        speedDashboadFrafmentModel.setAvgspeed(avgSpeed.toString());
////        speedDashboadFrafmentModel.setMaxspeed(maxSpeed.toString());
////        speedDashboadFrafmentModel.setDistance(distance.toString());
////        /*if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
////            mapFragment.upDateFragment(maxSpeed.toString(), distance.toString(), avgSpeed.toString());
////        }*/
////    }
//
//    /*@Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//        switch (position) {
//            case 0:
//                speedDashboardModel.setTitle("Digital");
//                break;
//            case 1:
//                speedDashboardModel.setTitle("Analog");
//                break;
//            case 2:
//                speedDashboardModel.setTitle("Map");
//                break;
//        }
//        binding.setViewModel(speedDashboardModel);
//
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }*/
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    public void setcurrentTime() {
////        Calendar calendar = Calendar.getInstance();
////        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
////        String strDate = "" + mdformat.format(calendar.getTime());
////        binding.textTime.setText(strDate);
//
//        Thread t = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    while (!isInterrupted()) {
//                        Thread.sleep(1000);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                long date = System.currentTimeMillis();
//                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
//                                String dateString = sdf.format(date);
//                                binding.textTime.setText(dateString);
//                            }
//                        });
//                    }
//                } catch (InterruptedException e) {
//                }
//            }
//        };
//        t.start();
//    }
//
//    public void batterypercent() {
//        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        Intent batteryStatus = registerReceiver(null, ifilter);
//
//        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//
//        float batteryPct = level / (float) scale;
//
//        int batLevel = (int) (batteryPct * 100);
//        binding.battery.setText(String.valueOf(batLevel));
//    }
//
//    private void batteryLevel() {
//        IntentFilter batteryLevelFliter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        registerReceiver(mBatteryLevelReciver, batteryLevelFliter);
//    }
//
//   /* public void setVisibility() {
//        if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.isCheck, "").equals("Clicked")) {
//            // Toast.makeText(getApplicationContext(),"In if",Toast.LENGTH_SHORT).show();
//            binding.textTime.setVisibility(View.VISIBLE);
//            binding.battery.setVisibility(View.VISIBLE);  //battery
//            binding.distanceView.setVisibility(View.VISIBLE);
//            binding.textAlarm.setVisibility(View.VISIBLE);
//            binding.maxtitile.setVisibility(View.VISIBLE);
//            binding.maxSpeedView.setVisibility(View.VISIBLE);
//            binding.textView10.setVisibility(View.VISIBLE); //avg
//            binding.avgSpeedView.setVisibility(View.VISIBLE);
//            binding.minimizeIcon.setVisibility(View.VISIBLE);
//
//        } else {
//
//            //  Toast.makeText(getApplicationContext(),"In else",Toast.LENGTH_SHORT).show();
//            if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.AVG_SPEED, "").equals("Clicked")) {
//                binding.textView10.setVisibility(View.VISIBLE);
//                binding.avgSpeedView.setVisibility(View.VISIBLE);
//            } else {
//                binding.textView10.setVisibility(View.INVISIBLE);
//                binding.avgSpeedView.setVisibility(View.INVISIBLE);
//            }
//
//            if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.MAX_SPEED, "").equals("Clicked")) {
//                binding.maxtitile.setVisibility(View.VISIBLE);
//                binding.maxSpeedView.setVisibility(View.VISIBLE);
//            } else {
//                binding.maxtitile.setVisibility(View.INVISIBLE);
//                binding.maxSpeedView.setVisibility(View.INVISIBLE);
//            }
//
//            if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.CURRENT_TIME, "").equals("Clicked")) {
//                binding.textTime.setVisibility(View.VISIBLE);
//            } else {
//                binding.textTime.setVisibility(View.INVISIBLE);
//            }
//
//            if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.BATTERY, "").equals("Clicked")) {
//                binding.battery.setVisibility(View.VISIBLE);
//            } else {
//                binding.battery.setVisibility(View.INVISIBLE);
//            }
//
//            if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.DISTANCE, "").equals("Clicked")) {
//                binding.distanceView.setVisibility(View.VISIBLE);
//            } else {
//                binding.distanceView.setVisibility(View.INVISIBLE);
//            }
//
//            if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.minimizeapp, "").equals("Clicked")) {
//                binding.minimizeIcon.setVisibility(View.VISIBLE);
//            } else {
//                binding.minimizeIcon.setVisibility(View.INVISIBLE);
//            }
//
//            if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.setspeedlimit, "").equals("Clicked")) {
//                binding.textAlarm.setVisibility(View.VISIBLE);
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                // SecondActivity.speedlimitclicked = false;
//            }
//
//            if (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.LIMIT_ALARM, "").equals("Clicked")) {
//                binding.textAlarm.setVisibility(View.VISIBLE);
//                //  binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(R.drawable.alarm_notification, 0, 0, 0);
//                // SecondActivity.speedalarmclicked = false;
//            }
////            else
////            {
////                binding.textAlarm.setVisibility(View.INVISIBLE);
////                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
////                //     SharedPreferenceHelper.getInstance().setStringValue(AppConstant.LIMIT_ALARM , "Not Clicked");
////
////            }
//
//
//            SharedPreferenceHelper.getInstance().setStringValue(AppConstant.isCheck, "Not Clicked");
//
//        }
//
//    }
//
//    public void setTheme() {
//        if (SetTheme.currentthemecolor == 1) {
//            binding.textTime.setTextColor(getResources().getColor(R.color.whiteColor));
//            binding.battery.setTextColor(getResources().getColor(R.color.whiteColor));  //battery
//            binding.distanceView.setTextColor(getResources().getColor(R.color.whiteColor));
//            binding.textAlarm.setTextColor(getResources().getColor(R.color.whiteColor));
//            binding.maxtitile.setTextColor(getResources().getColor(R.color.whiteColor));
//            binding.maxSpeedView.setTextColor(getResources().getColor(R.color.whiteColor));
//            binding.textView10.setTextColor(getResources().getColor(R.color.whiteColor)); //avg
//            binding.avgSpeedView.setTextColor(getResources().getColor(R.color.whiteColor));
//
//            Drawable drawable1 = getResources().getDrawable(R.drawable.distance_bg);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable1, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.whiteColor));
//            binding.distanceView.setBackground(drawable1);
//
//            Drawable drawable2 = getResources().getDrawable(R.drawable.battery);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable2, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.whiteColor));
//            binding.battery.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//
//            Drawable drawable3 = getResources().getDrawable(R.drawable.distance_bg);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable3, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.whiteColor));
//            binding.textAlarm.setBackground(drawable3);
//
//            Drawable drawable4 = getResources().getDrawable(R.drawable.hud_new);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable4, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.whiteColor));
//            //binding.hudBtn.setImageDrawable(drawable4);
//
//            Drawable drawable5 = getResources().getDrawable(R.drawable.minimize);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable5, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.whiteColor));
//            binding.minimizeIcon.setImageDrawable(drawable5);
//
//            Drawable drawable6 = getResources().getDrawable(R.drawable.speed_background);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable6, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.whiteColor));
//            //binding.viewPager.setBackground(drawable6);
//
//            if (SettingsActivity.ischecked == true) {
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.whiteColor));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//
//            if (SettingsActivity.ischecked == false && SettingsActivity.speedalarmclicked == true) {
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.whiteColor));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//
//        }
//
//        if (SetTheme.currentthemecolor == 2) {
//
//            Drawable drawable4 = getResources().getDrawable(R.drawable.hud_new);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable4, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.glowColor));
//            //binding.hudBtn.setImageDrawable(drawable4);
//
//            Drawable drawable5 = getResources().getDrawable(R.drawable.minimize);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable5, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.glowColor));
//            binding.minimizeIcon.setImageDrawable(drawable5);
//
//            Drawable drawable6 = getResources().getDrawable(R.drawable.speed_background);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable6, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.glowColor));
//            //binding.viewPager.setBackground(drawable6);
//
//            if (SettingsActivity.ischecked == true) {
//                binding.textTime.setTextColor(getResources().getColor(R.color.glowColor));
//                binding.battery.setTextColor(getResources().getColor(R.color.glowColor));  //battery
//                binding.distanceView.setTextColor(getResources().getColor(R.color.glowColor));
//                binding.textAlarm.setTextColor(getResources().getColor(R.color.glowColor));
//                binding.maxtitile.setTextColor(getResources().getColor(R.color.glowColor));
//                binding.maxSpeedView.setTextColor(getResources().getColor(R.color.glowColor));
//                binding.textView10.setTextColor(getResources().getColor(R.color.glowColor)); //avg
//                binding.avgSpeedView.setTextColor(getResources().getColor(R.color.glowColor));
//                Drawable drawable1 = getResources().getDrawable(R.drawable.distance_bg);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable1, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.glowColor));
//                binding.distanceView.setBackground(drawable1);
//
//                Drawable drawable2 = getResources().getDrawable(R.drawable.battery);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable2, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.glowColor));
//                binding.battery.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//
//                Drawable drawable3 = getResources().getDrawable(R.drawable.distance_bg);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable3, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.glowColor));
//                binding.textAlarm.setBackground(drawable3);
//
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.glowColor));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//
//
//            if (SettingsActivity.ischecked == false && SettingsActivity.speedalarmclicked == true) {
//                binding.textAlarm.setTextColor(getResources().getColor(R.color.glowColor));
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.glowColor));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//
//                Drawable drawable3 = getResources().getDrawable(R.drawable.distance_bg);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable3, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.glowColor));
//                binding.textAlarm.setBackground(drawable3);
//            }
//
//            if (SettingsActivity.ischecked == false && SettingsActivity.speedlimitclicked == true) {
//                binding.textAlarm.setTextColor(getResources().getColor(R.color.glowColor));
//                Drawable drawable3 = getResources().getDrawable(R.drawable.distance_bg);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable3, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.glowColor));
//                binding.textAlarm.setBackground(drawable3);
//            }
//
//            if (SettingsActivity.ischecked == false && (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.AVG_SPEED, "").equals("Clicked"))) {
//                binding.textView10.setTextColor(getResources().getColor(R.color.glowColor));
//                binding.avgSpeedView.setTextColor(getResources().getColor(R.color.glowColor));
//            }
//
//            if (SettingsActivity.ischecked == false && (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.MAX_SPEED, "").equals("Clicked"))) {
//                binding.maxtitile.setTextColor(getResources().getColor(R.color.glowColor));
//                binding.maxSpeedView.setTextColor(getResources().getColor(R.color.glowColor));
//            }
//
//            if (SettingsActivity.ischecked == false && (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.CURRENT_TIME, "").equals("Clicked"))) {
//                binding.textTime.setTextColor(getResources().getColor(R.color.glowColor));
//            }
//
//            if (SettingsActivity.ischecked == false && (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.DISTANCE, "").equals("Clicked"))) {
//                binding.distanceView.setTextColor(getResources().getColor(R.color.glowColor));
//                Drawable drawable1 = getResources().getDrawable(R.drawable.distance_bg);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable1, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.glowColor));
//                binding.distanceView.setBackground(drawable1);
//
//            }
//
//            if (SettingsActivity.ischecked == false && (SharedPreferenceHelper.getInstance().getStringValue(AppConstant.BATTERY, "").equals("Clicked"))) {
//                binding.battery.setTextColor(getResources().getColor(R.color.glowColor));
//                Drawable drawable2 = getResources().getDrawable(R.drawable.battery);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable2, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.glowColor));
//                binding.battery.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//
//            }
//        }
//
//        if (SetTheme.currentthemecolor == 3) {
//            binding.textTime.setTextColor(getResources().getColor(R.color.imagecolor1));
//            binding.battery.setTextColor(getResources().getColor(R.color.imagecolor1));  //battery
//            binding.distanceView.setTextColor(getResources().getColor(R.color.imagecolor1));
//            binding.textAlarm.setTextColor(getResources().getColor(R.color.imagecolor1));
//            binding.maxtitile.setTextColor(getResources().getColor(R.color.imagecolor1));
//            binding.maxSpeedView.setTextColor(getResources().getColor(R.color.imagecolor1));
//            binding.textView10.setTextColor(getResources().getColor(R.color.imagecolor1)); //avg
//            binding.avgSpeedView.setTextColor(getResources().getColor(R.color.imagecolor1));
//            Drawable drawable1 = getResources().getDrawable(R.drawable.distance_bg);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable1, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor1));
//            binding.distanceView.setBackground(drawable1);
//
//            Drawable drawable2 = getResources().getDrawable(R.drawable.battery);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable2, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor1));
//            binding.battery.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//
//            Drawable drawable3 = getResources().getDrawable(R.drawable.distance_bg);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable3, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor1));
//            binding.textAlarm.setBackground(drawable3);
//
//            Drawable drawable4 = getResources().getDrawable(R.drawable.hud_new);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable4, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor1));
//            //binding.hudBtn.setImageDrawable(drawable4);
//
//            Drawable drawable5 = getResources().getDrawable(R.drawable.minimize);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable5, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor1));
//            binding.minimizeIcon.setImageDrawable(drawable5);
//
//            Drawable drawable6 = getResources().getDrawable(R.drawable.speed_background);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable6, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor1));
//            //binding.viewPager.setBackground(drawable6);
//
//            if (SettingsActivity.ischecked == true) {
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor1));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//
//            if (SettingsActivity.ischecked == false && SettingsActivity.speedalarmclicked == true) {
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor1));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//        }
//
//        if (SetTheme.currentthemecolor == 4) {
//            binding.textTime.setTextColor(getResources().getColor(R.color.imagecolor2));
//            binding.battery.setTextColor(getResources().getColor(R.color.imagecolor2));  //battery
//            binding.distanceView.setTextColor(getResources().getColor(R.color.imagecolor2));
//            binding.textAlarm.setTextColor(getResources().getColor(R.color.imagecolor2));
//            binding.maxtitile.setTextColor(getResources().getColor(R.color.imagecolor2));
//            binding.maxSpeedView.setTextColor(getResources().getColor(R.color.imagecolor2));
//            binding.textView10.setTextColor(getResources().getColor(R.color.imagecolor2)); //avg
//            binding.avgSpeedView.setTextColor(getResources().getColor(R.color.imagecolor2));
//            Drawable drawable1 = getResources().getDrawable(R.drawable.distance_bg);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable1, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor2));
//            binding.distanceView.setBackground(drawable1);
//
//            Drawable drawable2 = getResources().getDrawable(R.drawable.battery);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable2, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor2));
//            binding.battery.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//
//            Drawable drawable3 = getResources().getDrawable(R.drawable.distance_bg);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable3, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor2));
//            binding.textAlarm.setBackground(drawable3);
//
//            Drawable drawable4 = getResources().getDrawable(R.drawable.hud_new);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable4, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor2));
//            //binding.hudBtn.setImageDrawable(drawable4);
//
//            Drawable drawable5 = getResources().getDrawable(R.drawable.minimize);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable5, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor2));
//            binding.minimizeIcon.setImageDrawable(drawable5);
//
//            Drawable drawable6 = getResources().getDrawable(R.drawable.speed_background);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable6, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor2));
//            //binding.viewPager.setBackground(drawable6);
//
//            if (SettingsActivity.ischecked == true) {
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor2));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//
//            if (SettingsActivity.ischecked == false && SettingsActivity.speedalarmclicked == true) {
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor2));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//        }
//
//        if (SetTheme.currentthemecolor == 5) {
//            binding.textTime.setTextColor(getResources().getColor(R.color.imagecolor3));
//            binding.battery.setTextColor(getResources().getColor(R.color.imagecolor3));  //battery
//            binding.distanceView.setTextColor(getResources().getColor(R.color.imagecolor3));
//            binding.textAlarm.setTextColor(getResources().getColor(R.color.imagecolor3));
//            binding.maxtitile.setTextColor(getResources().getColor(R.color.imagecolor3));
//            binding.maxSpeedView.setTextColor(getResources().getColor(R.color.imagecolor3));
//            binding.textView10.setTextColor(getResources().getColor(R.color.imagecolor3)); //avg
//            binding.avgSpeedView.setTextColor(getResources().getColor(R.color.imagecolor3));
//            Drawable drawable1 = getResources().getDrawable(R.drawable.distance_bg);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable1, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor3));
//            binding.distanceView.setBackground(drawable1);
//
//            Drawable drawable2 = getResources().getDrawable(R.drawable.battery);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable2, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor3));
//            binding.battery.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//
//            Drawable drawable3 = getResources().getDrawable(R.drawable.distance_bg);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable3, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor3));
//            binding.textAlarm.setBackground(drawable3);
//
//            Drawable drawable4 = getResources().getDrawable(R.drawable.hud_new);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable4, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor3));
//            //binding.hudBtn.setImageDrawable(drawable4);
//
//            Drawable drawable5 = getResources().getDrawable(R.drawable.minimize);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable5, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor3));
//            binding.minimizeIcon.setImageDrawable(drawable5);
//
//            Drawable drawable6 = getResources().getDrawable(R.drawable.speed_background);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable6, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor3));
//            //binding.viewPager.setBackground(drawable6);
//
//            if (SettingsActivity.ischecked == true) {
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor3));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//
//            if (SettingsActivity.ischecked == false && SettingsActivity.speedalarmclicked == true) {
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor3));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//        }
//
//        if (SetTheme.currentthemecolor == 6) {
//            binding.textTime.setTextColor(getResources().getColor(R.color.imagecolor4));
//            binding.battery.setTextColor(getResources().getColor(R.color.imagecolor4));  //battery
//            binding.distanceView.setTextColor(getResources().getColor(R.color.imagecolor4));
//            binding.textAlarm.setTextColor(getResources().getColor(R.color.imagecolor4));
//            binding.maxtitile.setTextColor(getResources().getColor(R.color.imagecolor4));
//            binding.maxSpeedView.setTextColor(getResources().getColor(R.color.imagecolor4));
//            binding.textView10.setTextColor(getResources().getColor(R.color.imagecolor4)); //avg
//            binding.avgSpeedView.setTextColor(getResources().getColor(R.color.imagecolor4));
//            Drawable drawable1 = getResources().getDrawable(R.drawable.distance_bg);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable1, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor4));
//            binding.distanceView.setBackground(drawable1);
//
//            Drawable drawable2 = getResources().getDrawable(R.drawable.battery);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable2, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor4));
//            binding.battery.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//
//            Drawable drawable3 = getResources().getDrawable(R.drawable.distance_bg);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable3, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor4));
//            binding.textAlarm.setBackground(drawable3);
//
//            Drawable drawable4 = getResources().getDrawable(R.drawable.hud_new);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable4, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor4));
//            //binding.hudBtn.setImageDrawable(drawable4);
//
//            Drawable drawable5 = getResources().getDrawable(R.drawable.minimize);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable5, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor4));
//            binding.minimizeIcon.setImageDrawable(drawable5);
//
//            Drawable drawable6 = getResources().getDrawable(R.drawable.speed_background);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable6, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor4));
//            //binding.viewPager.setBackground(drawable6);
//
//            if (SettingsActivity.ischecked == true) {
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor4));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//
//            if (SettingsActivity.ischecked == false && SettingsActivity.speedalarmclicked == true) {
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor4));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//        }
//
//        if (SetTheme.currentthemecolor == 7) {
//            binding.textTime.setTextColor(getResources().getColor(R.color.imagecolor5));
//            binding.battery.setTextColor(getResources().getColor(R.color.imagecolor5));  //battery
//            binding.distanceView.setTextColor(getResources().getColor(R.color.imagecolor5));
//            binding.textAlarm.setTextColor(getResources().getColor(R.color.imagecolor5));
//            binding.maxtitile.setTextColor(getResources().getColor(R.color.imagecolor5));
//            binding.maxSpeedView.setTextColor(getResources().getColor(R.color.imagecolor5));
//            binding.textView10.setTextColor(getResources().getColor(R.color.imagecolor5)); //avg
//            binding.avgSpeedView.setTextColor(getResources().getColor(R.color.imagecolor5));
//            Drawable drawable1 = getResources().getDrawable(R.drawable.distance_bg);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable1, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor5));
//            binding.distanceView.setBackground(drawable1);
//
//            Drawable drawable3 = getResources().getDrawable(R.drawable.distance_bg);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable3, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor5));
//            binding.textAlarm.setBackground(drawable3);
//
//            Drawable drawable4 = getResources().getDrawable(R.drawable.hud_new);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable4, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor5));
//            //binding.hudBtn.setImageDrawable(drawable4);
//
//            Drawable drawable5 = getResources().getDrawable(R.drawable.minimize);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable5, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor5));
//            binding.minimizeIcon.setImageDrawable(drawable5);
//
//
//            Drawable drawable2 = getResources().getDrawable(R.drawable.battery);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable2, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor5));
//            binding.battery.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
//
//            Drawable drawable6 = getResources().getDrawable(R.drawable.speed_background);
//            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//            DrawableCompat.setTint(drawable6, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor5));
//            //binding.viewPager.setBackground(drawable6);
//
//
//            if (SettingsActivity.ischecked) {
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor5));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//
//
//            if (SettingsActivity.ischecked == false && SettingsActivity.speedalarmclicked == true) {
//                Drawable drawable = getResources().getDrawable(R.drawable.alarm_notification);
//                //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
//                DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor5));
//                binding.textAlarm.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//        }
//    }*/
//
//    public void initializeView() {
//        // isminimizepressed = true;
//        Intent intent = new Intent(SpeedDashboardActivity2.this, FloatingViewService.class);
//        startService(intent);
//        moveTaskToBack(true);
//
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
//
//            //Check if the permission is granted or not.
//            if (resultCode == RESULT_OK) {
//                initializeView();
//            } else {
//                //Permission is not available
//                Toast.makeText(SpeedDashboardActivity2.this,
//                        "Draw over other app permission not available.",
//                        Toast.LENGTH_SHORT).show();
//
//                // finish();
//            }
//        } /*else {
//           // Toast.makeText(SpeedDashboardActivity.this, "permission code doesnt match", Toast.LENGTH_SHORT).show();
//            super.onActivityResult(requestCode, resultCode, data);
//        }*/
//    }
//
//    public void runningCountDownTimer() {
//        if (alreadyinitialized) {
//
//        } else {
//
//            countDownTimer = new CountDownTimer(300000, 1000) {
//
//                public void onTick(long millisUntilFinished) {
//                    isFinished = false;
//                    notificationalarm = false;
//                    alreadyinitialized = true;
//                }
//
//                public void onFinish() {
//                    alreadyinitialized = false;
//                    isFinished = true;
//
//
//                    if (currentSpeed != null && Integer.parseInt(currentSpeed) > speedLimit) {
//                        createNotification(String.valueOf(speedLimit));
//
//
//                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SpeedDashboardActivity2.this);
//                        builder1.setMessage("Your Safety is Important\nYour are currently over your speed limit");
//                        builder1.setCancelable(true);
//
//                        builder1.setPositiveButton(
//                                "Ignore",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.dismiss();
//                                        //  countDownTimer.start();
//
//                                    }
//                                });
//
//                        builder1.setNegativeButton(
//                                "Snooze",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        cancelNotification(SpeedDashboardActivity2.this, Notificationid);
//                                        dialog.dismiss();
//                                        countDownTimer.start();
//
//
//                                    }
//                                });
//
//                        if (!(SpeedDashboardActivity2.this).isFinishing()) {
//                            AlertDialog alert11 = builder1.create();
//                            alert11.show();
//                        }
//                    } else {
//                        countDownTimer.start();
//                    }
//
//
//                }
//
//            };
//        }
//    }
//
////    @Override
////    public void onSaveInstanceState(Bundle outState) {
//////---save whatever you need to persist—
////
////        if (outState == null) {
////            speedDashboardModel.setSpeedView("0");
////            speedDashboardModel.setUnitView("km/h");
////            speedDashboardModel.setDistanceView("0 M");
////            speedDashboardModel.setMaxSpeedView("0 km/h");
////            speedDashboardModel.setAvgSpeedView("0 km/h");
////        } else {
////            if (currentSpeed == null) {
////                outState.putString("currentspeed", "0");
////            } else {
////                outState.putString("currentspeed", currentSpeed);
////            }
////
////            if (maxSpeed == null) {
////                outState.putString("maxspeed", "0");
////            } else {
////                outState.putString("maxspeed", maxSpeed.toString());
////            }
////
////            if (avgSpeed == null) {
////                outState.putString("avgspeed", "0");
////            } else {
////                outState.putString("avgspeed", avgSpeed.toString());
////            }
////
////            if (distance == null) {
////                outState.putString("distance", "0");
////            } else {
////                outState.putString("distance", distance.toString());
////            }
////
////        }
////
////
////        super.onSaveInstanceState(outState);
////    }
////
////    @Override
////    public void onRestoreInstanceState(Bundle savedInstanceState) {
////        super.onRestoreInstanceState(savedInstanceState);
//////---retrieve the information persisted earlier---
////        Toast.makeText(this, "onRestore Created", Toast.LENGTH_SHORT).show();
////        currentSpeed = savedInstanceState.getString("currentspeed");
////        Log.d("currentspeed", currentSpeed);
////        //   Toast.makeText(this, ""+currentSpeed, Toast.LENGTH_SHORT).show();
////        maxSpeed = SpannableString.valueOf(savedInstanceState.getString("maxspeed"));
////        Log.d("maxspeed", maxSpeed.toString());
////        avgSpeed = SpannableString.valueOf(savedInstanceState.getString("avgspeed"));
////        Log.d("avgspeed", avgSpeed.toString());
////        distance = SpannableString.valueOf(savedInstanceState.getString("distance"));
////        Log.d("distance", distance.toString());
////        // speedDashboardModel.setDistanceView(distance.toString());
////    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//    }
//
//
//    private void buildAlertMessageNoGps() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("To continue, let your device turn on location using Google's location service.")
//                .setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog, final int id) {
//                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                    }
//                })
//                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog, final int id) {
//                        dialog.cancel();
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        if (!(SpeedDashboardActivity2.this).isFinishing()) {
//            alert.show();
//        }
//    }
//
//    public void statusCheck() {
//        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        try {
//            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                buildAlertMessageNoGps();
//
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    public void animate() {
//        scaleAnimation = new ScaleAnimation(1.0f, 1.15f, 1.0f, 1.15f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        scaleAnimation.setDuration(500);
//        scaleAnimation.setRepeatCount(Animation.INFINITE);
//        scaleAnimation.setRepeatMode(Animation.REVERSE);
//        binding.textAlarm.startAnimation(scaleAnimation);
//    }
//
//    public int getLocationMode(Context context) throws Settings.SettingNotFoundException {
//        return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
//
//    }
//
//    public void finishDriveDialog() {
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(SpeedDashboardActivity2.this);
//        builder1.setMessage("Do you want to quit drive?");
//        builder1.setCancelable(true);
//
//        builder1.setPositiveButton(
//                "Yes",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        String triptype, startTime, endtime;
//                        dialog.cancel();
//                        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US); //dd-MM-yyyy
//                        Calendar calender = Calendar.getInstance();
//                        TimeZone ccme = calender.getTimeZone();
//                        timeFormat.setTimeZone(ccme);
//                        endtime = timeFormat.format(new Date());
//
//                        triptype = SharedPreferenceHelper.getInstance().getStringValue(Constants.triptype, "");
//                        startTime = LocationViewModel.startTime;
//
//                        if (maxSpeed!=null && avgSpeed!=null && distance!=null)
//                        {
//                            String str = distance;
//                            String[] distancestring = str.split("\\s+");
//
//                            double carfueleconomyindouble = Double.parseDouble(carfueleconomyperkm);
//                            Double fueleconomycalculated = Double.parseDouble(distancestring[0]) * carfueleconomyindouble;
//                           // String fueleconomypertrip = String.valueOf(fueleconomycalculated);
//                            String fueleconomypertrip = String.format("%.3f" , fueleconomycalculated);
//                           // fueleconomypertrip = String.format("%.3f" , fueleconomypertrip);
//
//                            // todo save trip here in table / room
//                          //  String key = usertripsdatabase.push().getKey();
//                            // String tripTitle, String destination, String carname, String triptype, int maxspeed, int avgspeed, int distanceCovered,
//                            // int fueleconomypertrip, Date saveDate, int fuelCostPerUnit, int totalExpenses, int noOfLitres
//                         //   Trip trip = new Trip("trip to muree", "muree", carname, triptype, startTime, endtime, maxSpeed, avgSpeed, distance, date, dateinmillis, fueleconomypertrip);
//                            Trip trip = new Trip("trip to muree", "muree", carname, triptype, Integer.parseInt(maxSpeed), Integer.parseInt(avgSpeed), Integer.parseInt(distance),Integer.parseInt(fueleconomypertrip), new Date(date), 90, 5000, 20);
//                        //    usertripsdatabase.child(key).setValue(trip);
//                            Toast.makeText(SpeedDashboardActivity2.this, "Your trip is saved.", Toast.LENGTH_SHORT).show();
//
//                            Intent i  = new Intent(SpeedDashboardActivity2.this , MainActivity.class);
//                            startActivity(i);
//                            finish();
//                        }
//                        else if(maxSpeed == null && avgSpeed == null && distance == null)
//                        {
//                            // todo save trip here
////                            String key = usertripsdatabase.push().getKey();
////                            Trip trip = new Trip(carname, triptype, startTime, endtime, "0" + " "+SharedPreferenceHelper.getInstance().getStringValue(AppConstant.speedunits , ""), "0" + " "+SharedPreferenceHelper.getInstance().getStringValue(Constants.speedunits , ""), "0" + " "+SharedPreferenceHelper.getInstance().getStringValue(AppConstant.distanceunits , ""), date, dateinmillis, "0");
////                            usertripsdatabase.child(key).setValue(trip);
////                            Toast.makeText(SpeedDashboardActivity.this, "Your trip is saved.", Toast.LENGTH_SHORT).show();
//
//                            Intent i  = new Intent(SpeedDashboardActivity2.this , MainActivity.class);
//                            startActivity(i);
//                            finish();
//                        }
//
//
//                        /*Intent i = new Intent(SpeedDashboardActivity.this , HomeActivity.class);
//                        startActivity(i);
//                        finish();*/
//                    }
//                });
//
//        builder1.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//    }
//
//    public class NewSpeedLimitDialog extends Dialog implements
//            View.OnClickListener {
//
//        public Activity c;
//        public Dialog d;
//        public TextView done;
//        public EditText newspeededittext;
//
//        public NewSpeedLimitDialog(Activity a) {
//            super(a);
//            // TODO Auto-generated constructor stub
//            this.c = a;
//        }
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//            setContentView(R.layout.dialog_speedlimit);
//            done = (TextView) findViewById(R.id.done);
//            newspeededittext = (EditText) findViewById(R.id.newspeededittext);
//            done.setOnClickListener(this);
//
//        }
//
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.done:
//                    try {
//                        if (newspeededittext.getText().toString().length() != 0 && newspeededittext.getText().toString().length() <= 3) {
//                            //   SetSpeedLimitFuturistic.alarmon = true;
//                           /* SharedPreferenceHelper.getInstance().setStringValue(AppConstant.limitalarmfuturistic , "Clicked");
//                            FuturisticSettingsActivity.limitalarmpressed = true;*/
//
//                            try {
//                                speedLimit = Integer.parseInt(newspeededittext.getText().toString());
//                                LocationViewModel.speedLimit = Integer.parseInt(newspeededittext.getText().toString());
//                                SharedPreferenceHelper.getInstance().setIntegerValue(Constants.SPEED_LIMIT, Integer.parseInt(newspeededittext.getText().toString()));
//                                // FuturisticSettingsActivity.limitalarmpressed = true;
//                                if (SharedPreferenceHelper.getInstance().getStringValue(Constants.SPEED_LIMIT_METER_TYPE, "km/h").equals("km/h")) {
//                                    binding.textAlarm.setText(newspeededittext.getText().toString() + "km/h");
//                                } else if (SharedPreferenceHelper.getInstance().getStringValue(Constants.SPEED_LIMIT_METER_TYPE, "km/h").equals("mph")) {
//                                    binding.textAlarm.setText(newspeededittext.getText().toString() + "mph");
//                                }
//                            } catch (NullPointerException e) {
//                                e.printStackTrace();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            dismiss();
//                        } else if (newspeededittext.getText().toString().length() == 0 || newspeededittext.getText().toString().length() > 3) {
//                            //SharedPreferenceHelper.getInstance().setIntegerValue(AppConstant.SPEED_LIMIT, Integer.parseInt("0"));
//                            //binding.speedtext.setText("0");
//                            Toast.makeText(SpeedDashboardActivity2.this, "Please enter a valid speed limit", Toast.LENGTH_SHORT).show();
//                            newspeededittext.setText("");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        //  Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//                default:
//                    break;
//            }
//            // dismiss();
//        }
//    }
//}