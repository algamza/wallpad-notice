package com.wallpad.notice.view.visitor;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wallpad.notice.BR;
import com.wallpad.notice.databinding.RecyclerVisitorBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.VisitorHolder> {
    private final ArrayList<VisitorData> data = new ArrayList<>();

    @Inject public VisitorAdapter() { }

    @NonNull
    @Override
    public VisitorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VisitorHolder(RecyclerVisitorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<VisitorData> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    static class VisitorHolder extends RecyclerView.ViewHolder {
        RecyclerVisitorBinding binding;
        public VisitorHolder(RecyclerVisitorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(VisitorData data) { binding.setVariable(BR.visitor, data); }
    }
}
