package com.innovidio.androidbootstrap.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.innovidio.androidbootstrap.R;

public class IconProvider {
    private static ImageView fuelUpIcon, carWashIcon, tripRecordingIcon, speedometerIcon, servicesIcon, tripIcon;

    public static ImageView getFuelUp(Context context) {
        if (fuelUpIcon == null) {
            fuelUpIcon = new ImageView(context);
            fuelUpIcon.setImageResource(R.drawable.automate_add_fuelup_icon);
            fuelUpIcon.setBackground(context.getResources().getDrawable(R.drawable.bottom_sheet_background_fuel));
        }
        return fuelUpIcon;
    }

    public static ImageView getCarWash(Context context) {
        if (carWashIcon == null) {
            carWashIcon = new ImageView(context);
            carWashIcon.setImageResource(R.drawable.automate_add_car_wash);
            carWashIcon.setBackground(context.getResources().getDrawable(R.drawable.bottom_sheet_background_carwash));
        }
        return carWashIcon;
    }

    public static ImageView getTripRecording(Context context) {
        if (tripRecordingIcon == null) {
            tripRecordingIcon = new ImageView(context);
            tripRecordingIcon.setImageResource(R.drawable.automate_trip_rec);
            tripRecordingIcon.setBackground(context.getResources().getDrawable(R.drawable.bottom_sheet_background_triprecord));
        }
        return tripRecordingIcon;
    }

    public static ImageView getSpeedometer(Context context) {
        if (speedometerIcon == null) {
            speedometerIcon = new ImageView(context);
            speedometerIcon.setImageResource(R.drawable.automate_speedometer);
            speedometerIcon.setBackground(context.getResources().getDrawable(R.drawable.bottom_sheet_background_speedometer));
        }
        return speedometerIcon;
    }

    public static ImageView getServices(Context context) {
        if (servicesIcon == null) {
            servicesIcon = new ImageView(context);
            servicesIcon.setImageResource(R.drawable.automate_add_service_icon);
            servicesIcon.setBackground(context.getResources().getDrawable(R.drawable.bottom_sheet_background_service));
        }
        return servicesIcon;
    }

    public static ImageView getTrip(Context context) {
        if (tripIcon == null) {
            tripIcon = new ImageView(context);
            tripIcon.setImageResource(R.drawable.automate_add_trip_icon);
            tripIcon.setBackground(context.getResources().getDrawable(R.drawable.bottom_sheet_background_trip));
        }
        return tripIcon;
    }
}
