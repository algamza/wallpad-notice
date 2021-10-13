package com.wallpad.notice.view.visitor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wallpad.notice.R;
import com.wallpad.notice.databinding.FragmentVisitorBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VisitorFragment extends Fragment {
    private VisitorViewModel viewModel;
    @Inject
    VisitorAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(VisitorViewModel.class);
        FragmentVisitorBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_visitor, container, false);
        binding.setViewModel(viewModel);
        binding.setView(this);
        binding.recyclerView.setAdapter(adapter);
        binding.setLifecycleOwner(this);
        viewModel.getVisitors().observe(getViewLifecycleOwner(), data -> adapter.setData(data));
        viewModel.getVisitorCallback().observe(getViewLifecycleOwner(), data -> {
            VisitorDialog dialog = new VisitorDialog(data.getId(), data.getPlace(), data.getDate(), data.getPath());
            dialog.show(requireActivity().getSupportFragmentManager(), dialog.getTag());
        });
        viewModel.getVisitorCheck().observe(getViewLifecycleOwner(), data -> adapter.updateData(data.getId(), data.isCheck()));
        return binding.getRoot();
    }

    public void onClickRemoveAll() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setPositiveButton(getString(R.string.STR_CONFIRM), (dialog, which) -> viewModel.removeAll());
        alertDialog.setNegativeButton(getString(R.string.STR_CANCEL), (dialog, which) -> { });
        AlertDialog alert = alertDialog.create();
        alert.setTitle(R.string.STR_QUESTION_VISITOR_ALL_ITEM_DELETE);
        alert.show();
    }

    public void onClickRemoveSelected() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setPositiveButton(getString(R.string.STR_CONFIRM), (dialog, which) -> viewModel.removeSelected());
        alertDialog.setNegativeButton(getString(R.string.STR_CANCEL), (dialog, which) -> { });
        AlertDialog alert = alertDialog.create();
        alert.setTitle(R.string.STR_QUESTION_VISITOR_SELECT_ITEM_DELETE);
        alert.show();
    }
}
