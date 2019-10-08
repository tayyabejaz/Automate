package com.innovidio.androidbootstrap.interfaces;

import java.util.Date;

public interface TimeLineItem {
    Date getInsertDateTime();

    Type getType();


    public enum Type {
        FEUL, MAINTENANCE,TRIP
    }

}
