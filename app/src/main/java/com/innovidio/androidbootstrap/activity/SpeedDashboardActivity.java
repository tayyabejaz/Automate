package com.innovidio.androidbootstrap.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.github.anastr.speedviewlib.PointerSpeedometer;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.snackbar.Snackbar;
import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.databinding.ActivitySpeedDashboardBinding;
import com.innovidio.androidbootstrap.databinding.DialogStopTripBinding;
import com.innovidio.androidbootstrap.db.converters.TripTypeConverters;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.entity.models.DataManager;
import com.innovidio.androidbootstrap.entity.models.FullAddress;
import com.innovidio.androidbootstrap.entity.models.SpeedDashboardModel;
import com.innovidio.androidbootstrap.repository.PreferencesRepository;
import com.innovidio.androidbootstrap.service.FloatingViewService;
import com.innovidio.androidbootstrap.viewmodel.LocationViewModel;
import com.innovidio.androidbootstrap.viewmodel.TripViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static com.innovidio.androidbootstrap.Constants.FUEL_UP_FORM;
import static com.innovidio.androidbootstrap.Constants.KM;
import static com.innovidio.androidbootstrap.Constants.KM_HR;
import static com.innovidio.androidbootstrap.Constants.MILES;
import static com.innovidio.androidbootstrap.Constants.M_HR;

