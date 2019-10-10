package com.innovidio.androidbootstrap.Utils;

import android.os.Build;

import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sorting {

    public static List<TimeLineItem>  SortList(List<TimeLineItem> timeLineItemList){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            timeLineItemList.sort(Comparator.comparing(o -> o.getInsertDateTime()));
        } else {
            Collections.sort(timeLineItemList, new Comparator<TimeLineItem>() {
                public int compare(TimeLineItem obj1, TimeLineItem obj2) {
                    return obj1.getInsertDateTime().compareTo(obj2.getInsertDateTime());
                }
            });
        }

        return timeLineItemList;
    }
}
