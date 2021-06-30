package com.wallpad.notice.view.visitor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wallpad.notice.BR;
import com.wallpad.notice.databinding.RecyclerVisitorBinding;
import com.wallpad.notice.view.notification.NotificationAdapter;
import com.wallpad.notice.view.notification.NotificationViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.VisitorHolder> {
    private final AsyncListDiffer<VisitorViewModel.VisitorData> differ = new AsyncListDiffer<>(this, new VisitorAdapter.DiffCallback());

    @Inject public VisitorAdapter() {  }

    @NonNull
    @Override
    public VisitorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VisitorHolder(RecyclerVisitorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setData(List<VisitorViewModel.VisitorData> data) {
        differ.submitList(data);
    }

    public void updateData(int id, boolean check) {
        List<VisitorViewModel.VisitorData> data = differ.getCurrentList();
        int i = 0;
        for (VisitorViewModel.VisitorData visitor : data) {
            if ( id == visitor.getId() ) {
                visitor.setCheck(check);
                break;
            }
            i++;
        }
        differ.submitList(data);
        notifyItemChanged(i);
    }

    static class VisitorHolder extends RecyclerView.ViewHolder {
        RecyclerVisitorBinding binding;
        public VisitorHolder(RecyclerVisitorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(VisitorViewModel.VisitorData data) { binding.setVariable(BR.visitor, data); }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<VisitorViewModel.VisitorData> {
        @Override
        public boolean areItemsTheSame(@NonNull VisitorViewModel.VisitorData oldItem, @NonNull VisitorViewModel.VisitorData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull VisitorViewModel.VisitorData oldItem, @NonNull VisitorViewModel.VisitorData newItem) {
            return oldItem.getId() == newItem.getId() &&
                    oldItem.isRead() == newItem.isRead() &&
                    oldItem.isCheck() == newItem.isCheck();
        }
    }
}
