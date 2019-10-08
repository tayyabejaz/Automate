package com.innovidio.androidbootstrap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.entity.models.SpinnerDataModel;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<SpinnerDataModel> {


    public SpinnerAdapter(@NonNull Context context, ArrayList<SpinnerDataModel> datalist) {
        super(context, 0, datalist);

    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_row, parent, false);
        }

        ImageView spinner_image = convertView.findViewById(R.id.ivFlag);
        TextView spinner_text = convertView.findViewById(R.id.tvName);

        SpinnerDataModel item = getItem(position);

        if (item != null) {
            spinner_image.setImageResource(R.drawable.automate_select_car_placeholder);
            spinner_text.setText(item.getmName());
        }
        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return initView(position, convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
}
