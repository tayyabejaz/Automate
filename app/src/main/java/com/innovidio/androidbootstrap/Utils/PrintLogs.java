package com.innovidio.androidbootstrap.Utils;

import android.util.Log;

import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

public class PrintLogs {

    private static final String TAG = "PrintLogs";
    public static void printTimelineObjects(TimeLineItem item){
        switch (item.getType()) {
            case FUEL:
                FuelUp fuelUp = (FuelUp) item;
                Log.d(TAG, "timeLine: FuelUp: " + fuelUp.getSaveDateInString());
                break;

            case MAINTENANCE:
                Maintenance maintenance = (Maintenance) item;
                Log.d(TAG, "timeLine: Maintenance: " + maintenance.getMaintenanceName());
                Log.d(TAG, "timeLine: Maintenance: " + maintenance.getSaveDateInString());
                break;

            case TRIP:
                Trip trip = (Trip) item;
                Log.d(TAG, "timeLine: Trip: " + trip.getTripTitle());
                Log.d(TAG, "timeLine: Trip: " + trip.getSaveDateInString());
                break;
        }
    }
}
