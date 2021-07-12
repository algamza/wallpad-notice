package com.wallpad.notice.view.visitor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wallpad.notice.R;
import com.wallpad.notice.databinding.FragmentVisitorBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VisitorFragment extends Fragment {
    private VisitorViewModel viewModel;
    @Inject
    VisitorAdapter adapter;
    List<VisitorViewModel.VisitorData> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(VisitorViewModel.class);
        FragmentVisitorBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_visitor, container, false);
        binding.setViewModel(viewModel);
        binding.recyclerView.setAdapter(adapter);
        binding.setLifecycleOwner(this);
        viewModel.getVisitors().observe(getViewLifecycleOwner(), data -> {
            //Collections.sort(data);
            adapter.setData(data);
        });
        viewModel.getVisitorCallback().observe(getViewLifecycleOwner(), data -> {
            VisitorDialog dialog = new VisitorDialog(data.getId(), data.getPlace(), data.getDate(), data.getPath());
            dialog.show(requireActivity().getSupportFragmentManager(), dialog.getTag());
        });
        viewModel.getVisitorCheck().observe(getViewLifecycleOwner(), data -> {
            adapter.updateData(data.getId(), data.isCheck());
        });
        return binding.getRoot();
    }
}
