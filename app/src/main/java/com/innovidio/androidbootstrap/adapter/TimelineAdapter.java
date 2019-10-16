package com.innovidio.androidbootstrap.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.IconProvider;
import com.innovidio.androidbootstrap.databinding.ItemBoxesMainFragmentBinding;
import com.innovidio.androidbootstrap.databinding.ItemTimelineCarWashBinding;
import com.innovidio.androidbootstrap.databinding.ItemTimelineFuelUpBinding;
import com.innovidio.androidbootstrap.databinding.ItemTimelineMaintenanceBinding;
import com.innovidio.androidbootstrap.databinding.ItemTimelineTripsBinding;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.ArrayList;
import java.util.List;


public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<? extends TimeLineItem> timeLineItemList = new ArrayList<>();

    private static final int BOXES = 0;
    private static final int FUEL_UP = 1;
    private static final int TRIPS =  2;
    private static final int MAINTENANCE =  3;
    private static final int CAR_WASH =  4;
    private static final int FOOTER =  5;


    public TimelineAdapter(Context context, List<? extends TimeLineItem> dataList) {
        this.context = context;
        this.timeLineItemList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        switch (viewType){
            case BOXES:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_boxes_main_fragment,
                        parent, false);
                return new BoxesViewHolder(view);
            case FUEL_UP:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_fuel_up,
                        parent, false);
                return new FuelUpItemViewHolder(view);
            case MAINTENANCE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_maintenance,
                        parent, false);
                return new MaintenanceViewHolder(view);
            case CAR_WASH:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_car_wash,
                        parent, false);
                return new MaintenanceViewHolder(view);
            case TRIPS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_trips,
                        parent, false);
                return new TripsViewHolder(view);

            case FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_trips,
                        parent, false);
                return new TripsViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {

        TimeLineItem item = timeLineItemList.get(position);
        switch (item.getType()) {
            case BOXES:
                // todo task about boxes data
                FuelUp fuelUpDummy = (FuelUp) item;
//                FuelUpItemViewHolder fuelUpItemViewHolder = (FuelUpItemViewHolder) holderParent;
//                fuelUpItemViewHolder.bind(fuelUp);

                break;
            case FUEL:
                FuelUp fuelUp = (FuelUp) item;
                FuelUpItemViewHolder fuelUpItemViewHolder = (FuelUpItemViewHolder) holderParent;
                fuelUpItemViewHolder.bind(fuelUp);

                fuelUpItemViewHolder.itemBinding.imageIcon.setBackground(IconProvider.getFuelUp(context).getBackground());
                fuelUpItemViewHolder.itemBinding.imageIcon.setImageDrawable(IconProvider.getFuelUp(context).getDrawable());

                break;

            case MAINTENANCE:
                Maintenance maintenance = (Maintenance) item;
                MaintenanceViewHolder maintenanceViewHolder = (MaintenanceViewHolder) holderParent;
                maintenanceViewHolder.bind(maintenance);

                maintenanceViewHolder.itemBinding.imageIcon.setBackground(IconProvider.getServices(context).getBackground());
                maintenanceViewHolder.itemBinding.imageIcon.setImageDrawable(IconProvider.getServices(context).getDrawable());

                break;


            case CAR_WASH:
                Maintenance maintenanceCarWash = (Maintenance) item;
                MaintenanceCarWashViewHolder maintenanceCarWashViewHolder = (MaintenanceCarWashViewHolder) holderParent;
                maintenanceCarWashViewHolder.bind(maintenanceCarWash);

                maintenanceCarWashViewHolder.itemBinding.imageIcon.setBackground(IconProvider.getServices(context).getBackground());
                maintenanceCarWashViewHolder.itemBinding.imageIcon.setImageDrawable(IconProvider.getServices(context).getDrawable());

                break;

            case TRIP:
                Trip trip = (Trip) item;
                TripsViewHolder tripsViewHolder = (TripsViewHolder) holderParent;
                tripsViewHolder.bind(trip);

                tripsViewHolder.itemBinding.imageIcon.setBackground(IconProvider.getTrip(context).getBackground());
                tripsViewHolder.itemBinding.imageIcon.setImageDrawable(IconProvider.getTrip(context).getDrawable());
                break;
        }

//        if(position == timeLineItemList.size()-1){
//            holderParent.itemBinding.ivRvItemCircle.setVisibility(View.VISIBLE);
//        }
//        else{
//            holder.itemBinding.ivRvItemCircle.setVisibility(View.GONE);
//        }

    }

    @Override
    public int getItemCount() {
        Log.e("timeLine", "arrayListSize" + timeLineItemList.size());
        return timeLineItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (timeLineItemList.get(position).getType()) {
            case BOXES:
                return BOXES;
            case FUEL:
                return FUEL_UP;
            case TRIP:
                return TRIPS;
            case MAINTENANCE:
                return MAINTENANCE;
            case CAR_WASH:
                return CAR_WASH;
            case FOOTER:
                return FOOTER;
        }
        return 0;
    }

    public void updateData(List<TimeLineItem> updatedList) {
        this.timeLineItemList = updatedList;
        notifyDataSetChanged();
    }

    private void addHeader(){

    }

    class MaintenanceViewHolder extends RecyclerView.ViewHolder {

        private final ItemTimelineMaintenanceBinding itemBinding;

        public MaintenanceViewHolder(View itemView) {
            super(itemView);
            this.itemBinding = DataBindingUtil.bind(itemView);;
        }

        public void bind(Maintenance item) {
            itemBinding.setTimeLineMaintenanceItem(item);
            itemBinding.executePendingBindings();
        }
    }


    class MaintenanceCarWashViewHolder extends RecyclerView.ViewHolder {

        private final ItemTimelineCarWashBinding itemBinding;

        public MaintenanceCarWashViewHolder(View itemView) {
            super(itemView);
            this.itemBinding = DataBindingUtil.bind(itemView);;
        }

        public void bind(Maintenance item) {
            itemBinding.setTimeLineCardWashItem(item);
            itemBinding.executePendingBindings();
        }
    }

    class TripsViewHolder extends RecyclerView.ViewHolder {

        private final ItemTimelineTripsBinding itemBinding;

        public TripsViewHolder(View itemView) {
            super(itemView);
            this.itemBinding = DataBindingUtil.bind(itemView);;
        }

        public void bind(Trip item) {
            itemBinding.setTimeLineTripItem(item);
            itemBinding.executePendingBindings();
        }
    }

    class FuelUpItemViewHolder extends RecyclerView.ViewHolder {

        private final ItemTimelineFuelUpBinding itemBinding;

        public FuelUpItemViewHolder(View itemView) {
            super(itemView);
            this.itemBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(FuelUp item) {
            itemBinding.setTimeLineFuelUpItem(item);
            itemBinding.executePendingBindings();
        }
    }


    class BoxesViewHolder extends RecyclerView.ViewHolder {

        private final ItemBoxesMainFragmentBinding itemBinding;

        public BoxesViewHolder(View itemView) {
            super(itemView);
            this.itemBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(Object item) {
           // itemBinding.setTimeLineFuelUpItem(item);
          //  itemBinding.executePendingBindings();
        }
    }
}
