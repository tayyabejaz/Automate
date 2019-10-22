package com.innovidio.androidbootstrap.dashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentSender;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

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
import com.innovidio.androidbootstrap.Constants;

import java.util.Locale;

/**
 * Created by MuhammadSalman on 9/14/2018.
 */

public class DashboardViewModel extends AndroidViewModel implements LocationListener, GpsStatus.Listener {

    private static final String TAG = "SpeedDashboardActivity";

    private static final String CHANNEL_ID = "channelId";

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 1;

    public static String currentSpeed;

    GoogleApiClient googleApiClient;

    public static boolean ischronometerstarted = true;

    public static int speedLimit;

    Location lastlocation = new Location("last");

    double currentLon = 0;

    double currentLat = 0;

    double lastLon = 0;

    double lastLat = 0;

    boolean limitApproaching = false;

    DataManager data;

    private LocationManager mLocationManager;

    private final MutableLiveData<DataManager> locationMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<DataManager> getLocationMutableLiveData() {
        return locationMutableLiveData;
    }

    public MutableLiveData<String> getSpeedexceedMutableLiveData() {
        return speedexceedMutableLiveData;
    }

    public MutableLiveData<String> getSpeedApproachingMutableLiveData() {
        return speedApproachingMutableLiveData;
    }

    private final MutableLiveData<String> speedexceedMutableLiveData = new MutableLiveData<String>();
    private final MutableLiveData<String> speedApproachingMutableLiveData = new MutableLiveData<String>();

    @SuppressLint("MissingPermission")
    public DashboardViewModel(@NonNull Application application) {
        super(application);
        data = new DataManager();
        data.setFirstTime(true);


        mLocationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);

        try {
            if (mLocationManager.getAllProviders().indexOf(LocationManager.GPS_PROVIDER) >= 0) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, this);
            } else {
                Log.w("MainActivity", "No GPS location provider found. GPS data display will not be available.");
            }
            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                // Toast.makeText(application, "Please turn on the location", Toast.LENGTH_SHORT).show();
                showGpsDisabledDialog();
            }
            mLocationManager.addGpsStatusListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showGpsDisabledDialog() {


        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getApplication())
                    .addApi(LocationServices.API).addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) getApplication())
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) getApplication()).build();
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
                                status.startResolutionForResult((Activity) getApplication().getApplicationContext(), 1000);
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
    protected void onCleared() {
        super.onCleared();
        mLocationManager.removeUpdates(this);
        mLocationManager.removeGpsStatusListener(this);

    }


    @Override
    public void onLocationChanged(Location location) {

        /*if (!isTimeSet) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US); //dd-MM-yyyy
            Calendar calender = Calendar.getInstance();
            TimeZone ccme = calender.getTimeZone();
            timeFormat.setTimeZone(ccme);
            starttime = timeFormat.format(new Date());
            isTimeSet = true;
        }*/

        currentLat = location.getLatitude();
        currentLon = location.getLongitude();

        if (data.isFirstTime()) {
            lastLat = currentLat;
            lastLon = currentLon;
            data.setFirstTime(false);
        }

        lastlocation.setLatitude(lastLat);
        lastlocation.setLongitude(lastLon);
        double distance = location.distanceTo(lastlocation);

        if (location.getAccuracy() < distance) {
            data.addDistance(distance);

            lastLat = currentLat;
            lastLon = currentLon;
        }


        if (location.hasSpeed())
        {
//            if (SharedPreferenceHelper.getInstance().getStringValue(Constants.speedunits , "KM/hr").equals("KM/hr"))
//            {
            data.setCurSpeed(location.getSpeed() * 3.6);
            //  }
            /*else if (SharedPreferenceHelper.getInstance().getStringValue(Constants.speedunits , "KM/hr").equals("M/hr"))
            {
                data.setCurSpeed(location.getSpeed() * 3.6  * 0.62137119);
            }*/

            if (SharedPreferenceHelper.getInstance().getStringValue(Constants.speedunits, "KM/hr").equals("KM/hr")) {
                currentSpeed = String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6) /*+ "km/h"*/;
                data.setCurrentspeed(currentSpeed);
                data.setTotalAvgSpeed(currentSpeed);
            } else if (SharedPreferenceHelper.getInstance().getStringValue(Constants.speedunits, "KM/hr").equals("M/hr")) {
                currentSpeed = String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6 * 0.62137119)/* + "mi/h"*/;
                data.setCurrentspeed(currentSpeed);
                data.setTotalAvgSpeed(currentSpeed);

            } else {
                currentSpeed = String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6 * 0.5399568) /*+ "kn"*/;
                data.setCurrentspeed(currentSpeed);
                data.setTotalAvgSpeed(currentSpeed);

            }

            speedLimit = SharedPreferenceHelper.getInstance().getIntegerValue(Constants.SPEED_LIMIT, 0);
            Log.d("speedlimit" , ""+speedLimit);
            if (SharedPreferenceHelper.getInstance().getStringValue(Constants.SPEED_LIMIT_METER_TYPE, "km/h").equals("km/h")) {

                if (SharedPreferenceHelper.getInstance().getStringValue(Constants.METER_TYPE, "km/h").equals("km/h"))
                {

                } else if (SharedPreferenceHelper.getInstance().getStringValue(Constants.METER_TYPE, "km/h").equals("mph")) {
                    speedLimit *= 0.62137119;
                } else {
                    speedLimit *= 0.5399568;
                }
            } else if (SharedPreferenceHelper.getInstance().getStringValue(Constants.METER_TYPE, "km/h").equals("mph")) {
                if (SharedPreferenceHelper.getInstance().getStringValue(Constants.METER_TYPE, "km/h").equals("km/h")) {
                    speedLimit /= 0.62137119;
                } else if (SharedPreferenceHelper.getInstance().getStringValue(Constants.METER_TYPE, "km/h").equals("mph")) {

                } else {
                    speedLimit *= 0.868976;
                }

            } else {
                if (SharedPreferenceHelper.getInstance().getStringValue(Constants.METER_TYPE, "km/h").equals("km/h")) {
                    speedLimit /= 1.852;
                } else if (SharedPreferenceHelper.getInstance().getStringValue(Constants.METER_TYPE, "km/h").equals("mph")) {
                    speedLimit /= 1.15078;
                } else {

                }
            }


            try
            {
                if (currentSpeed!=null)
                {


                    if (Integer.parseInt(currentSpeed) > speedLimit && speedLimit != 0 )
                    {
                        speedexceedMutableLiveData.setValue(currentSpeed);
                    }
                }

            }

            catch (NumberFormatException e)
            {
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

                }
                break;
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                break;
        }
    }

}
