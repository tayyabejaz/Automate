package com.innovidio.androidbootstrap.dashboard;

import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ActivitySetSpeedLimitBinding;

import static com.innovidio.androidbootstrap.Constants.KM_HR;

public class SetSpeedLimit extends AppCompatActivity implements View.OnClickListener {

    ActivitySetSpeedLimitBinding binding;
    public static boolean speedalarmon = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_speed_limit);
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_speed_limit);

        SharedPreferenceHelper.getInstance().setStringValue(Constants.METER_UNIT, KM_HR);
        SharedPreferenceHelper.getInstance().setStringValue(Constants.SPEED_LIMIT_METER_TYPE, KM_HR);

        binding.start.setOnClickListener(this);
        binding.skip.setOnClickListener(this);


        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_speedlimit);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if (binding.switchSpeedalarm.isChecked())
                {
                    if (binding.speedlimitEdittext.getText().toString().length()==0 || binding.speedlimitEdittext.getText().toString().equals("0") || binding.speedlimitEdittext.getText().toString().length()>3)
                    {
                        Toast.makeText(SetSpeedLimit.this, "Please enter valid speed limit", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        speedalarmon = true;
                       // Toast.makeText(SetSpeedLimit.this, "Speed Limit is valid", Toast.LENGTH_SHORT).show();
                        SharedPreferenceHelper.getInstance().setIntegerValue(Constants.SPEED_LIMIT , Integer.parseInt(binding.speedlimitEdittext.getText().toString()));
                        Intent i = new Intent(SetSpeedLimit.this, SpeedDashboardActivity.class);
                        startActivity(i);
                        finish();
                    }

                }
                else
                {
                    if (binding.speedlimitEdittext.getText().toString().length()==0 || binding.speedlimitEdittext.getText().toString().equals("0") || binding.speedlimitEdittext.getText().toString().length()>3)
                    {
                        Toast.makeText(SetSpeedLimit.this, "Please enter valid speed limit", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        speedalarmon = false;
                       // Toast.makeText(SetSpeedLimit.this, "Speed Limit is valid", Toast.LENGTH_SHORT).show();
                        SharedPreferenceHelper.getInstance().setIntegerValue(Constants.SPEED_LIMIT , Integer.parseInt(binding.speedlimitEdittext.getText().toString()));
                        Intent i = new Intent(SetSpeedLimit.this, SpeedDashboardActivity.class);
                        startActivity(i);
                        finish();
                    }
                }

                break;

            case R.id.skip:
                Intent i = new Intent(SetSpeedLimit.this , SpeedDashboardActivity.class);
                startActivity(i);
                finish();
                SharedPreferenceHelper.getInstance().setIntegerValue(Constants.SPEED_LIMIT , 0);
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