public class SpeedDashboardActivity extends DaggerAppCompatActivity implements GpsStatus.Listener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final String TAG = "SpeedDashboardActivity";
    private static final String CHANNEL_ID = "channelId";
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 1;
    public static Double currentSpeed;
    ActivitySpeedDashboardBinding binding;
    SpeedDashboardModel speedDashboardModel;
    int maximumspeed = 280;
    int maxSpeed;
    int avgSpeed;
    Double distance = 0.0;
    long[] vibrate = {500, 1000};
    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    boolean isClickHudBtn = false;
    Notification.Builder notification;
    GoogleApiClient googleApiClient;
    ScaleAnimation scaleAnimation;
    boolean alreadyinitialized = false;
    CountDownTimer countDownTimer;
    boolean isFinished = true;
    boolean notificationalarm = false;
    int speedLimit;
    int Notificationid = 1;
    MediaPlayer mp;
    boolean isPlaing = false;
    Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    int counter = 0;
    @Inject
    LocationViewModel locationViewModel;
    boolean isstart = true;
    private LocationManager mLocationManager;
    private DataManager data;
    Double carfueleconomyperkm;

    @Inject
    AppPreferences appPreferences;

    @Inject
    TripViewModel tripViewModel;

    @Inject
    PreferencesRepository prefRepo;

    private BroadcastReceiver mBatteryLevelReciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //int rawLevel = intent.getIntExtra("level", -1);
            int rawLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            //int scale = intent.getIntExtra("scale", -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int level = -1;
            if (rawLevel >= 0 && scale > 0) {
                level = (rawLevel * 100) / scale;
            }
            binding.battery.setText("" + level + "%");
        }

    };

    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        try {
            nMgr.cancel(notifyId);
            nMgr.cancelAll();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_speed_dashboard);
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        PointerSpeedometer pointerSpeedometer = (PointerSpeedometer) findViewById(R.id.iv_imageSpeedometer);
        pointerSpeedometer.setMaxSpeed(maximumspeed);

        prefRepo.setAutoDriveDetect(appPreferences.getBoolean(AppPreferences.Key.DRIVE_DETECT));

        carfueleconomyperkm = appPreferences.getDouble(AppPreferences.Key.FUEL_ECONOMY, 0.7f);

        Log.d("speedunits: ", prefRepo.getSpeedUnit() + "");
        Log.d("distanceunits: ", prefRepo.getDistanceUnit() + "");

        prefRepo.setSpeedLimit(appPreferences.getInt(AppPreferences.Key.SPEED_LIMIT, 50));
        speedLimit = prefRepo.getSpeedLimit();

        if (speedLimit == 0) {
            binding.textAlarm.setVisibility(View.GONE);
            binding.textSpeedlimit.setVisibility(View.GONE);
        } else {
            binding.textAlarm.setVisibility(View.VISIBLE);
            binding.textSpeedlimit.setVisibility(View.VISIBLE);
        }

        binding.textAlarm.setText(speedLimit + " " + prefRepo.getSpeedUnit());
        Snackbar snackbar = Snackbar
                .make(binding.maincontainer, "The data will appear as soon as we detect your movement", Snackbar.LENGTH_LONG);
        snackbar.show();

        if (Build.VERSION.SDK_INT >= 21) {
            Drawable drawable1 = getResources().getDrawable(R.drawable.minimize);
            DrawableCompat.setTint(drawable1, ContextCompat.getColor(this, R.color.whiteColor));
            binding.minimizeIcon.setImageDrawable(drawable1);
        } else {
            Drawable drawable1 = ContextCompat.getDrawable(SpeedDashboardActivity.this, R.drawable.minimize);
            Drawable wrappedDrawable1 = null;
            try {
                wrappedDrawable1 = DrawableCompat.wrap(drawable1);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            DrawableCompat.setTint(wrappedDrawable1, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.whiteColor));
            binding.minimizeIcon.setImageDrawable(wrappedDrawable1);
        }

        speedDashboardModel = new SpeedDashboardModel();

        try {
            mp = new MediaPlayer();
            mp = MediaPlayer.create(getApplicationContext(), notification1);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        data = new DataManager();

        binding.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDriveDialog();
            }
        });

        if (prefRepo.getSpeedUnit().equals(KM_HR)) {
            speedDashboardModel.setSpeedView("0");
            speedDashboardModel.setUnitView(prefRepo.getSpeedUnit());
            speedDashboardModel.setDistanceView("0 " + KM_HR);
            speedDashboardModel.setMaxSpeedView("0 " + KM_HR);
            speedDashboardModel.setAvgSpeedView("0 " + KM_HR);
        } else {
            speedDashboardModel.setSpeedView("0");
            speedDashboardModel.setUnitView(prefRepo.getSpeedUnit());
            speedDashboardModel.setDistanceView("0 " + M_HR);
            speedDashboardModel.setMaxSpeedView("0 " + M_HR);
            speedDashboardModel.setAvgSpeedView("0 " + M_HR);
        }

        binding.speedUnit.setText(prefRepo.getSpeedUnit());

        binding.chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer chrono) {
                long time;
                time = SystemClock.elapsedRealtime() - chrono.getBase();
                Log.d("basetime", "" + chrono.getBase() + "\t" + "realtime" + "" + SystemClock.elapsedRealtime() + "\t" + "finaltime" + time);
                locationViewModel.setChronoTimerMutableLiveData(time);
            }
        });
        locationViewModel.getLocationMutableLiveData().observe(this, new Observer<DataManager>() {
            @Override
            public void onChanged(@Nullable DataManager dataManager) {
                //  dataManagerLocal = dataManager;

                /*if (mp!=null && mp.isPlaying())
                {
                    mp.stop();
                }*/

                if (isstart) {
                    binding.chronometer.start();
                    isstart = false;
                }


                long time = 0;
                double distancecovered = 0;
                try {
                    time = dataManager.getTime();
                    distancecovered = dataManager.getDistance();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                double average;

                if (time <= 0) {
                    average = 0.0;
                } else {

                    average = (distancecovered / (time / 1000)) * 3.6;
                }


                int maxSpeedTemp = 0;
                try {
                    maxSpeedTemp = dataManager.getMaxSpeed();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                double distanceTemp = 0;
                try {
                    distanceTemp = dataManager.getDistance();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                double averageTemp = average;

                if (prefRepo.getSpeedUnit().equals(KM_HR)) {
                    try {
                        if (prefRepo.getDistanceUnit().equals(KM)) {
                            distanceTemp = distanceTemp * 0.001;
                        } else if (prefRepo.getDistanceUnit().equals(MILES)) {
                            distanceTemp = distanceTemp * 0.000621371;
                        } else {
                            distanceTemp = distanceTemp * 0.001;
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (prefRepo.getSpeedUnit().equals(M_HR)) {
                    try {
                        maxSpeedTemp *= 0.62137119;
                        if (prefRepo.getDistanceUnit().equals(KM)) {
                            distanceTemp = distanceTemp * 0.001;
                        } else if (prefRepo.getDistanceUnit().equals(MILES)) {
                            distanceTemp = distanceTemp * 0.000621371;
                        } else {
                            distanceTemp = distanceTemp * 0.001;
                        }
                        averageTemp *= 0.62137119;
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                speedDashboardModel.setUnitView(prefRepo.getSpeedUnit());

                Log.d("maxspeed", maxSpeedTemp + "\t" + "speedavg" + averageTemp + "\t" + "distance" + distanceTemp);


                //  maxSpeed = String.format("%.0f", maxSpeedTemp) + " " + speedUnits;
                maxSpeed = maxSpeedTemp;
                // speedDashboardModel.setMaxSpeedView(String.format("%.0f", maxSpeedTemp) + " " + speedUnits);
                speedDashboardModel.setMaxSpeedView(maxSpeedTemp + " " + prefRepo.getSpeedUnit());

                // avgSpeed =  String.format("%.0f", averageTemp)+ speedUnits;
                try {
                    //  avgSpeed = dataManager.getTotalAvgSpeed() + " " + speedUnits;
                    avgSpeed = dataManager.getTotalAvgSpeed();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    avgSpeed = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                speedDashboardModel.setAvgSpeedView(avgSpeed + " " + prefRepo.getSpeedUnit());

                // distance = String.format("%.2f", distanceTemp) + " " + distanceUnits;
                distance = distanceTemp;
                speedDashboardModel.setDistanceView(String.format("%.2f", distanceTemp) + " " + prefRepo.getDistanceUnit());

                binding.setViewModel(speedDashboardModel);

                try {
                    currentSpeed = dataManager.getCurrentSpeed();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (currentSpeed != null) {
                    binding.currentSpeed.setText(currentSpeed + "");
                    //binding.ivImageSpeedometer.speedTo(Integer.parseInt(currentSpeed));
                    //binding.ivImageSpeedometer.speedTo(Integer.parseInt(currentSpeed));
                    pointerSpeedometer.speedTo((int) Double.parseDouble(currentSpeed + ""));
                } else {
                    binding.currentSpeed.setText("0");
                    // binding.ivImageSpeedometer.speedTo(0);
                    // binding.ivImageSpeedometer.speedTo(0);
                    pointerSpeedometer.speedTo(0);
                }

                if (currentSpeed != null && currentSpeed < speedLimit) {
                    binding.currentSpeed.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.speedUnit.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.textTime.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.battery.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.textMaxspeed.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.maxSpeedView.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.avgSpeedView.setTextColor(getResources().getColor(R.color.whiteColor));
                    //  binding.textView10.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.textDistance.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.distanceView.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.textAlarm.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.hud.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.textAveragespeed.setTextColor(getResources().getColor(R.color.whiteColor));
                    //  binding.stop.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.textDuration.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.chronometer.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.textCurrentspeed.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.textSpeedlimit.setTextColor(getResources().getColor(R.color.whiteColor));
                    binding.stop.setTextColor(getResources().getColor(R.color.blackColor));
                    binding.fuelup.setTextColor(getResources().getColor(R.color.blackColor));
                    if (Build.VERSION.SDK_INT >= 21) {
                        Drawable drawable = getResources().getDrawable(R.drawable.minimize);
                        //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
                        DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.whiteColor));
                        binding.minimizeIcon.setImageDrawable(drawable);
                    } else {

                        Drawable drawable = ContextCompat.getDrawable(SpeedDashboardActivity.this, R.drawable.minimize);
                        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
                        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.whiteColor));
                        binding.minimizeIcon.setImageDrawable(drawable);
                    }
                }
            }
        });


        locationViewModel.getSpeedexceedMutableLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String value) {
                if (value != null) {
                    if (currentSpeed != null && currentSpeed > speedLimit) {
                        if (mp != null && !mp.isPlaying()) {
                            //  mp.setLooping(true);
                            mp.start();
                        }

                        setMeterViewForOverSpeed();


                    } else {
                        if (mp != null && mp.isPlaying()) {
                            mp.stop();
                        }
                        setMeterViewForNormalSpeed();
                    }
                }

            }
        });

        binding.speedUnit.setText(prefRepo.getSpeedUnit());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setcurrentTime();

        binding.setViewModel(speedDashboardModel);
        binding.setActivity(this);
        // historySaveDialog = new HistorySaveDialog(this);

        //setValue();


        data.setFirstTime(true);

        //  binding.stop.setOnClickListener(this);
        binding.hud.setOnClickListener(this);
        binding.minimizeIcon.setOnClickListener(this);
        binding.textAlarm.setOnClickListener(this);
        binding.fuelup.setOnClickListener(this);


        runningCountDownTimer();
        animate();
    }

    public void hudAnimationButton() {
        scaleAnimation = new ScaleAnimation(1.0f, 1.15f, 1.0f, 1.15f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        binding.hud.startAnimation(scaleAnimation);
    }

    private void setMeterViewForOverSpeed() {
        binding.currentSpeed.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.speedUnit.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.textTime.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.battery.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.textMaxspeed.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.maxSpeedView.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.avgSpeedView.setTextColor(getResources().getColor(R.color.imagecolor1));
        //   binding.textView10.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.textDistance.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.distanceView.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.textAlarm.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.hud.setTextColor(getResources().getColor(R.color.imagecolor1));

        binding.textAveragespeed.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.textDuration.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.chronometer.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.textCurrentspeed.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.textSpeedlimit.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.stop.setTextColor(getResources().getColor(R.color.imagecolor1));
        binding.fuelup.setTextColor(getResources().getColor(R.color.imagecolor1));
        //  binding.stop.setTextColor(getResources().getColor(R.color.imagecolor1));
        if (Build.VERSION.SDK_INT >= 21) {
            Drawable drawable = getResources().getDrawable(R.drawable.minimize);
            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor1));
            binding.minimizeIcon.setImageDrawable(drawable);
        } else {

            Drawable drawable = ContextCompat.getDrawable(SpeedDashboardActivity.this, R.drawable.minimize);
            Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.imagecolor1));
            binding.minimizeIcon.setImageDrawable(drawable);
        }
    }

    private void setMeterViewForNormalSpeed() {
        binding.currentSpeed.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.speedUnit.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.textTime.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.battery.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.textMaxspeed.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.maxSpeedView.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.avgSpeedView.setTextColor(getResources().getColor(R.color.whiteColor));
        //  binding.textView10.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.textDistance.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.distanceView.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.textAlarm.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.hud.setTextColor(getResources().getColor(R.color.whiteColor));
        //  binding.stop.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.textAveragespeed.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.textDuration.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.chronometer.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.textCurrentspeed.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.textSpeedlimit.setTextColor(getResources().getColor(R.color.whiteColor));
        binding.stop.setTextColor(getResources().getColor(R.color.blackColor));
        binding.fuelup.setTextColor(getResources().getColor(R.color.blackColor));

        if (Build.VERSION.SDK_INT >= 21) {
            Drawable drawable = getResources().getDrawable(R.drawable.minimize);
            //  drawable.mutate().setColorFilter(0xffff0000, PorterDuff.Mode.SRC_IN);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.whiteColor));
            binding.minimizeIcon.setImageDrawable(drawable);
        } else {

            Drawable drawable = ContextCompat.getDrawable(SpeedDashboardActivity.this, R.drawable.minimize);
            Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(SpeedDashboardActivity.this, R.color.whiteColor));
            binding.minimizeIcon.setImageDrawable(drawable);
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.save_track_btn:
            // todo read carefully what to do here
//                {
//                HistoryManager.getInstance(this).saveHistoryData(maxSpeed, avgSpeed, distance);
////                historySaveDialog.showDialog();
//                startActivity(new Intent(this, HistoryActivity.class));
//                finish();
//
//                break;
//            }
//            case R.id.kmph_btn:
//                SharedPreferenceHelper.getInstance().setStringValue(Constants.METER_UNIT, "km/h");
//                SpeedMeterManager.getInstance().setKmphBtnValue(binding.kmphBtn, binding.mphBtn/*, binding.knotBtn*/);
//                break;
//            case R.id.mph_btn:
//                SharedPreferenceHelper.getInstance().setStringValue(Constants.METER_UNIT, "mph");
//                SpeedMeterManager.getInstance().setMphBtnValue(binding.kmphBtn, binding.mphBtn/*, binding.knotBtn*/);
//                break;

            case R.id.fuelup:
                UtilClass.startFormActivity(this, FUEL_UP_FORM);
                break;

            case R.id.minimizeIcon:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

                    //If the draw over permission is not available open the settings screen
                    //to grant the permission.

                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
                } else {

                    initializeView();
                }

                break;

            case R.id.hud:
                // if (!SharedPreferenceHelper.getInstance().getStringValue(Constants.VEHICLE_TYPE, "cycle").equals("cycle"))
                // {
                if (!isClickHudBtn) {
                    isClickHudBtn = true;
                    //  binding.hudBtn.setImageResource(R.drawable.normal_view);
                    setHudView();
                } else {
                    isClickHudBtn = false;
                    //  binding.hudBtn.setImageResource(R.drawable.hud_new);
                    setNormalView();


                }
                break;

            case R.id.text_alarm:
                NewSpeedLimitDialog newSpeedLimitDialog = new NewSpeedLimitDialog(SpeedDashboardActivity.this);
                newSpeedLimitDialog.show();

                break;
        }
    }


    private void setNormalView() {
        binding.textAlarm.setScaleX(1);
        binding.textTime.setScaleX(1);
        binding.battery.setScaleX(1);
        binding.maxSpeedView.setScaleX(1);
        binding.textMaxspeed.setScaleX(1);
        binding.avgSpeedView.setScaleX(1);
        binding.distanceView.setScaleX(1);
        binding.currentSpeed.setScaleX(1);
        binding.speedUnit.setScaleX(1);
        binding.hud.setScaleX(1);
        binding.minimizeIcon.setScaleX(1);
        binding.ivImageSpeedometer.setScaleX(1);
        binding.textDistance.setScaleX(1);
        binding.stop.setScaleX(1);

        binding.textAveragespeed.setScaleX(1);
        binding.textDuration.setScaleX(1);
        binding.chronometer.setScaleX(1);
        binding.textCurrentspeed.setScaleX(1);
        binding.textSpeedlimit.setScaleX(1);
        binding.fuelup.setScaleX(1);
    }

    private void setHudView() {
        binding.textAlarm.setScaleX(-1);
        binding.textTime.setScaleX(-1);
        binding.battery.setScaleX(-1);
        binding.textMaxspeed.setScaleX(-1);
        binding.maxSpeedView.setScaleX(-1);
        binding.avgSpeedView.setScaleX(-1);
        binding.textDistance.setScaleX(-1);
        binding.distanceView.setScaleX(-1);
        binding.currentSpeed.setScaleX(-1);
        binding.speedUnit.setScaleX(-1);
        binding.minimizeIcon.setScaleX(-1);
        binding.ivImageSpeedometer.setScaleX(-1);
        binding.hud.setScaleX(-1);
        binding.stop.setScaleX(-1);

        binding.textAveragespeed.setScaleX(-1);
        binding.textDuration.setScaleX(-1);
        binding.chronometer.setScaleX(-1);
        binding.textCurrentspeed.setScaleX(-1);
        binding.textSpeedlimit.setScaleX(-1);
        binding.fuelup.setScaleX(-1);
    }

    @Override
    protected void onResume() {
        super.onResume();


        //   statusCheck();

        int locationmode = 0;
        try {
            locationmode = getLocationMode(SpeedDashboardActivity.this);

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                showGpsDisabledDialog();
            } else if (locationmode != 3) {
                // int locationmode = getLocationMode(SpeedDashboardSimpleActivity.this);
//                if (locationmode!=3)
//                {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(SpeedDashboardActivity.this);
                builder1.setMessage("Please enable Location Mode with High Accuracy to proceed");
                builder1.setCancelable(false);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent i = new Intent(SpeedDashboardActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                //  Toast.makeText(getApplicationContext(), "Please enable Location Mode with High Accuracy to proceed", Toast.LENGTH_SHORT).show();
                //  startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                // }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (data == null) {
            data = new DataManager();
        }

        batteryLevel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBatteryLevelReciver);

    }

    public void showGpsDisabledDialog() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            // **************************
            builder.setAlwaysShow(true); // this is the key ingredient
            // **************************

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can
                            // initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be
                            // fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling
                                // startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(SpeedDashboardActivity.this, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have
                            // no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
        }

    }

    @Override
    public void onGpsStatusChanged(int event) {

        switch (event) {
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                break;

            case GpsStatus.GPS_EVENT_STOPPED:
                if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    showGpsDisabledDialog();
                }
                break;
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                break;
        }

    }


    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        finishDriveDialog();
    }

    private void createNotification(String speedLimit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel chan1 = new NotificationChannel(CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setLightColor(Color.GREEN);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            assert manager != null;
            manager.createNotificationChannel(chan1);

            notification = new Notification.Builder(this, chan1.getId());

        } else {
            notification = new Notification.Builder(this);

        }


        notification.setContentTitle(getString(R.string.running))
                .setContentText("Speed limit across the " + speedLimit)
                .setVibrate(vibrate)
                .setSound(uri)
                .setSmallIcon(R.drawable.ic_directions_car);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(Notificationid, notification.build());


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void setcurrentTime() {

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                                String dateString = sdf.format(date);
                                binding.textTime.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

    public void batterypercent() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float) scale;

        int batLevel = (int) (batteryPct * 100);
        binding.battery.setText(String.valueOf(batLevel));
    }

    private void batteryLevel() {
        IntentFilter batteryLevelFliter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBatteryLevelReciver, batteryLevelFliter);
    }

    public void initializeView() {
        // isminimizepressed = true;
        Intent intent = new Intent(SpeedDashboardActivity.this, FloatingViewService.class);
        startService(intent);
        moveTaskToBack(true);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                initializeView();
            } else {
                //Permission is not available
                Toast.makeText(SpeedDashboardActivity.this,
                        "Draw over other app permission not available.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void runningCountDownTimer() {
        if (alreadyinitialized) {

        } else {

            countDownTimer = new CountDownTimer(300000, 1000) {

                public void onTick(long millisUntilFinished) {
                    isFinished = false;
                    notificationalarm = false;
                    alreadyinitialized = true;
                }

                public void onFinish() {
                    alreadyinitialized = false;
                    isFinished = true;


                    if (currentSpeed != null && Integer.parseInt(currentSpeed + "") > speedLimit) {
                        createNotification(String.valueOf(speedLimit));


                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SpeedDashboardActivity.this);
                        builder1.setMessage("Your Safety is Important\nYour are currently over your speed limit");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Ignore",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        //  countDownTimer.start();

                                    }
                                });

                        builder1.setNegativeButton(
                                "Snooze",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        cancelNotification(SpeedDashboardActivity.this, Notificationid);
                                        dialog.dismiss();
                                        countDownTimer.start();


                                    }
                                });

                        if (!(SpeedDashboardActivity.this).isFinishing()) {
                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }
                    } else {
                        countDownTimer.start();
                    }


                }

            };
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("To continue, let your device turn on location using Google's location service.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        if (!(SpeedDashboardActivity.this).isFinishing()) {
            alert.show();
        }
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void animate() {
        scaleAnimation = new ScaleAnimation(1.0f, 1.15f, 1.0f, 1.15f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        binding.textAlarm.startAnimation(scaleAnimation);
    }

    public int getLocationMode(Context context) throws Settings.SettingNotFoundException {
        return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

    }

    public void finishDriveDialog() {
        if (mp != null && mp.isPlaying()) {
            mp.stop();
        }
        Trip trip = new Trip();
        DialogStopTripBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_stop_trip, null, false);
        View dialogView = binding.getRoot();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        exitDialog.setView(dialogView);
        exitDialog.show();

        FullAddress startFullAddress = UtilClass.getAddressFromLatLon(this, locationViewModel.getInitialLocation());
        FullAddress endFullAddress = UtilClass.getAddressFromLatLon(this, locationViewModel.getLastLocation());

        if (startFullAddress != null) {
            appPreferences.put(AppPreferences.Key.START_LOCATION, startFullAddress.getAddress());
        }
        if (endFullAddress != null) {
            appPreferences.put(AppPreferences.Key.END_LOCATION, endFullAddress.getAddress());
        }


        String triptype = appPreferences.getString(AppPreferences.Key.TRIP_TYPE, Trip.TripType.PERSONAL.name());
        Trip.TripType tripType = TripTypeConverters.restoreEnumFromString(triptype);
        if (maxSpeed != 0 && avgSpeed != 0 && distance != null) {
            String str = distance + "";
            String[] distancestring = str.split("\\s+");

            // double carfueleconomyindouble = Double.parseDouble(carfueleconomyperkm);
            Double fueleconomycalculated = Double.parseDouble(distancestring[0]) * carfueleconomyperkm;
            // String fueleconomypertrip = String.valueOf(fueleconomycalculated);
            String fueleconomypertrip = String.format("%.3f", fueleconomycalculated);
            // fueleconomypertrip = String.format("%.3f" , fueleconomypertrip);

            Date currentTime = Calendar.getInstance().getTime();


            trip.setCarId(appPreferences.getInt(AppPreferences.Key.SELECTED_CAR_ID, 1));
            trip.setTripType(tripType);
            trip.setStartTime(LocationViewModel.startTime);
            trip.setEndTime(currentTime); // or end time
            trip.setMaxspeed(maxSpeed);
            trip.setAvgspeed(avgSpeed);
            trip.setDistanceCovered(UtilClass.getRoundFigureValue(distance));
            trip.setSaveDate(currentTime);
            trip.setFueleconomypertrip(UtilClass.getRoundFigureValue(fueleconomycalculated));
            trip.setNoOfLitres((int) (distance / 13f));
            trip.setIntialOdometer(appPreferences.getInt(AppPreferences.Key.START_ODOMETER)); // not set yet
            double finalOdoMeter = appPreferences.getInt(AppPreferences.Key.START_ODOMETER) + distance;
            trip.setFinalOdometer((int) finalOdoMeter); // not set yet
            trip.setFuelCostPerUnit(prefRepo.getFuelUnitPrice()); // not set yet
            trip.setOrigin(appPreferences.getString(AppPreferences.Key.START_LOCATION, "Address not found."));
            trip.setDestination(appPreferences.getString(AppPreferences.Key.END_LOCATION, "Address not found.")); // not set yet

            binding.setTripData(trip);
//            Toast.makeText(SpeedDashboardActivity.this, "Your trip is saved.", Toast.LENGTH_SHORT).show();

        } else if (maxSpeed == 0 && avgSpeed == 0 && distance == 0.0) {
//            Toast.makeText(SpeedDashboardActivity.this, "Your trip is not saved.", Toast.LENGTH_SHORT).show();
        }

        binding.btnDialogSaveTrip.setOnClickListener(view -> {
            tripViewModel.addTrip(trip);
            exitDialog.dismiss();
            Toast.makeText(SpeedDashboardActivity.this, "Your trip is saved.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(SpeedDashboardActivity.this, MainActivity.class);
            startActivity(i);
            finish();

        });
    }

    public class NewSpeedLimitDialog extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        public TextView done;
        public EditText newspeededittext;

        public NewSpeedLimitDialog(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_speedlimit);
            done = (TextView) findViewById(R.id.done);
            newspeededittext = (EditText) findViewById(R.id.newspeededittext);
            done.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.done:
                    try {
                        if (newspeededittext.getText().toString().length() != 0 && newspeededittext.getText().toString().length() <= 3) {

                            try {
                                speedLimit = Integer.parseInt(newspeededittext.getText().toString());
                                LocationViewModel.speedLimit = Integer.parseInt(newspeededittext.getText().toString());
                                prefRepo.setSpeedLimit(Integer.parseInt(newspeededittext.getText().toString()));
                                binding.textAlarm.setText(newspeededittext.getText().toString() + prefRepo.getSpeedUnit());
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            dismiss();
                        } else if (newspeededittext.getText().toString().length() == 0 || newspeededittext.getText().toString().length() > 3) {
                            Toast.makeText(SpeedDashboardActivity.this, "Please enter a valid speed limit", Toast.LENGTH_SHORT).show();
                            newspeededittext.setText("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            // dismiss();
        }
    }
}