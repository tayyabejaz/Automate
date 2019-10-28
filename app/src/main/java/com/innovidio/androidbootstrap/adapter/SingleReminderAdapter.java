package com.innovidio.androidbootstrap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ItemSingleAlarmBinding;
import com.innovidio.androidbootstrap.entity.Alarm;
import com.innovidio.androidbootstrap.interfaces.OnAlarmCardListener;

import java.util.ArrayList;
import java.util.List;

public class SingleReminderAdapter extends RecyclerView.Adapter<SingleReminderAdapter.SingleReminderViewHolder> {

    private Context context;
    private List<Alarm> dataList = new ArrayList<>();
    private OnAlarmCardListener listener;

    public SingleReminderAdapter() {
    }

    public SingleReminderAdapter(Context context, OnAlarmCardListener onCarEditDeleteListener, List<Alarm> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.listener = onCarEditDeleteListener;
    }

    @NonNull
    @Override
    public SingleReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        ItemSingleAlarmBinding view = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_single_alarm, parent, false);
        return new SingleReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleReminderViewHolder holder, int position) {
        Alarm maintenance = dataList.get(position);
        holder.bind(maintenance);

        holder.itemView.findViewById(R.id.single_item_edit_icon).setOnClickListener(view -> {
            listener.onEditClicked(maintenance);
        });

        holder.itemView.findViewById(R.id.singleitem_delete_icon).setOnClickListener(view -> {
            listener.onDeleteClicked(maintenance);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class SingleReminderViewHolder extends RecyclerView.ViewHolder {

        private ItemSingleAlarmBinding binding;

        public SingleReminderViewHolder(@NonNull ItemSingleAlarmBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(Alarm alarm) {
            binding.setItemSingleMaintenance(alarm);
            binding.executePendingBindings();
        }

    }

    public void updateList(List<Alarm> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
