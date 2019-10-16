package com.innovidio.androidbootstrap.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.FragmentAddNewCarBinding;
import com.innovidio.androidbootstrap.interfaces.OnFragmentClickListener;

public class AddNewCar extends Fragment {

    private OnFragmentClickListener fragmentClickListener;
    private FragmentAddNewCarBinding binding;

    public AddNewCar(OnFragmentClickListener listener) {
        // Required empty public constructor
        this.fragmentClickListener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_new_car, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCustomCar.setOnClickListener(view1 -> {
            fragmentClickListener.onCustomFragmentClick();
        });
    }
}
