package com.wallpad.notice.view.notification;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wallpad.notice.BR;
import com.wallpad.notice.databinding.RecyclerNotificationBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {
    private final ArrayList<NotificationData> data = new ArrayList<>();

    @Inject public NotificationAdapter() { }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationHolder(RecyclerNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<NotificationData> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    static class NotificationHolder extends RecyclerView.ViewHolder {
        RecyclerNotificationBinding binding;
        public NotificationHolder(RecyclerNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(NotificationData data) { binding.setVariable(BR.notification, data); }
    }
}
