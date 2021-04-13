package com.wallpad.parking.view.parking;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wallpad.parking.BR;
import com.wallpad.parking.databinding.RecyclerParkingBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.ParkingHolder> {
    private final ArrayList<ParkingViewModel.ParkingInfo> data = new ArrayList<>();
    private final AsyncListDiffer<ParkingViewModel.ParkingInfo> differ = new AsyncListDiffer(this, new ElectricCarDiffCallback());

    @Inject
    public ParkingAdapter() {}

    @NonNull
    @Override
    public ParkingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ParkingHolder(RecyclerParkingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setData(List<ParkingViewModel.ParkingInfo> data) {
        differ.submitList(data);
    }

    static class ParkingHolder extends RecyclerView.ViewHolder {
        RecyclerParkingBinding binding;
        public ParkingHolder(RecyclerParkingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(ParkingViewModel.ParkingInfo parking) {
            binding.setVariable(BR.parking, parking);
        }
    }

    static class ElectricCarDiffCallback extends DiffUtil.ItemCallback<ParkingViewModel.ParkingInfo> {
        @Override
        public boolean areItemsTheSame(@NonNull ParkingViewModel.ParkingInfo oldItem, @NonNull ParkingViewModel.ParkingInfo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ParkingViewModel.ParkingInfo oldItem, @NonNull ParkingViewModel.ParkingInfo newItem) {
            return oldItem.getParkingTime().equals(newItem.getParkingTime());
        }
    }

}
