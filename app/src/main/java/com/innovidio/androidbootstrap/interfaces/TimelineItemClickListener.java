package com.innovidio.androidbootstrap.interfaces;

import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;

public interface TimelineItemClickListener {
    void onFuelUpClick(FuelUp fuelUp);

    void onCarWashClick(Maintenance maintenance);

    void onMaintenanceClick(Maintenance maintenance);

    void onTripsClick(Trip trip);
}
