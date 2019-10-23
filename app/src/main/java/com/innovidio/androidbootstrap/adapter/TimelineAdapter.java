package com.innovidio.androidbootstrap.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.interfaces.TimelineItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<? extends TimeLineItem> timeLineItemList = new ArrayList<>();
    private int adapterType = 0;
    private TimelineItemClickListener timelineItemClickListener;

    private static final int FUEL_UP = 0;
    private static final int TRIPS = 1;
    private static final int MAINTENANCE = 2;
    private static final int CAR_WASH = 3;
    private static final int FOOTER = 4;


    public TimelineAdapter(Context context, TimelineItemClickListener listener, List<? extends TimeLineItem> dataList, int adapterType) {
        this.context = context;
        this.timeLineItemList = dataList;
        this.timelineItemClickListener = listener;
        this.adapterType = adapterType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        switch (viewType) {
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
                return new MaintenanceCarWashViewHolder(view);
            case TRIPS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_trips,
                        parent, false);
                return new TripsViewHolder(view);

            case FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_timeline,
                        parent, false);
                return new FooterViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, int position) {

        // check if size is 0 then return after adding footer
        if (setFooterItem(holderParent, position)){
            return;
        }
        // =======================================

        TimeLineItem item = timeLineItemList.get(position);
        switch (item.getType()) {
            case FUEL:
                FuelUp fuelUp = (FuelUp) item;
                FuelUpItemViewHolder fuelUpItemViewHolder = (FuelUpItemViewHolder) holderParent;
                fuelUpItemViewHolder.bind(fuelUp);

                fuelUpItemViewHolder.itemBinding.imageIcon.setBackground(IconProvider.getFuelUp(context).getBackground());
                fuelUpItemViewHolder.itemBinding.imageIcon.setImageDrawable(IconProvider.getFuelUp(context).getDrawable());

                fuelUpItemViewHolder.itemView.setOnClickListener(view -> {
                    timelineItemClickListener.onFuelUpClick(fuelUp);
                });

                break;

            case MAINTENANCE:
                Maintenance maintenance = (Maintenance) item;
                MaintenanceViewHolder maintenanceViewHolder = (MaintenanceViewHolder) holderParent;
                maintenanceViewHolder.bind(maintenance);

                maintenanceViewHolder.itemView.setOnClickListener(view -> {
                    timelineItemClickListener.onMaintenanceClick(maintenance);
                });
                maintenanceViewHolder.itemBinding.imageIcon.setBackground(IconProvider.getServices(context).getBackground());
                maintenanceViewHolder.itemBinding.imageIcon.setImageDrawable(IconProvider.getServices(context).getDrawable());

                break;


            case CAR_WASH:
                Maintenance maintenanceCarWash = (Maintenance) item;
                MaintenanceCarWashViewHolder maintenanceCarWashViewHolder = (MaintenanceCarWashViewHolder) holderParent;
                maintenanceCarWashViewHolder.bind(maintenanceCarWash);

                maintenanceCarWashViewHolder.itemView.setOnClickListener(view -> {
                    timelineItemClickListener.onCarWashClick(maintenanceCarWash);
                });
                maintenanceCarWashViewHolder.itemBinding.imageIcon.setBackground(IconProvider.getCarWash(context).getBackground());
                maintenanceCarWashViewHolder.itemBinding.imageIcon.setImageDrawable(IconProvider.getCarWash(context).getDrawable());

                break;

            case TRIP:
                Trip trip = (Trip) item;
                TripsViewHolder tripsViewHolder = (TripsViewHolder) holderParent;
                tripsViewHolder.bind(trip);

                tripsViewHolder.itemView.setOnClickListener(view -> {
                    timelineItemClickListener.onTripsClick(trip);
                });
                tripsViewHolder.itemBinding.imageIcon.setBackground(IconProvider.getTrip(context).getBackground());
                tripsViewHolder.itemBinding.imageIcon.setImageDrawable(IconProvider.getTrip(context).getDrawable());
                break;
        }
    }

    @Override
    public int getItemCount() {
        Log.e("timeLine", "arrayListSize" + timeLineItemList.size());
        if (adapterType == Constants.FILTERED_ADAPTER) {
            return timeLineItemList.size();
        }
        // todo -- +1 for footer item
        return timeLineItemList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        // todo -- check for footer item
        if (position < timeLineItemList.size()) {
            switch (timeLineItemList.get(position).getType()) {
                case FUEL:
                    return FUEL_UP;
                case TRIP:
                    return TRIPS;
                case MAINTENANCE:
                    return MAINTENANCE;
                case CAR_WASH:
                    return CAR_WASH;
            }
        }
        return FOOTER;
    }

    public void updateData(List<TimeLineItem> updatedList) {
        this.timeLineItemList = updatedList;
        notifyDataSetChanged();
    }

    private boolean setFooterItem(RecyclerView.ViewHolder holderParent, int position) {
        boolean value = false;
        // todo for footer item only
        // return because no data binding needed for footer
        if (timeLineItemList.size() == position) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holderParent;
            if (position < 1) {
                footerViewHolder.itemBinding.ivTrackitem.setVisibility(View.VISIBLE);

            } else {
                footerViewHolder.itemBinding.ivTrackitem.setVisibility(View.GONE);
            }
            // true means retun not run below code in viewbinder
            value = true;
        }//TODO: Done this for a Track Item
        else if (adapterType == Constants.FILTERED_ADAPTER) {
            holderParent.itemView.findViewById(R.id.iv_trackitem).setVisibility(View.GONE);
        } else {
            holderParent.itemView.findViewById(R.id.iv_trackitem).setVisibility(View.VISIBLE);
        }
        return value;
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

    class MaintenanceCarWashViewHolder extends RecyclerView.ViewHolder {

        private final ItemTimelineCarWashBinding itemBinding;

        public MaintenanceCarWashViewHolder(View itemView) {
            super(itemView);
            this.itemBinding = DataBindingUtil.bind(itemView);
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
            this.itemBinding = DataBindingUtil.bind(itemView);
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

    class FooterViewHolder extends RecyclerView.ViewHolder {

        private final ItemFooterTimelineBinding itemBinding;

        public FooterViewHolder(View itemView) {
            super(itemView);
            this.itemBinding = DataBindingUtil.bind(itemView);
        }

//        public void bind(Object item) {
//           // itemBinding.setTimeLineFuelUpItem(item);
//          //  itemBinding.executePendingBindings();
//        }
    }
}
