package com.innovidio.androidbootstrap.entity.models;

import com.innovidio.androidbootstrap.R;

public class SpinnerDataModel {

    int mFlag;
    String mName;


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public SpinnerDataModel(String mName) {
        this.mFlag = R.drawable.automate_select_car_placeholder;
        this.mName = mName;
    }
}