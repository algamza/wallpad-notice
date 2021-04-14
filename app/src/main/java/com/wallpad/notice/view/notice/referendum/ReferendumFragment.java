package com.wallpad.notice.view.notice.referendum;

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
import com.wallpad.notice.databinding.FragmentReferendumBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReferendumFragment extends Fragment {
    private ReferendumViewModel viewModel;
    private List<ReferendumData> data = new ArrayList<>();
    @Inject
    ReferendumAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ReferendumViewModel.class);
        FragmentReferendumBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_referendum, container, false);
        binding.recyclerView.setAdapter(adapter);
        binding.setLifecycleOwner(this);
        viewModel.getReferendums().observe(getViewLifecycleOwner(), data -> adapter.setData(data));
        return binding.getRoot();
    }

    private final ReferendumData.ICallback callback = id -> {
        ReferendumData _data = findReferendumData(id);
        if ( _data == null ) return;
        ReferendumDialog dialog = new ReferendumDialog(_data);
        dialog.show(requireActivity().getSupportFragmentManager(), dialog.getTag());
    };

    private ReferendumData findReferendumData(int id) {
        for ( ReferendumData ref : data ) {
            if ( id == ref.getId() ) return ref;
        }
        return null;
    }
}
