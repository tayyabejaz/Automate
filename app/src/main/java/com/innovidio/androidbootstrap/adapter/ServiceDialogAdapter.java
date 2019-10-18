package com.innovidio.androidbootstrap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ItemServiceDialogRecyclerBinding;
import com.innovidio.androidbootstrap.entity.Maintenance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceDialogAdapter extends RecyclerView.Adapter<ServiceDialogAdapter.ServiceDialogViewHolder> {

    private Context context;
    private List<Maintenance> dataList = new ArrayList<>();

    public ServiceDialogAdapter() {
    }

    public ServiceDialogAdapter(Context context, List<Maintenance> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    private void init() {
        for (int i = 0; i < 5; i++) {
            Maintenance maintenance = new Maintenance();
            maintenance.setMaintenanceName("Test Title");
            maintenance.setMaintenanceCost(10000);
            maintenance.setSaveDate(new Date());
            maintenance.setNextMaintenanceDate(new Date());
            dataList.add(maintenance);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        init();
    }

    @NonNull
    @Override
    public ServiceDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ItemServiceDialogRecyclerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_service_dialog_recycler, parent, false);
        return new ServiceDialogViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceDialogViewHolder holder, int position) {
        Maintenance maintenance = dataList.get(position);
        holder.bind(maintenance);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ServiceDialogViewHolder extends RecyclerView.ViewHolder {

        private ItemServiceDialogRecyclerBinding binding;

        public ServiceDialogViewHolder(@NonNull ItemServiceDialogRecyclerBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(Maintenance maintenance) {
            binding.setItemServiceData(maintenance);
            binding.executePendingBindings();
        }
    }
}
