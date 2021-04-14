package com.wallpad.notice.view.notice.referendum;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wallpad.notice.model.ReferendumModel;
import com.wallpad.notice.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class ReferendumViewModel extends ViewModel {
    private final Repository repository;
    private final LiveData<List<ReferendumData>> referendums;

    @ViewModelInject public ReferendumViewModel(Repository repository) {
        this.repository = repository;
        referendums = Transformations.map(repository.getReferendums(), models -> {
            List<ReferendumData> data = new ArrayList<>();
            for ( ReferendumModel model : models ) {
                data.add(new ReferendumData(
                        callback,
                        model.getId(),
                        model.getTitle(),
                        model.getContent(),
                        model.getDate(),
                        model.getState(),
                        model.getOkCount(),
                        model.getNoCount(),
                        model.isRead()
                ));
            }
            return data;
        });
    }

    private final ReferendumData.ICallback callback = this::readNotice;
    private void readNotice(int id) { repository.readNoticeReferendums(id);}
    public LiveData<List<ReferendumData>> getReferendums() { return referendums; }
}
