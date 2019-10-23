package com.innovidio.androidbootstrap.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.dashboard.SetSpeedLimit;
import com.innovidio.androidbootstrap.databinding.FragmentDriveBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDrive extends Fragment  {




    public FragmentDrive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drive, container, false);
    }



    private void startDrive(){
        Intent i = new Intent(getContext(), SetSpeedLimit.class);
        startActivity(i);
    }


}
