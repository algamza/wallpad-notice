package com.wallpad.notice.view.visitor;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wallpad.notice.repository.Repository;

public class VisitorDialogViewModel extends ViewModel {
    public final Repository repository;
    private MutableLiveData<String> place = new MutableLiveData<>();
    private MutableLiveData<String> date = new MutableLiveData<>();
    private MutableLiveData<String> screen = new MutableLiveData<>();

    @ViewModelInject public VisitorDialogViewModel(Repository repository) {
        this.repository = repository;
    }

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

    public LiveData<String> getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen.postValue(screen);
    }

    public void deleteVisitor(String id) {
        repository.deleteVisitor(id);
    }
}
