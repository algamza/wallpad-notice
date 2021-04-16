package com.wallpad.notice.view.vote;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wallpad.notice.BR;
import com.wallpad.notice.databinding.RecyclerVoteBinding;

import java.util.List;

import javax.inject.Inject;

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.VoteHolder> {
    private final AsyncListDiffer<VoteViewModel.VoteData> differ = new AsyncListDiffer<>(this, new DiffCallback());

    @Inject public VoteAdapter() { }

    @NonNull
    @Override
    public VoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VoteHolder(RecyclerVoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VoteHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setData(List<VoteViewModel.VoteData> data) {
        differ.submitList(data);
    }

    static class VoteHolder extends RecyclerView.ViewHolder {
        RecyclerVoteBinding binding;
        public VoteHolder(RecyclerVoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(VoteViewModel.VoteData data) { binding.setVariable(BR.vote, data); }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<VoteViewModel.VoteData> {
        @Override
        public boolean areItemsTheSame(@NonNull VoteViewModel.VoteData oldItem, @NonNull VoteViewModel.VoteData newItem) {
            return oldItem.getMasterId() == newItem.getMasterId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull VoteViewModel.VoteData oldItem, @NonNull VoteViewModel.VoteData newItem) {
            return oldItem.isRead() == newItem.isRead()
                    && oldItem.getResIdTextStatus() == newItem.getResIdTextStatus();
        }
    }
}
