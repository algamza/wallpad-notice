package com.wallpad.notice.view.delivery;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wallpad.notice.BR;
import com.wallpad.notice.databinding.RecyclerDeliveryBinding;

import java.util.List;

import javax.inject.Inject;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.DeliveryHolder> {
    private final AsyncListDiffer<DeliveryViewModel.DeliveryData> differ = new AsyncListDiffer<>(this, new DiffCallback());

    @Inject public DeliveryAdapter() { }

    @NonNull
    @Override
    public DeliveryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeliveryHolder(RecyclerDeliveryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setData(List<DeliveryViewModel.DeliveryData> data) {
        differ.submitList(data);
    }

    static class DeliveryHolder extends RecyclerView.ViewHolder {
        RecyclerDeliveryBinding binding;
        public DeliveryHolder(RecyclerDeliveryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(DeliveryViewModel.DeliveryData data) { binding.setVariable(BR.delivery, data); }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<DeliveryViewModel.DeliveryData> {
        @Override
        public boolean areItemsTheSame(@NonNull DeliveryViewModel.DeliveryData oldItem, @NonNull DeliveryViewModel.DeliveryData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull DeliveryViewModel.DeliveryData oldItem, @NonNull DeliveryViewModel.DeliveryData newItem) {
            return oldItem.isRead() == newItem.isRead()
                    && oldItem.isReceipt() == newItem.isReceipt()
                    && oldItem.getPickupTime().equals(newItem.getPickupTime());
        }
    }
}
