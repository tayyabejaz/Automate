package com.innovidio.androidbootstrap.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.FragmentAddCustomCarBinding;
import com.innovidio.androidbootstrap.interfaces.OnActivityBtnClickListener;
import com.innovidio.androidbootstrap.interfaces.OnFragmentClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCustomCar extends Fragment implements OnActivityBtnClickListener {

    private OnFragmentClickListener listener;
    private FragmentAddCustomCarBinding binding;

    public AddCustomCar(OnFragmentClickListener clickListener) {
        // Required empty public constructor
        this.listener = clickListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_custom_car, container, false);
        View view = binding.getRoot();

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCustomCar.setOnClickListener(view1 -> {
            listener.onGoDefaultClick();
        });
    }

    @Override
    public void onSubmitButtonClick(Context context) {
        Toast.makeText(context, "Custom Car fragment", Toast.LENGTH_SHORT).show();
    }
}
