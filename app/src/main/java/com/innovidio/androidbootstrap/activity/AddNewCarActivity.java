package com.innovidio.androidbootstrap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ActivityAddNewCarBinding;
import com.innovidio.androidbootstrap.fragment.AddCustomCar;
import com.innovidio.androidbootstrap.fragment.AddNewCar;
import com.innovidio.androidbootstrap.interfaces.ActivityBtnClickListener;
import com.innovidio.androidbootstrap.interfaces.FragmentClickListener;

public class AddNewCarActivity extends AppCompatActivity implements FragmentClickListener {

    ActivityBtnClickListener onActivityBtnClickListener;
    private ActivityAddNewCarBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_new_car);

        AddNewCar addNewCar = new AddNewCar(this);
        onActivityBtnClickListener=(ActivityBtnClickListener) addNewCar;
        getSupportFragmentManager().beginTransaction().add(R.id.fl_newcar_fragment,addNewCar).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        binding.ivNewCarBack.setOnClickListener(view -> {
            finish();
        });

        binding.ivAddNewCarDone.setOnClickListener(view->{
            onActivityBtnClickListener.onSubmitButtonClick(this);
        });
    }

    @Override
    public void onCustomFragmentClick() {
        AddCustomCar addCustomCar = new AddCustomCar(this);
        onActivityBtnClickListener=(ActivityBtnClickListener) addCustomCar;
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_newcar_fragment,addCustomCar).commit();
    }

    @Override
    public void onGoDefaultClick() {
        AddNewCar addNewCar = new AddNewCar(this);
        onActivityBtnClickListener=(ActivityBtnClickListener) addNewCar;
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_newcar_fragment,new AddNewCar(this)).commit();
    }
}
