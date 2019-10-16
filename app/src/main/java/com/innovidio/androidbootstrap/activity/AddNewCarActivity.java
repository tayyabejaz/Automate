package com.innovidio.androidbootstrap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ActivityAddNewCarBinding;
import com.innovidio.androidbootstrap.fragment.AddCustomCar;
import com.innovidio.androidbootstrap.fragment.AddNewCar;
import com.innovidio.androidbootstrap.interfaces.OnFragmentClickListener;

public class AddNewCarActivity extends AppCompatActivity implements OnFragmentClickListener {

    private ActivityAddNewCarBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_new_car);

        getSupportFragmentManager().beginTransaction().add(R.id.fl_newcar_fragment,new AddNewCar(this)).commit();
    }

    @Override
    public void onCustomFragmentClick() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_newcar_fragment,new AddCustomCar(this)).commit();
    }

    @Override
    public void onGoDefaultClick() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_newcar_fragment,new AddNewCar(this)).commit();
    }
}
