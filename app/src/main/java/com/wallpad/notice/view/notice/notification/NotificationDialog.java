package com.wallpad.notice.view.notice.notification;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.wallpad.notice.R;
import com.wallpad.notice.databinding.DialogNotificationBinding;
import com.wallpad.notice.view.common.BaseDialog;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationDialog extends BaseDialog {
    private NotificationDialogViewModel viewModel;
    private final NotificationData data;
    public NotificationDialog(NotificationData data) {
        this.data = data;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.LauncherDialog);
        viewModel = new ViewModelProvider(this).get(NotificationDialogViewModel.class);
        viewModel.setContent(data.getContent());
        viewModel.setTitle(data.getTitle());
        DialogNotificationBinding binding = DialogNotificationBinding.inflate(LayoutInflater.from(getContext()));
        binding.setLifecycleOwner(this);
        binding.setView(this);
        binding.setNotification(viewModel);
        builder.setView(binding.getRoot());
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onClickClose() { dismiss(); }

    public void onClickOk() {
        // TODO
        dismiss();
    }
}
