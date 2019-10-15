package com.innovidio.androidbootstrap.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.IconProvider;
import com.innovidio.androidbootstrap.databinding.MainDashboardRecyclerItemBinding;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.entity.models.TimeLine;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.ArrayList;
import java.util.List;


public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewholder> {

    private Context context;
    private List<TimeLineItem> dataList = new ArrayList<>();


    public TimelineAdapter(Context context, List<TimeLineItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public TimelineViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MainDashboardRecyclerItemBinding itemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.main_dashboard_recycler_item, parent, false);
        return new TimelineViewholder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewholder holder, int position) {

        TimeLineItem item = dataList.get(position);
//        holder.bind(item);
        TimeLine timeLine = new TimeLine();
        switch (item.getType()) {
            case FUEL:
                holder.itemBinding.imageIcon.setBackground(IconProvider.getFuelUp(context).getBackground());
                holder.itemBinding.imageIcon.setImageDrawable(IconProvider.getFuelUp(context).getDrawable());
                FuelUp fuelUp = (FuelUp) item;
                timeLine.setId(fuelUp.getId());
                timeLine.setSaveDate(fuelUp.getSaveDate());
                timeLine.setLocation(fuelUp.getLocation());
                timeLine.setMeterCurrentValue(fuelUp.getOdometerreading() + "");
                timeLine.setTotalPrice(fuelUp.getTotalprice());
//                Log.d(TAG, "timeLine: FuelUp: " + fuelUp.getCarname());
                break;

            case MAINTENANCE:
                holder.itemBinding.imageIcon.setBackground(IconProvider.getServices(context).getBackground());
                holder.itemBinding.imageIcon.setImageDrawable(IconProvider.getServices(context).getDrawable());
                Maintenance maintenance = (Maintenance) item;
                timeLine.setId(maintenance.getId());
                timeLine.setSaveDate(maintenance.getSaveDate());
                timeLine.setLocation(maintenance.getMaintenanceLocation());
                timeLine.setMeterCurrentValue(maintenance.getMaintenanceName());
                timeLine.setTotalPrice(maintenance.getMaintenanceCost());
//                Log.d(TAG, "timeLine: Maintenance: " + maintenance.getMaintenanceName());
                break;

            case TRIP:
                holder.itemBinding.imageIcon.setBackground(IconProvider.getTrip(context).getBackground());
                holder.itemBinding.imageIcon.setImageDrawable(IconProvider.getTrip(context).getDrawable());
                Trip trip = (Trip) item;
                timeLine.setId(trip.getId());
                timeLine.setSaveDate(trip.getSaveDate());
                timeLine.setLocation(trip.getDestination());
                timeLine.setMeterCurrentValue(trip.getDistanceCovered() + "");
                timeLine.setTotalPrice(trip.getFueleconomypertrip());
//                Log.d(TAG, "timeLine: Trip: " + trip.getTripTitle());
                break;
        }
        holder.bind(timeLine);


//        if(position == dataList.size()-1){
//            Toast.makeText(context,"This is position:" + position, Toast.LENGTH_LONG).show();
//        }

    }

    @Override
    public int getItemCount() {
        Log.e("timeLine", "arrayListSize" + dataList.size());
        return dataList.size();
    }

    class TimelineViewholder extends RecyclerView.ViewHolder {

        private final MainDashboardRecyclerItemBinding itemBinding;

        public TimelineViewholder(MainDashboardRecyclerItemBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
        }

        public void bind(TimeLine item) {
            itemBinding.setTimeLineItem(item);
            itemBinding.executePendingBindings();
        }
    }

    public void updateData(List<TimeLineItem> updatedList) {
        this.dataList = updatedList;
        notifyDataSetChanged();
    }
}
