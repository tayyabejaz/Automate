package com.innovidio.androidbootstrap.interfaces;

import java.util.Date;

public interface TimeLineItem {
    Date getInsertDateTime();

    Type getType();


    public enum Type {
        BOXES, FUEL, MAINTENANCE,TRIP, CAR_WASH, FOOTER;
    }

}
