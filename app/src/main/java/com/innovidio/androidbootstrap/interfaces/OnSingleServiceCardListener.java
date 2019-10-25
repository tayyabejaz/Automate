package com.innovidio.androidbootstrap.interfaces;

import com.innovidio.androidbootstrap.entity.Maintenance;

public interface OnSingleServiceCardListener {
    void onEditClick(Maintenance maintenance);
    void onDeleteClick(Maintenance maintenance);
}
