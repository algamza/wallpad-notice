package com.wallpad.notice.view.visitor;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VisitorDialogViewModel extends ViewModel {
    private MutableLiveData<String> place = new MutableLiveData<>();
    private MutableLiveData<String> date = new MutableLiveData<>();
    private MutableLiveData<Integer> screen = new MutableLiveData<>();

    @ViewModelInject public VisitorDialogViewModel() {}

    public LiveData<String> getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place.postValue(place);
    }

    public LiveData<String> getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.postValue(date);
    }

    public LiveData<Integer> getScreen() {
        return screen;
    }

    public void setScreen(Integer screen) {
        this.screen.postValue(screen);
    }
}
