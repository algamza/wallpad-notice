package com.wallpad.notice.view.notification;

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
import com.wallpad.notice.databinding.FragmentNotificationBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationFragment extends Fragment {
    private NotificationViewModel viewModel;
    @Inject
    NotificationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        FragmentNotificationBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        binding.recyclerView.setAdapter(adapter);
        binding.setLifecycleOwner(this);
        viewModel.getNotifications().observe(getViewLifecycleOwner(), data ->{
            Collections.sort(data);
            adapter.setData(data);
        });
        viewModel.getNoticeCallback().observe(getViewLifecycleOwner(), data -> {
            NotificationDialog dialog = new NotificationDialog(data.getTitle(), data.getContent());
            dialog.show(requireActivity().getSupportFragmentManager(), dialog.getTag());
        });

        return binding.getRoot();
    }
}
