package com.wallpad.notice.view.notice.referendum;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wallpad.notice.BR;
import com.wallpad.notice.databinding.RecyclerReferendumBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ReferendumAdapter extends RecyclerView.Adapter<ReferendumAdapter.ReferendumHolder> {
    private final ArrayList<ReferendumData> data = new ArrayList<>();

    @Inject public ReferendumAdapter() { }

    @NonNull
    @Override
    public ReferendumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReferendumHolder(RecyclerReferendumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReferendumHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<ReferendumData> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    static class ReferendumHolder extends RecyclerView.ViewHolder {
        RecyclerReferendumBinding binding;
        public ReferendumHolder(RecyclerReferendumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(ReferendumData data) { binding.setVariable(BR.referendum, data); }
    }
}
