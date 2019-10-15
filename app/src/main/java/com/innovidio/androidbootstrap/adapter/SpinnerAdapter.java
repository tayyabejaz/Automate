package com.innovidio.androidbootstrap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.CustomSpinnerRowBinding;
import com.innovidio.androidbootstrap.entity.models.SpinnerDataModel;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.SpinnerViewHolder> {

    private Context context;
    private List<SpinnerDataModel> spinnerDataModel = new ArrayList<>();

    public SpinnerAdapter(Context context, List<SpinnerDataModel> spinnerDataModel) {
        this.context = context;
        this.spinnerDataModel = spinnerDataModel;
    }

    @NonNull
    @Override
    public SpinnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        CustomSpinnerRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.custom_spinner_row, parent, false);
        return new SpinnerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SpinnerViewHolder holder, int position) {
        SpinnerDataModel dataModel = new SpinnerDataModel();
        holder.bind(dataModel);
    }

    @Override
    public int getItemCount() {
        return spinnerDataModel.size();
    }

    class SpinnerViewHolder extends RecyclerView.ViewHolder {

        private final CustomSpinnerRowBinding binding;

        public SpinnerViewHolder(CustomSpinnerRowBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        void bind(SpinnerDataModel model) {
            binding.setRowitem(model);
            binding.executePendingBindings();

        }
    }
}
