package com.wallpad.notice.view.vote;

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
import com.wallpad.notice.databinding.FragmentVoteBinding;

import java.util.Collections;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VoteFragment extends Fragment {
    private VoteViewModel viewModel;
    @Inject VoteAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(VoteViewModel.class);
        FragmentVoteBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vote, container, false);
        binding.recyclerView.setAdapter(adapter);
        binding.setLifecycleOwner(this);
        viewModel.getVoteData().observe(getViewLifecycleOwner(), data -> {
            Collections.sort(data);
            adapter.setData(data);
        });
        viewModel.getVoteItem().observe(getViewLifecycleOwner(), data -> {
            VoteDialog dialog = new VoteDialog(data.getId());
            dialog.show(requireActivity().getSupportFragmentManager(), dialog.getTag());
        });
        return binding.getRoot();
    }
}
