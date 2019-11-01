package com.innovidio.androidbootstrap.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.IconProvider;
import com.innovidio.androidbootstrap.databinding.ItemFooterTimelineBinding;
import com.innovidio.androidbootstrap.databinding.ItemTimelineCarWashBinding;
import com.innovidio.androidbootstrap.databinding.ItemTimelineFuelUpBinding;
import com.innovidio.androidbootstrap.databinding.ItemTimelineMaintenanceBinding;
import com.innovidio.androidbootstrap.databinding.ItemTimelineTripsBinding;
import com.innovidio.androidbootstrap.entity.FormWithMaintenance;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.MaintenanceItemClickListener;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.interfaces.TimelineItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<FormWithMaintenance> timeLineItemList = new ArrayList<>();
    private int adapterType = 0;
    private MaintenanceItemClickListener maintenanceItemClickListener;


    public MaintenanceAdapter(Context context, MaintenanceItemClickListener listener, List<FormWithMaintenance> dataList, int adapterType) {
        this.context = context;
        this.timeLineItemList = dataList;
        this.maintenanceItemClickListener = listener;
        this.adapterType = adapterType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_maintenance,
                parent, false);
        return new MaintenanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {

        FormWithMaintenance formWithMaintenance = timeLineItemList.get(position);
        List<Maintenance> maintenanceList = formWithMaintenance.maintenanceList;
        MaintenanceViewHolder maintenanceViewHolder = (MaintenanceViewHolder) holderParent;
        maintenanceViewHolder.bind(maintenanceList.get(0));

        if (maintenanceItemClickListener != null) {
            maintenanceViewHolder.itemView.setOnClickListener(view -> {
                maintenanceItemClickListener.onMaintenanceClick(formWithMaintenance);
                Toast.makeText(context, "yes it workked" , Toast.LENGTH_SHORT).show();
            });
        }
        maintenanceViewHolder.itemBinding.imageIcon.setBackground(IconProvider.getServices(context).getBackground());
        maintenanceViewHolder.itemBinding.imageIcon.setImageDrawable(IconProvider.getServices(context).getDrawable());
    }

    @Override
    public int getItemCount() {
        return timeLineItemList.size();
    }

    public void updateData(List<FormWithMaintenance> updatedList) {
        this.timeLineItemList = updatedList;
        notifyDataSetChanged();
    }

    class MaintenanceViewHolder extends RecyclerView.ViewHolder {

        private final ItemTimelineMaintenanceBinding itemBinding;

        public MaintenanceViewHolder(View itemView) {
            super(itemView);
            this.itemBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(Maintenance item) {
            itemBinding.setTimeLineMaintenanceItem(item);
            itemBinding.executePendingBindings();
        }
    }

}
