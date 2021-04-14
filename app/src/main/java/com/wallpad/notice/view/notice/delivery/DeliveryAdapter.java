package com.wallpad.notice.view.notice.delivery;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wallpad.notice.BR;
import com.wallpad.notice.databinding.RecyclerDeliveryBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.DeliveryHolder> {
    private final ArrayList<DeliveryData> data = new ArrayList<>();

    @Inject public DeliveryAdapter() { }

    @NonNull
    @Override
    public DeliveryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeliveryHolder(RecyclerDeliveryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<DeliveryData> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    static class DeliveryHolder extends RecyclerView.ViewHolder {
        RecyclerDeliveryBinding binding;
        public DeliveryHolder(RecyclerDeliveryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(DeliveryData data) { binding.setVariable(BR.delivery, data); }
    }
}
