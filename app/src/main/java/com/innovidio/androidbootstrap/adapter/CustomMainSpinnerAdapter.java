package com.innovidio.androidbootstrap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ItemCustomSpinnerBinding;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.interfaces.SpinnerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CustomMainSpinnerAdapter extends RecyclerView.Adapter<CustomMainSpinnerAdapter.SpinnerViewHolder> {

    private Context context;
    private List<Car> carList = new ArrayList<>();
    SpinnerItemClickListener spinnerItemClickListener;

    public CustomMainSpinnerAdapter(Context context, SpinnerItemClickListener spinnerItemClickListener, List<Car> cars) {
        this.context = context;
        this.carList = cars;
        this.spinnerItemClickListener = spinnerItemClickListener;
    }

    @NonNull
    @Override
    public SpinnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemCustomSpinnerBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_custom_spinner, parent, false);
        return new SpinnerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SpinnerViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.bind(car);

        holder.itemView.setOnClickListener(view->{
            spinnerItemClickListener.onSpinnerItemClick(car);
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    class SpinnerViewHolder extends RecyclerView.ViewHolder {

        private final ItemCustomSpinnerBinding binding;

        public SpinnerViewHolder(ItemCustomSpinnerBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        void bind(Car model) {
            binding.setCar(model);
            binding.executePendingBindings();

        }
    }

    public void updateAdapterList(List<Car> cars){
        carList =  cars;
        notifyDataSetChanged();
    }
}
