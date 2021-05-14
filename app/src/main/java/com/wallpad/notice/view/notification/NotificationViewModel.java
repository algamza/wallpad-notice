package com.wallpad.notice.view.notification;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wallpad.notice.model.NoticeModel;
import com.wallpad.notice.repository.Repository;
import com.wallpad.notice.view.common.Mapper;
import com.wallpad.notice.view.delivery.DeliveryViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationViewModel extends ViewModel {
    private final Repository repository;
    private final LiveData<List<NotificationData>> notifications;
    private final MutableLiveData<NoticeCallback> noticeCallback = new MutableLiveData<>();

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
                        Mapper.mapToChargingTime(model.getDate()),
                        model.isRead()
                ));
            }
            return data;
        });
    }

    private final NotificationData.ICallback callback = new NotificationData.ICallback() {
        @Override
        public void onClick(int id, String title, String content) {
            readNotice(id);
            noticeCallback.postValue(new NoticeCallback(title, content));
        }
    };

    private void readNotice(int id) { repository.readNoticeNotification(id); }
    public LiveData<List<NotificationData>> getNotifications() { return notifications; }
    public LiveData<NoticeCallback> getNoticeCallback() { return noticeCallback; }

    public static class NoticeCallback {
        private String title;
        private String content;

        public NoticeCallback(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class NotificationData implements Comparable {
        public interface ICallback {
            void onClick(int id, String title, String content);
        }

        private ICallback callback;
        private int id;
        private String title;
        private String content;
        private String date;
        private boolean read;

        public NotificationData(ICallback callback, int id, String title, String content, String date, boolean read) {
            this.callback = callback;
            this.id = id;
            this.title = title;
            this.content = content;
            this.date = date;
            this.read = read;
        }

        @Override
        public int compareTo(Object o) {
            NotificationData data = (NotificationData)o;
            return Integer.compare(data.id, this.id);
        }

        public ICallback getCallback() {
            return callback;
        }

        public void setCallback(ICallback callback) {
            this.callback = callback;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public boolean isRead() {
            return read;
        }

        public void setRead(boolean read) {
            this.read = read;
        }
    }

}
