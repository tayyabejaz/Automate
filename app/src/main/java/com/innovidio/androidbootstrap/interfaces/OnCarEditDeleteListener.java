package com.innovidio.androidbootstrap.interfaces;

import com.innovidio.androidbootstrap.entity.Car;

public interface OnCarEditDeleteListener {

    void onEditClicked();
    void onDeleteClicked(Car car);
    void onDetailClicked(Car car);
}
