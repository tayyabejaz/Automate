package com.innovidio.androidbootstrap.dashboard;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddressClass {

    public static FullAddress getAddressFromLatLon(Context context, double latitude, double longitude){
        Geocoder geocoder;
        FullAddress fullAddress = new FullAddress();
        List<Address> addresses = null;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            fullAddress.setAddress(address);
            fullAddress.setCity(city);
            fullAddress.setState(state);
            fullAddress.setCountry(country);
            fullAddress.setPostalCode(postalCode);
            fullAddress.setKnownName(knownName);
            return fullAddress;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
