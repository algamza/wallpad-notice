package com.wallpad.notice.view.notification;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wallpad.notice.BR;
import com.wallpad.notice.databinding.RecyclerNotificationBinding;

import java.util.List;

import javax.inject.Inject;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {
    private final AsyncListDiffer<NotificationViewModel.NotificationData> differ = new AsyncListDiffer<>(this, new NotificationAdapter.DiffCallback());

    @Inject public NotificationAdapter() { }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationHolder(RecyclerNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setData(List<NotificationViewModel.NotificationData> data) {
        differ.submitList(data);
    }

    static class NotificationHolder extends RecyclerView.ViewHolder {
        RecyclerNotificationBinding binding;
        public NotificationHolder(RecyclerNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(NotificationViewModel.NotificationData data) { binding.setVariable(BR.notification, data); }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<NotificationViewModel.NotificationData> {
        @Override
        public boolean areItemsTheSame(@NonNull NotificationViewModel.NotificationData oldItem, @NonNull NotificationViewModel.NotificationData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NotificationViewModel.NotificationData oldItem, @NonNull NotificationViewModel.NotificationData newItem) {
            return oldItem.getId() == newItem.getId() &&
                    oldItem.isRead() == newItem.isRead();
        }
    }
}
