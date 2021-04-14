package com.wallpad.notice.view.notice.referendum;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReferendumDialogViewModel extends ViewModel {
    private MutableLiveData<String> title = new MutableLiveData<>();
    private MutableLiveData<String> content = new MutableLiveData<>();
    private MutableLiveData<Integer> okCount = new MutableLiveData<>();
    private MutableLiveData<Integer> noCount = new MutableLiveData<>();
    private MutableLiveData<Integer> state = new MutableLiveData<>();

    @ViewModelInject public ReferendumDialogViewModel() { }

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

    public LiveData<Integer> getOkCount() {
        return okCount;
    }

    public void setOkCount(Integer okCount) {
        this.okCount.postValue(okCount);
    }

    public LiveData<Integer> getNoCount() {
        return noCount;
    }

    public void setNoCount(Integer noCount) {
        this.noCount.postValue(noCount);
    }

    public LiveData<Integer> getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state.postValue(state);
    }
}
