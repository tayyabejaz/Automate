package com.innovidio.androidbootstrap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.innovidio.androidbootstrap.R;

import java.util.ArrayList;

public class ServiceAdapter extends ArrayAdapter {

    public ServiceAdapter(Context context, String[] data) {
        super(context, 0, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_general_spinner, parent, false);

        }

        TextView title = convertView.findViewById(R.id.tv_spinner_item_title);

        String item = (String) getItem(position);

        if (item != null) {
            title.setText(item);
        }
        return convertView;
    }

}
