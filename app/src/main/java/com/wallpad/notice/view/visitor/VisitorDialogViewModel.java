package com.wallpad.notice.view.visitor;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wallpad.notice.repository.Repository;

public class VisitorDialogViewModel extends ViewModel {
    public final Repository repository;
    private MutableLiveData<Integer> place = new MutableLiveData<>();
    private MutableLiveData<String> date = new MutableLiveData<>();
    private MutableLiveData<String> screen = new MutableLiveData<>();
    private MutableLiveData<Boolean> isMedia = new MutableLiveData<>();
    private MutableLiveData<Boolean> isPrepare = new MutableLiveData<>(false);

    @ViewModelInject public VisitorDialogViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<Integer> getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place.postValue(place);
    }

    public LiveData<String> getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.postValue(date);
    }

    public LiveData<String> getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen.postValue(screen);
    }

    public LiveData<Boolean> getIsMedia() {
        return isMedia;
    }

    public void setIsMedia(boolean isMedia) {
        this.isMedia.postValue(isMedia);
    }

    public LiveData<Boolean> getIsPrepare() {
        return isPrepare;
    }

    public void setIsPrepare(boolean isPrepare) {
        this.isPrepare.postValue(isPrepare);
    }

    public void deleteVisitor(String id) {
        repository.deleteVisitor(id);
    }
}
