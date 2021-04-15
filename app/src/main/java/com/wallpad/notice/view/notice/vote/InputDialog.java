package com.wallpad.notice.view.notice.vote;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.wallpad.notice.R;
import com.wallpad.notice.databinding.DialogInputBinding;
import com.wallpad.notice.view.common.BaseDialog;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class InputDialog extends BaseDialog {
    private final MutableLiveData<String> target = new MutableLiveData<>();
    private VoteDialogViewModel viewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(VoteDialogViewModel.class);
        DialogInputBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_input, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.setView(this);
        return binding.getRoot();
    }

    public void onClickNum(int num) {
        viewModel.setVoteCode(viewModel.getVoteCode().getValue()*10+num);
    }

    public void onClickRemoveAll() {
        viewModel.setVoteCode(0);
    }

    public void onClickRemove() {
        viewModel.setVoteCode((int)Math.floor(viewModel.getVoteCode().getValue()/10));
    }

    public void onClickApply() {
        viewModel.applyVote();
        dismiss();
    }

    public void onClickCancel() {
        dismiss();
    }

}
