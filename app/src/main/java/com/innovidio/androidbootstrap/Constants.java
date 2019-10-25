package com.innovidio.androidbootstrap;

import android.content.Context;

public interface Constants {
    String URL_BASE_API = "https://www.carqueryapi.com";

    String ACTIVITY = "activity";

    String CAR_WASH_FORM = "carWash";
    String FUEL_UP_FORM = "fuelUp";
    String SERVICE_FORM = "service";
    String TRIP_FORM = "trip";

    String FILE_NAME = "file";

    String METER_UNIT = "meterUnit";
    String SPEED_LIMIT = "speedlimit";
    String SPEED_LIMIT_METER_TYPE = "speedlimitmetertype";


    // todo all constants added from speedo meter
    String userid = "userid";
    String id = "id";
    String name = "name";
    String email = "email";
    String onboardingdone = "false";
    String carnameselectedfortrip = "carnameselectedfortrip";
    String carfueleconomyperkm = "carnameselectedfortrip";
    String triptype = "triptype";
    String fuelunits = "fuelunits";
    String distanceunits = "distanceunits";
    String speedunits = "distanceunits";
    String perunitfuelprice = "perunitfuelprice";

  //  Context CONTEXT = null;
    int FILTERED_ADAPTER = 1;
    int SPINNER_ADPTER_TYPE = 1;
    String FILTER_FUEL_UPS = "filter_fuelup";
    String FILTER_MAINTENANCE = "filter_maintenance";
    String FILTER_TRIPS = "filter_trips";
    String FILTER_CARWASH = "filter_carwash";
    ///
    String KM_HR = "KM/hr";
    String M_HR ="M/hr";


    // awarness api constants
    String BROADCAST_DETECTED_ACTIVITY = "activity_intent";
    long DETECTION_INTERVAL_IN_MILLISECONDS = 30 * 1000;
    int CONFIDENCE = 70;

}

