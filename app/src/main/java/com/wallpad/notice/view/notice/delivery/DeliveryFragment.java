package com.wallpad.notice.view.notice.delivery;

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
import com.wallpad.notice.databinding.FragmentDeliveryBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeliveryFragment extends Fragment {
    private DeliveryViewModel viewModel;
    @Inject
    DeliveryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(DeliveryViewModel.class);
        FragmentDeliveryBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_delivery, container, false);
        binding.recyclerView.setAdapter(adapter);
        binding.setLifecycleOwner(this);
        viewModel.getDeliveries().observe(getViewLifecycleOwner(), data -> adapter.setData(data));
        return binding.getRoot();
    }
}
