package com.wallpad.notice.view.vote;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.wallpad.notice.R;
import com.wallpad.notice.databinding.DialogVoteBinding;
import com.wallpad.notice.view.common.BaseDialog;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VoteDialog extends BaseDialog {
    private VoteDialogViewModel viewModel;
    private int masterId;
    @Inject VoteDialogAdapter adapter;

    public VoteDialog(int masterId) {
        this.masterId = masterId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.LauncherDialog);
        viewModel = new ViewModelProvider(this).get(VoteDialogViewModel.class);
        viewModel.setId(masterId);
        DialogVoteBinding binding = DialogVoteBinding.inflate(LayoutInflater.from(getContext()));
        binding.setLifecycleOwner(this);
        binding.setView(this);
        binding.setVote(viewModel);
        binding.recyclerView.setAdapter(adapter);
        builder.setView(binding.getRoot());
        viewModel.getVote().observe(this, data -> {
            if ( data == null || data.getMenus() == null || data.getMenus().size() == 0) return;
            adapter.setData(data.getMenus());
        });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onClickClose() { dismiss(); }

    public void onClickOk() {
        dismiss();
    }

    public void onClickVote() {
        InputDialog dialog = new InputDialog();
        dialog.show(this.getChildFragmentManager(), dialog.getTag());
    }
}
