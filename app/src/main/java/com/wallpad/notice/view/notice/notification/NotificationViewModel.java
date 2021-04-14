package com.wallpad.notice.view.notice.notification;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wallpad.notice.model.NoticeModel;
import com.wallpad.notice.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class NotificationViewModel extends ViewModel {
    private final Repository repository;
    private final LiveData<List<NotificationData>> notifications;

    @ViewModelInject public NotificationViewModel(Repository repository) {
        this.repository = repository;
        notifications = Transformations.map(repository.getNotices(), models -> {
            List<NotificationData> data = new ArrayList<>();
            for ( NoticeModel model : models ) {
                data.add(new NotificationData(
                        callback,
                        model.getId(),
                        model.getTitle(),
                        model.getContent(),
                        model.getDate(),
                        model.isRead()
                ));
            }
            return data;
        });
    }

    private final NotificationData.ICallback callback = this::readNotice;
    private void readNotice(int id) { repository.readNoticeNotification(id); }
    public LiveData<List<NotificationData>> getNotifications() { return notifications; }
}
