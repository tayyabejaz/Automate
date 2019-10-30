package com.innovidio.androidbootstrap.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.entity.models.DataManager;
import com.innovidio.androidbootstrap.repository.PreferencesRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import static com.innovidio.androidbootstrap.Constants.KM_HR;
import static com.innovidio.androidbootstrap.Constants.M_HR;

public class LocationViewModel extends ViewModel implements LocationListener, GpsStatus.Listener {
    public static Double currentSpeed;
    private final Context context;

    @Inject
    AppPreferences appPreferences;

    @Inject
    PreferencesRepository prefRepo;

    private Location startLocation = new Location("start");
    Location lastlocation = new Location("last");
    double currentLon = 0;
    double currentLat = 0;
    double lastLon = 0;
    double lastLat = 0;
    boolean isTimeSet = false;
    public static Date startTime;
    public static int speedLimit;
    DataManager data;
    private LocationManager mLocationManager;

    private final MutableLiveData<DataManager> locationMutableLiveData = new MutableLiveData<DataManager>();
    private final MutableLiveData<String> speedexceedMutableLiveData = new MutableLiveData<String>();


    //  private boolean isTimerRunning = false;
    public MutableLiveData<String> getSpeedexceedMutableLiveData() {
        return speedexceedMutableLiveData;
    }

    @SuppressLint("MissingPermission")
    @Inject
    public LocationViewModel(Context context) {
this.context = context;
        data = new DataManager();
        data.setFirstTime(true);

        if (!isTimeSet) {
            startTime = Calendar.getInstance().getTime();
           // startTime = DateConverter.getTimeFormateForUS("hh:mm a", Locale.US);
            isTimeSet = true;
        }

        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            if (mLocationManager.getAllProviders().indexOf(LocationManager.GPS_PROVIDER) >= 0) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, (android.location.LocationListener) this);
            } else {
                Log.w("MainActivity", "No GPS location provider found. GPS data display will not be available.");
            }

            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                 Toast.makeText(context, "Please turn on the location", Toast.LENGTH_SHORT).show();
                // showGpsDisabledDialog();
            }
            mLocationManager.addGpsStatusListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<DataManager> getLocationMutableLiveData() {
        return locationMutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mLocationManager.removeUpdates((android.location.LocationListener) this);
        mLocationManager.removeGpsStatusListener(this);
    }

    public void setChronoTimerMutableLiveData(Long time) {
        data.setTime(time);
        locationMutableLiveData.setValue(data);
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLat = location.getLatitude();
        currentLon = location.getLongitude();

        if (data.isFirstTime()) {
            lastLat = currentLat;
            lastLon = currentLon;
            data.setFirstTime(false);
            // for first time
            this.startLocation.setLatitude(location.getLatitude());
            this.startLocation.setLongitude(location.getLongitude());
        }

        // last known location for trip end
        lastlocation.setLatitude(lastLat);
        lastlocation.setLongitude(lastLon);
        double distance = location.distanceTo(lastlocation);

        if (location.getAccuracy() < distance) {
            data.addDistance(distance);

            lastLat = currentLat;
            lastLon = currentLon;
        }


        if (location.hasSpeed()) {
            data.setCurSpeed((int) (location.getSpeed() * 3.6));

            if (prefRepo.getSpeedUnit().equals(KM_HR)) {
                currentSpeed = Double.parseDouble(String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6)) /*+ "km/h"*/;
                data.setCurrentSpeed(currentSpeed);
                data.setTotalAvgSpeed(currentSpeed+"");
            } else if (prefRepo.getSpeedUnit().equals(M_HR)) {
                currentSpeed = Double.parseDouble(String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6 * 0.62137119));
                data.setCurrentSpeed(currentSpeed);
                data.setTotalAvgSpeed(currentSpeed + "");

            } else {
                currentSpeed = Double.parseDouble(String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6 * 0.5399568));
                data.setCurrentSpeed(currentSpeed);
                data.setTotalAvgSpeed(currentSpeed + "");

            }

            speedLimit = appPreferences.getInt(AppPreferences.Key.SPEED_LIMIT, 70);
            try {
                if (currentSpeed != null) {


                    if (currentSpeed > speedLimit && speedLimit != 0) {
                        speedexceedMutableLiveData.setValue(currentSpeed+"");
                    }
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }

        locationMutableLiveData.setValue(data);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onGpsStatusChanged(int event) {
        switch (event) {
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    // showGpsDisabledDialog();
                }
                break;
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                break;
        }
    }

    public Location getInitialLocation(){
        return this.startLocation;
    }

    public Location getLastLocation(){
        return this.lastlocation;
    }
}
