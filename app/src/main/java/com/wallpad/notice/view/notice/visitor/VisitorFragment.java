package com.wallpad.notice.view.notice.visitor;

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
import com.wallpad.notice.databinding.FragmentVisitorBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VisitorFragment extends Fragment {
    private VisitorViewModel viewModel;
    @Inject
    VisitorAdapter adapter;
    List<VisitorData> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(VisitorViewModel.class);
        FragmentVisitorBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_visitor, container, false);
        binding.recyclerView.setAdapter(adapter);
        binding.setLifecycleOwner(this);
        viewModel.getVisitors().observe(getViewLifecycleOwner(), data -> adapter.setData(data));
        return binding.getRoot();
    }

    private final VisitorData.ICallback callback = new VisitorData.ICallback() {
        @Override
        public void onClick(int id) {
            VisitorData _data = findVisitorData(id);
            if ( _data == null ) return;
            VisitorDialog dialog = new VisitorDialog(_data);
            dialog.show(requireActivity().getSupportFragmentManager(), dialog.getTag());
        }

        @Override
        public void onClickCheck(int id, boolean check) {
            for ( int i = 0; i<data.size(); i++ ) {
                if ( data.get(i).getId() == id ) {
                    data.get(i).setCheck(check);
                    adapter.notifyItemChanged(i);
                    break;
                }
            }
        }
    };

    private VisitorData findVisitorData(int id) {
        for ( VisitorData visitor : data ) {
            if ( id == visitor.getId() ) return visitor;
        }
        return null;
    }
}
