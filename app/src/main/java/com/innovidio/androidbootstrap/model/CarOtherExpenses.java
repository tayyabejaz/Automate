package com.innovidio.androidbootstrap.model;

/**
 * Created by MuhammadSalman on 1/23/2019.
 */

public class CarOtherExpenses {

    private String expensename;
    private String expenseprice;
    private String expenselifetime;
    private int lifetimealert;
    private int alarmid;
    private long alarmtimeinmillis;
    private boolean alarmon;

    public CarOtherExpenses(String expensename, String expenseprice, String expenselifetime, int lifetimealert, int alarmid, long alarmtimeinmillis) {
        this.expensename = expensename;
        this.expenseprice = expenseprice;
        this.expenselifetime = expenselifetime;
        this.lifetimealert = lifetimealert;
        this.alarmid = alarmid;
        this.alarmtimeinmillis = alarmtimeinmillis;
    }

    public CarOtherExpenses()
    {

    }

    public String getExpensename() {
        return expensename;
    }

    public String getExpenseprice() {
        return expenseprice;
    }

    public String getExpenselifetime() {
        return expenselifetime;
    }

    public int getLifetimealert() {
        return lifetimealert;
    }

    public int getAlarmid() {
        return alarmid;
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

}
