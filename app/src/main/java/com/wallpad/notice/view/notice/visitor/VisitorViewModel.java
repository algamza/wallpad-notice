package com.wallpad.notice.view.notice.visitor;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wallpad.notice.model.VisitorModel;
import com.wallpad.notice.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class VisitorViewModel extends ViewModel {
    private final Repository repository;
    private final LiveData<List<VisitorData>> visitors;

    @ViewModelInject public VisitorViewModel(Repository repository) {
        this.repository = repository;
        visitors = Transformations.map(repository.getVisitors(), models -> {
            List<VisitorData> data = new ArrayList<>();
            for ( VisitorModel model : models ) {
                data.add(new VisitorData(
                        callback,
                        model.getId(),
                        model.getScreen(),
                        model.getPlace(),
                        model.getDate(),
                        model.isRead(),
                        false
                ));
            }
            return data;
        });
    }

    private final VisitorData.ICallback callback = new VisitorData.ICallback() {
        @Override
        public void onClick(int id) {
            readNotice(id);
        }
        @Override
        public void onClickCheck(int id, boolean check) {
        }
    };

    private void readNotice(int id) { repository.readNoticeReferendums(id); }
    public LiveData<List<VisitorData>> getVisitors() { return visitors; }
}
