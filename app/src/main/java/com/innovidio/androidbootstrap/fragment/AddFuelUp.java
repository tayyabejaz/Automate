package com.innovidio.androidbootstrap.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovidio.androidbootstrap.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFuelUp extends Fragment {


    public AddFuelUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_fuel_up, container, false);
    }

}
