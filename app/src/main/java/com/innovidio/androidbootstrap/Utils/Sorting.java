package com.innovidio.androidbootstrap.Utils;

import android.os.Build;

import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sorting {

    public static List<TimeLineItem>  sortList(List<TimeLineItem> timeLineItemList, Type type){

        switch (type) {
            case ASC:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //reference  https://stackoverflow.com/questions/25172595/comparator-reversed-does-not-compile-using-lambda
                    timeLineItemList.sort(Comparator.comparing(o -> o.getInsertDateTime()));
                } else {
                    Collections.sort(timeLineItemList, new Comparator<TimeLineItem>() {
                        public int compare(TimeLineItem obj1, TimeLineItem obj2) {
                            return obj1.getInsertDateTime().compareTo(obj2.getInsertDateTime());
                        }
                    });
                }
                break;

            case DSC:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //reference  https://stackoverflow.com/questions/25172595/comparator-reversed-does-not-compile-using-lambda
                    timeLineItemList.sort(Comparator.comparing((TimeLineItem o) -> o.getInsertDateTime()).reversed());
                } else {
                    Collections.sort(timeLineItemList, new Comparator<TimeLineItem>() {
                        public int compare(TimeLineItem obj1, TimeLineItem obj2) {
                            return obj2.getInsertDateTime().compareTo(obj1.getInsertDateTime());
                        }
                    });
                }
                break;
        }

        return timeLineItemList;
    }

    public enum Type {
        ASC, DSC;
    }

}
