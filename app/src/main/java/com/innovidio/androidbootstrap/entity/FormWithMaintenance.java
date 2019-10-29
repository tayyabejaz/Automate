package com.innovidio.androidbootstrap.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class FormWithMaintenance {
    @Embedded
    public Form form;
    @Relation(parentColumn = "id", entityColumn = "formId", entity = Maintenance.class)
    public List<Maintenance> maintenanceList;

    public List<Maintenance> getMaintenanceList() {
        return maintenanceList;
    }

    public void setMaintenanceList(List<Maintenance> maintenanceList) {
        this.maintenanceList = maintenanceList;
    }
}