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
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationFragment extends Fragment {
    private NotificationViewModel viewModel;
    List<NotificationData> data = new ArrayList<>();
    @Inject
    NotificationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        FragmentNotificationBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        binding.recyclerView.setAdapter(adapter);
        binding.setLifecycleOwner(this);
        viewModel.getNotifications().observe(getViewLifecycleOwner(), data -> adapter.setData(data));
        return binding.getRoot();
    }

    private final NotificationData.ICallback callback = id -> {
        NotificationData _data = findNotificationData(id);
        if ( _data == null ) return;
        NotificationDialog dialog = new NotificationDialog(_data);
        dialog.show(requireActivity().getSupportFragmentManager(), dialog.getTag());
    };

    private NotificationData findNotificationData(int id) {
        for ( NotificationData noti : data ) {
            if ( id == noti.getId() ) return noti;
        }
        return null;
    }
}
