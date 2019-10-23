package com.innovidio.androidbootstrap.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
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

    FragmentDriveBinding binding;



    public FragmentDrive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_drive, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_drive, container, false);
        View v = binding.getRoot();
       // binding.setFirstFragmentData(this);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvGoButton.setOnClickListener(v->{
            startDrive();
        });
    }



    private void startDrive(){
        Intent i = new Intent(getContext(), SetSpeedLimit.class);
        startActivity(i);
    }


}
