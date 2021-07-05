package com.wallpad.notice.view.visitor;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wallpad.notice.model.VisitorModel;
import com.wallpad.notice.repository.Repository;
import com.wallpad.notice.view.common.Mapper;
import com.wallpad.notice.view.notification.NotificationViewModel;

import java.util.ArrayList;
import java.util.List;

public class VisitorViewModel extends ViewModel {
    private final Repository repository;
    private final LiveData<List<VisitorData>> visitors;
    private final MutableLiveData<VisitorCallback> visitorCallback = new MutableLiveData<>();
    private final MutableLiveData<Check> visitorCheck = new MutableLiveData<>();

    @ViewModelInject public VisitorViewModel(Repository repository) {
        this.repository = repository;
        visitors = Transformations.map(repository.getVisitors(), models -> {
            List<VisitorData> data = new ArrayList<>();
            for ( VisitorModel model : models ) {
                data.add(new VisitorData(
                        callback,
                        model.getId(),
                        model.getPath(),
                        mapToPlace(model.getPlace()),
                        Mapper.mapToChargingTime(model.getDate()),
                        model.isRead(),
                        false
                ));
            }
            return data;
        });
    }

    private final VisitorData.ICallback callback = new VisitorData.ICallback() {
        @Override
        public void onClick(int id, String place, String date, String path) {
            readNotice(id);
            visitorCallback.postValue(new VisitorCallback(id, place, date, path));
        }

        @Override
        public void onClickCheck(int id, boolean check) {
            visitorCheck.postValue(new Check(id, check));
        }
    };

    private void readNotice(int id) { repository.readNoticeVisitor(id); }
    public LiveData<List<VisitorData>> getVisitors() { return visitors; }
    public LiveData<VisitorCallback> getVisitorCallback() { return visitorCallback; }
    public LiveData<Check> getVisitorCheck() { return visitorCheck; }

    public void onClickRemoveAll() {
        List<Integer> ids = new ArrayList<>();
        for ( VisitorData data : visitors.getValue() ) {
            ids.add(data.getId());
        }
        repository.deleteVisitors(ids);
    }

    public void onClickRemoveSelected() {
        List<Integer> ids = new ArrayList<>();
        for ( VisitorData data : visitors.getValue() ) {
            if ( !data.isCheck() ) continue;
            ids.add(data.getId());
            // TODO:
        }
        repository.deleteVisitors(ids);
    }

    private String mapToPlace(String place) {
        if ( place == null ) return "";
        if ( place.contains("Door") ) return "세대현관";
        if ( place.contains("Motion") ) return "동작감지";
        return "로비";
    }

    public static class Check {
        private int id;
        private boolean check;

        public Check(int id, boolean check) {
            this.id = id;
            this.check = check;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }
    }

    public static class VisitorCallback {
        private int id;
        private String place;
        private String date;
        private String path;

        public VisitorCallback(int id, String place, String date, String path) {
            this.id = id;
            this.place = place;
            this.date = date;
            this.path = path;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    public static class VisitorData implements Comparable{
        public interface ICallback {
            void onClick(int id, String place, String date, String path);
            void onClickCheck(int id, boolean check);
        }

        private ICallback callback;
        private int id;
        private String path;
        private String place;
        private String date;
        private boolean read;
        private boolean check;

        public VisitorData(ICallback callback, int id, String path, String place, String date, boolean read, boolean check) {
            this.callback = callback;
            this.id = id;
            this.path = path;
            this.place = place;
            this.date = date;
            this.read = read;
            this.check = check;
        }

        @Override
        public int compareTo(Object o) {
            VisitorData data = (VisitorData)o;
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

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
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

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }
    }
}
