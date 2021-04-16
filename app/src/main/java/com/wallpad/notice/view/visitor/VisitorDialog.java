package com.wallpad.notice.view.visitor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.wallpad.notice.R;
import com.wallpad.notice.databinding.DialogVisitorBinding;
import com.wallpad.notice.view.common.BaseDialog;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VisitorDialog extends BaseDialog {
    private VisitorDialogViewModel viewModel;
    private final VisitorData data;
    public VisitorDialog(VisitorData data) { this.data = data; }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.LauncherDialog);
        viewModel = new ViewModelProvider(this).get(VisitorDialogViewModel.class);
        viewModel.setDate(this.data.getDate());
        viewModel.setPlace(this.data.getPlace());
        viewModel.setScreen(this.data.getScreen());
        DialogVisitorBinding binding = DialogVisitorBinding.inflate(LayoutInflater.from(getContext()));
        binding.setLifecycleOwner(this);
        binding.setView(this);
        binding.setVisitor(viewModel);
        builder.setView(binding.getRoot());
        return builder.create();
    }

    public void onClickClose() { dismiss(); }

    public void onClickDelete() {
        // TODO :
        dismiss();
    }
}
