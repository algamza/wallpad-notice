package com.wallpad.notice.view;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wallpad.notice.model.DeliveryModel;
import com.wallpad.notice.model.NoticeModel;
import com.wallpad.notice.model.VoteModel;
import com.wallpad.notice.model.VisitorModel;
import com.wallpad.notice.repository.Repository;

public class MainViewModel extends ViewModel {
    public enum MODE {
        NOTIFICATION,
        REFERENDUM,
        DELIVERY,
        VISTOR
    }

    private Repository repository;
    private final MutableLiveData<MODE> mode = new MutableLiveData<>(MODE.NOTIFICATION);
    private final LiveData<Integer> deliveryNewCount;
    private final LiveData<Integer> notificationNewCount;
    private final LiveData<Integer> referendumNewCount;
    private final LiveData<Integer> visitorNewCount;

    @ViewModelInject
    public MainViewModel(Repository repository) {
        this.repository = repository;
        deliveryNewCount = Transformations.map(repository.getDeliveries(), models -> {
            int count = 0;
            for ( DeliveryModel model : models ) if ( !model.isRead() ) count++;
            return count;
        });
        notificationNewCount = Transformations.map(repository.getNotices(), models -> {
            int count = 0;
            for ( NoticeModel model : models ) if ( !model.isRead() ) count++;
            return count;
        });
        referendumNewCount = Transformations.map(repository.getVote(), models -> {
            int count = 0;
            for ( VoteModel model : models ) if ( !model.isRead() ) count++;
            return count;
        });
        visitorNewCount = Transformations.map(repository.getVisitors(), models -> {
            int count = 0;
            for ( VisitorModel model : models ) if ( !model.isRead() ) count++;
            return count;
        });
    }

    public void onClickMode(MODE mode) { this.mode.postValue(mode); }

    public LiveData<MODE> getMode() {
        return mode;
    }

    public void setMode(MODE mode) {
        this.mode.postValue(mode);
    }

    public LiveData<Integer> getDeliveryNewCount() {
        return deliveryNewCount;
    }

    public LiveData<Integer> getNotificationNewCount() {
        return notificationNewCount;
    }

    public LiveData<Integer> getReferendumNewCount() {
        return referendumNewCount;
    }

    public LiveData<Integer> getVisitorNewCount() {
        return visitorNewCount;
    }

    public int getDeliveryCount() {
        if ( deliveryNewCount == null || deliveryNewCount.getValue() == null ) return 0;
        return deliveryNewCount.getValue();
    }

    public int getReferendumCount() {
        if ( referendumNewCount == null || referendumNewCount.getValue() == null ) return 0;
        return referendumNewCount.getValue();
    }

    public int getNotificationCount() {
        if ( notificationNewCount == null || notificationNewCount.getValue() == null ) return 0;
        return notificationNewCount.getValue();
    }

    public int getVisitorCount() {
        if ( visitorNewCount == null || visitorNewCount.getValue() == null ) return 0;
        return visitorNewCount.getValue();
    }
}
