package com.wallpad.parking.view.parking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wallpad.parking.R;
import com.wallpad.parking.databinding.FragmentParkingBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ParkingFragment extends Fragment {
    private ParkingViewModel viewModel;
    @Inject
    ParkingAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ParkingViewModel.class);
        FragmentParkingBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_parking, container, false);
        binding.recyclerView.setAdapter(adapter);
        binding.setLifecycleOwner(this);
        viewModel.getInfos().observe(getViewLifecycleOwner(), data -> adapter.setData(data));
        return binding.getRoot();
    }
}
