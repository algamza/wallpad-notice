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
    private final String id;
    private final String place;
    private final String date;
    private final String path;
    public VisitorDialog(String id, String place, String date, String path) {
        this.id = id;
        this.place = place;
        this.date = date;
        this.path = path;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.LauncherDialog);
        viewModel = new ViewModelProvider(this).get(VisitorDialogViewModel.class);
        viewModel.setDate(this.date);
        viewModel.setPlace(this.place);
        viewModel.setScreen(this.path);
        DialogVisitorBinding binding = DialogVisitorBinding.inflate(LayoutInflater.from(getContext()));
        binding.setLifecycleOwner(this);
        binding.setView(this);
        binding.setVisitor(viewModel);
        return builder.create();
    }

    public void onClickClose() { dismiss(); }

    public void onClickDelete() {
        viewModel.deleteVisitor(id);
        dismiss();
    }
}
