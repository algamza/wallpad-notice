package com.wallpad.notice.view.notice.notification;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationDialogViewModel extends ViewModel {
    private MutableLiveData<String> title = new MutableLiveData<>();
    private MutableLiveData<String> content = new MutableLiveData<>();
    @ViewModelInject public NotificationDialogViewModel() { }

    public LiveData<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.postValue(title);
    }

    public LiveData<String> getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content.postValue(content);
    }
}
