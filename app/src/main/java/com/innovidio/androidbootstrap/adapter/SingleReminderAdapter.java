package com.innovidio.androidbootstrap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ItemSingleAlarmCustomBinding;
import com.innovidio.androidbootstrap.databinding.ItemSingleAlarmMaintenanceBinding;
import com.innovidio.androidbootstrap.entity.Alarm;
import com.innovidio.androidbootstrap.interfaces.OnAlarmCardListener;

import java.util.ArrayList;
import java.util.List;

public class SingleReminderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Alarm> dataList = new ArrayList<>();
    private OnAlarmCardListener listener;
    private static final int CUSTOM_ALARM = 0;
    private static final int MAINTENANCE_ALARM = 1;

    public SingleReminderAdapter() {
    }

    public SingleReminderAdapter(Context context, OnAlarmCardListener onCarEditDeleteListener, List<Alarm> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.listener = onCarEditDeleteListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        switch (viewType) {
            case CUSTOM_ALARM:
                view = LayoutInflater.from(context).inflate(R.layout.item_single_alarm_custom, parent, false);
                return new CustomAlarmViewHolder(view);

            case MAINTENANCE_ALARM:
                view = LayoutInflater.from(context).inflate(R.layout.item_single_alarm_maintenance, parent, false);
                return new MaintenanceAlarmViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder parentHolder, int position) {
        Alarm alarm = dataList.get(position);
        if(alarm.getAlarmType() == Alarm.AlarmType.MAINTENANCE){

            MaintenanceAlarmViewHolder singleMaintenanceReminderViewHolder = (MaintenanceAlarmViewHolder) parentHolder;
           singleMaintenanceReminderViewHolder.bindMaintenanceAlarm(alarm);

        }
        else if(alarm.getAlarmType() == Alarm.AlarmType.CUSTOM){
            CustomAlarmViewHolder singleMaintenanceReminderViewHolder = (CustomAlarmViewHolder) parentHolder;
            singleMaintenanceReminderViewHolder.bindCustomAlarm(alarm);
        }
        parentHolder.itemView.findViewById(R.id.single_item_edit_icon).setOnClickListener(view -> {
            listener.onEditClicked(alarm);
        });

        parentHolder.itemView.findViewById(R.id.singleitem_delete_icon).setOnClickListener(view -> {
            listener.onDeleteClicked(alarm);
        });

        parentHolder.itemView.findViewById(R.id.iv_alarm_on).setOnClickListener(view -> {
            listener.onAlarmOnOffButton(alarm);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.size() > 0) {
            switch (dataList.get(position).getAlarmType()) {
                case CUSTOM:
                    return CUSTOM_ALARM;
                case MAINTENANCE:
                    return MAINTENANCE_ALARM;
            }
        }
        return super.getItemViewType(position);
    }

    class CustomAlarmViewHolder extends RecyclerView.ViewHolder {

        private ItemSingleAlarmCustomBinding binding;

        private CustomAlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = DataBindingUtil.bind(itemView);
        }

        private void bindCustomAlarm(Alarm alarm) {
            binding.setItemCustomAlarm(alarm);
            binding.executePendingBindings();
        }
    }

    class MaintenanceAlarmViewHolder extends RecyclerView.ViewHolder {
        private ItemSingleAlarmMaintenanceBinding binding;

        public MaintenanceAlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = DataBindingUtil.bind(itemView);
        }

        public void bindMaintenanceAlarm(Alarm alarm) {
            binding.setItemSingleMaintenance(alarm);
            binding.executePendingBindings();
        }
    }

    public void updateList(List<Alarm> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
