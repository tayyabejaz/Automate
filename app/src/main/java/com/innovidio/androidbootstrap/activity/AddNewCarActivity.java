package com.innovidio.androidbootstrap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ActivityAddNewCarBinding;
import com.innovidio.androidbootstrap.fragment.AddCustomCar;
import com.innovidio.androidbootstrap.fragment.AddNewCar;
import com.innovidio.androidbootstrap.interfaces.OnActivityBtnClickListener;
import com.innovidio.androidbootstrap.interfaces.OnFragmentClickListener;

public class AddNewCarActivity extends AppCompatActivity implements OnFragmentClickListener {

    OnActivityBtnClickListener onActivityBtnClickListener;
    private ActivityAddNewCarBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_new_car);

        AddNewCar addNewCar = new AddNewCar(this);
        onActivityBtnClickListener=(OnActivityBtnClickListener) addNewCar;
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
        onActivityBtnClickListener=(OnActivityBtnClickListener) addCustomCar;
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_newcar_fragment,addCustomCar).commit();
    }

    @Override
    public void onGoDefaultClick() {
        AddNewCar addNewCar = new AddNewCar(this);
        onActivityBtnClickListener=(OnActivityBtnClickListener) addNewCar;
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_newcar_fragment,new AddNewCar(this)).commit();
    }
}
