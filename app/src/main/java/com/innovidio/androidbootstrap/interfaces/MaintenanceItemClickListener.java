package com.innovidio.androidbootstrap.interfaces;

import com.innovidio.androidbootstrap.entity.FormWithMaintenance;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;

import java.util.List;

public interface MaintenanceItemClickListener {

    void onMaintenanceClick(FormWithMaintenance formWithMaintenance);
}
