package com.innovidio.androidbootstrap.model;

/**
 * Created by MuhammadSalman on 12/6/2018.
 */

public class CarService
{
    private String maintenancename;
    private String maintenanceprice;
    private String maintenancelifetime;
  //  private String maintenancealerts;
    private int maintenancealerts;
    private int alarmid;
    private long alarmtimeinmillis;
    private boolean alarmon;
    private String maintenancetype;
    private String date;
    private long dateinmillis;

    public CarService()
    {

    }

        public CarService(String maintenancename, String maintenanceprice, String maintenancelifetime, /*String maintenancealerts*/ int maintenancealerts , int alarmid , long alarmtimeinmillis , String maintenancetype, String date, long dateinmillis) {
        this.maintenancename = maintenancename;
        this.maintenanceprice = maintenanceprice;
        this.maintenancelifetime = maintenancelifetime;
        this.maintenancealerts = maintenancealerts;
        this.alarmid = alarmid;
        this.alarmtimeinmillis = alarmtimeinmillis;
        this.maintenancetype = maintenancetype;
        this.date = date;
        this.dateinmillis = dateinmillis;
    }

    public String getmaintenancename() {
        return maintenancename;
    }

    public String getmaintenanceprice() {
        return maintenanceprice;
    }

    public String getmaintenancelifetime() {
        return maintenancelifetime;
    }

    public int getmaintenancealerts() {
        return maintenancealerts;
    }

    public int getAlarmid() {
        return alarmid;
    }

    public void setAlarmid(int alarmid) {
        this.alarmid = alarmid;
    }

    public long getAlarmtimeinmillis() {
        return alarmtimeinmillis;
    }

    public boolean isAlarmon() {
        return alarmon;
    }

    public void setAlarmon(boolean alarmon) {
        this.alarmon = alarmon;
    }

    public String getMaintenancetype() {
        return maintenancetype;
    }

    public String getDate() {
        return date;
    }

    public long getDateinmillis() {
        return dateinmillis;
    }
}
