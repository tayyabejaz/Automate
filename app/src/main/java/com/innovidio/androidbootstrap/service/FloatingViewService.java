package com.innovidio.androidbootstrap.service;

/**
 * Created by Adnan Naeem on 7/30/2018.
 */

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.activity.SpeedDashboardActivity;
import com.innovidio.androidbootstrap.repository.PreferencesRepository;

import java.util.Locale;

import javax.inject.Inject;

import static com.innovidio.androidbootstrap.Constants.KM_HR;
import static com.innovidio.androidbootstrap.Constants.M_HR;

public class FloatingViewService extends Service {


    @Inject
    PreferencesRepository prefRepo;

    @Inject
    AppPreferences appPreferences;
    //Current Speed
    TextView currspeed;
    WindowManager.LayoutParams params;
    CustomLocationRunnable customLocationRunnable;
    Thread customLocationThread;
    String currentSpeed;
    // ImageView closeButton;
    TextView speedunits;
    ImageView openButton;
    private WindowManager mWindowManager;
    private View mFloatingView;

    public FloatingViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        EventBus.getDefault().register(this);
        //Inflate the floating view layout we created
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.item_minimize_layout, null);


        if (Build.VERSION.SDK_INT >= 26) {
            //Add the view to the window.
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        } else /*if (Build.VERSION.SDK_INT < 26)*/
        {
            //Add the view to the window.
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.START;        //Initially view will be added to top-left corner
        params.x = 100;
        params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        try {
            mWindowManager.addView(mFloatingView, params);
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //The root element of the collapsed view layout
        //  final View collapsedView = mFloatingView.findViewById(R.id.collapse_view);
        //The root element of the expanded view layout
        final View expandedView = mFloatingView.findViewById(R.id.expanded_container);

        currspeed = (TextView) mFloatingView.findViewById(R.id.current_speed);
        currspeed.setText("0");

        //Speed Units
        speedunits = (TextView) mFloatingView.findViewById(R.id.units);
       // if (appPreferences.getString(AppPreferences.Key.DISTANCE_UNIT))
        if (prefRepo.getSpeedUnit().equals(KM_HR)) {
            speedunits.setText("KMPH");
        } else if (prefRepo.getSpeedUnit().equals(M_HR)) {
            speedunits.setText("MPH");
        }

        openButton = (ImageView) mFloatingView.findViewById(R.id.open_app);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open the application  click.
                    stopSelf();
                    if (customLocationRunnable != null) {
                        customLocationRunnable = null;
                        customLocationThread.interrupt();
                    }
                    Intent intent = new Intent(getApplicationContext(), SpeedDashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    getApplicationContext().startActivity(intent);

            }
        });


      //  setTheme();

        //Drag and move floating view using user's touch action.
        mFloatingView.findViewById(R.id.expanded_container).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);

                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
//                        if (Xdiff < 10 && Ydiff < 10) {
//                            if (isViewCollapsed()) {
//                                //When user clicks on the image view of the collapsed layout,
//                                //visibility of the collapsed layout will be changed to "View.GONE"
//                                //and expanded view will become visible.
//                                //collapsedView.setVisibility(View.GONE);
//                                expandedView.setVisibility(View.VISIBLE);
//                            }
//                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);

                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            if (this.customLocationRunnable == null) {
                this.customLocationRunnable = new CustomLocationRunnable(this);
                this.customLocationThread = new Thread(this.customLocationRunnable);
                if (!this.customLocationThread.isAlive()) {
                    this.customLocationThread.start();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //  EventBus.getDefault().unregister(this);
        if (mFloatingView != null)
            mWindowManager.removeView(mFloatingView);
    }




    public void setFloatingWindowSSpeed(Location location) {
        if (prefRepo.getSpeedUnit().equals(KM_HR)) {
            //  Toast.makeText(getApplicationContext(), "In Kmph Speed", Toast.LENGTH_SHORT).show();
            currentSpeed = String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6) /*+ "km/h"*/;

        } else if (prefRepo.getSpeedUnit().equals(M_HR)) {
            currentSpeed = String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6 * 0.62137119)/* + "mi/h"*/;
            // EventBus.getDefault().post(new ServiceSpeedModel(currentSpeed));
        } else {
            currentSpeed = String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6 * 0.5399568) /*+ "kn"*/;
        }
        currspeed.setText(currentSpeed);
    }


}

