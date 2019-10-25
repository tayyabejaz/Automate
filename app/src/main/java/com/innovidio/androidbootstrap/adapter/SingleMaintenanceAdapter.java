package com.innovidio.androidbootstrap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ItemSingleMaintenanceBinding;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.interfaces.OnCarEditDeleteListener;
import com.innovidio.androidbootstrap.interfaces.OnSingleServiceCardListener;

import java.util.ArrayList;
import java.util.List;

public class SingleMaintenanceAdapter extends RecyclerView.Adapter<SingleMaintenanceAdapter.SingleMaintenanceViewHolder> {

    private Context context;
    private List<Maintenance> dataList = new ArrayList<>();
    private OnSingleServiceCardListener listener;

    public SingleMaintenanceAdapter() {
    }

    public SingleMaintenanceAdapter(Context context, OnSingleServiceCardListener onCarEditDeleteListener, List<Maintenance> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.listener = onCarEditDeleteListener;
    }

    @NonNull
    @Override
    public SingleMaintenanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        ItemSingleMaintenanceBinding view = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_single_maintenance, parent, false);
        return new SingleMaintenanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleMaintenanceViewHolder holder, int position) {
        Maintenance maintenance = dataList.get(position);
        holder.bind(maintenance);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class SingleMaintenanceViewHolder extends RecyclerView.ViewHolder {

        private ItemSingleMaintenanceBinding binding;

        public SingleMaintenanceViewHolder(@NonNull ItemSingleMaintenanceBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(Maintenance maintenance) {
            binding.setItemSingleMaintenance(maintenance);
            binding.executePendingBindings();
        }
    }

    public void updateList(List<Maintenance> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
