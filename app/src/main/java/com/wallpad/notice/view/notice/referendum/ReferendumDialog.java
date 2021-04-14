package com.wallpad.notice.view.notice.referendum;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.wallpad.notice.R;
import com.wallpad.notice.databinding.DialogReferendumBinding;
import com.wallpad.notice.view.common.BaseDialog;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReferendumDialog extends BaseDialog {
    private ReferendumDialogViewModel viewModel;
    private final ReferendumData data;

    public ReferendumDialog(ReferendumData data) {
        this.data = data;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.LauncherDialog);
        viewModel = new ViewModelProvider(this).get(ReferendumDialogViewModel.class);
        viewModel.setContent(data.getContent());
        viewModel.setTitle(data.getTitle());
        viewModel.setOkCount(data.getOkCount());
        viewModel.setNoCount(data.getNoCount());
        viewModel.setState(data.getState());
        DialogReferendumBinding binding = DialogReferendumBinding.inflate(LayoutInflater.from(getContext()));
        binding.setLifecycleOwner(this);
        binding.setView(this);
        binding.setReferendum(viewModel);
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

    public void onClickVote() {
        // TODO:
        dismiss();
    }
}
