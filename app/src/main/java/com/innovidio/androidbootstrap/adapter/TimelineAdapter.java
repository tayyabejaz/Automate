package com.innovidio.androidbootstrap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.MainDashboardRecyclerItemBinding;
import com.innovidio.androidbootstrap.entity.models.TimeLine;

import java.util.ArrayList;
import java.util.List;


public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewholder> {

    private Context context;
    private List<TimeLine> dataList = new ArrayList<>();


    public TimelineAdapter(Context context, List<TimeLine> dataList) {
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

        TimeLine item = dataList.get(position);
        holder.bind(item);

//        if(position == dataList.size()-1){
//            Toast.makeText(context,"This is position:" + position, Toast.LENGTH_LONG).show();
//        }

    }

    @Override
    public int getItemCount() {
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
}
