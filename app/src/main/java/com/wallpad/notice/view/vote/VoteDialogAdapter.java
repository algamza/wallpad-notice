package com.wallpad.notice.view.vote;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wallpad.notice.BR;
import com.wallpad.notice.databinding.RecyclerVoteMenuBinding;

import java.util.List;

import javax.inject.Inject;

public class VoteDialogAdapter extends RecyclerView.Adapter<VoteDialogAdapter.VoteHolder> {
    private final AsyncListDiffer<VoteDialogViewModel.Vote.Menu> differ = new AsyncListDiffer<>(this, new DiffCallback());

    @Inject public VoteDialogAdapter() { }

    @NonNull
    @Override
    public VoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VoteHolder(RecyclerVoteMenuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VoteHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setData(List<VoteDialogViewModel.Vote.Menu> data) {
        differ.submitList(data);
    }

    static class VoteHolder extends RecyclerView.ViewHolder {
        RecyclerVoteMenuBinding binding;
        public VoteHolder(RecyclerVoteMenuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(VoteDialogViewModel.Vote.Menu data) { binding.setVariable(BR.menu, data); }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<VoteDialogViewModel.Vote.Menu> {
        @Override
        public boolean areItemsTheSame(@NonNull VoteDialogViewModel.Vote.Menu oldItem, @NonNull VoteDialogViewModel.Vote.Menu newItem) {
            return oldItem.getVoteCode() == newItem.getVoteCode();
        }

        @Override
        public boolean areContentsTheSame(@NonNull VoteDialogViewModel.Vote.Menu oldItem, @NonNull VoteDialogViewModel.Vote.Menu newItem) {
            return oldItem.isVote() == newItem.isVote();
        }
    }
}
