package com.innovidio.androidbootstrap.interfaces;

import com.innovidio.androidbootstrap.entity.Alarm;

public interface OnAlarmCardListener {

    void onDeleteClicked(Alarm alarm);
    void onEditClicked(Alarm alarm);
    void onAlarmOnOffButton(Alarm alarm);
}
