package com.wallpad.notice.view.visitor;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wallpad.notice.R;
import com.wallpad.notice.model.VisitorModel;
import com.wallpad.notice.repository.Repository;
import com.wallpad.notice.view.common.Mapper;
import com.wallpad.notice.view.notification.NotificationViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VisitorViewModel extends ViewModel {
    private final Repository repository;
    private final LiveData<List<VisitorData>> visitors;
    private final MutableLiveData<VisitorCallback> visitorCallback = new MutableLiveData<>();
    private final MutableLiveData<Check> visitorCheck = new MutableLiveData<>();

    @ViewModelInject public VisitorViewModel(Repository repository) {
        this.repository = repository;
        visitors = Transformations.map(repository.getVisitors(), models -> {
            Collections.sort(models, (o1, o2) -> Long.compare(Long.parseLong(o2.getDate()), Long.parseLong(o1.getDate())));
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
        public void onClick(String id, int place, String date, String path) {
            readNotice(id);
            visitorCallback.postValue(new VisitorCallback(id, place, date, path));
        }

        @Override
        public void onClickCheck(String id, boolean check) {
            visitorCheck.postValue(new Check(id, check));
        }
    };

    private void readNotice(String id) { repository.readNoticeVisitor(id); }
    public LiveData<List<VisitorData>> getVisitors() { return visitors; }
    public LiveData<VisitorCallback> getVisitorCallback() { return visitorCallback; }
    public LiveData<Check> getVisitorCheck() { return visitorCheck; }

    public void removeAll() {
        List<String> ids = new ArrayList<>();
        for ( VisitorData data : visitors.getValue() ) ids.add(data.getId());
        repository.deleteVisitors(ids, true);
    }

    public void removeSelected() {
        List<String> ids = new ArrayList<>();
        for ( VisitorData data : visitors.getValue() ) {
            if ( !data.isCheck() ) continue;
            ids.add(data.getId());
        }
        repository.deleteVisitors(ids, false);
    }

    private int mapToPlace(String place) {
        if ( place == null ) return 0;
        if ( place.contains("door") ) return R.string.STR_HOUSEHOLD_ENTRANCE;
        if ( place.contains("lobby") ) return R.string.STR_COMMON_ENTRANCE;
        return 0;
    }

    public static class Check {
        private String id;
        private boolean check;

        public Check(String id, boolean check) {
            this.id = id;
            this.check = check;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
        private String id;
        private int place;
        private String date;
        private String path;

        public VisitorCallback(String id, int place, String date, String path) {
            this.id = id;
            this.place = place;
            this.date = date;
            this.path = path;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPlace() {
            return place;
        }

        public void setPlace(int place) {
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

    public static class VisitorData {
        public interface ICallback {
            void onClick(String id, int place, String date, String path);
            void onClickCheck(String id, boolean check);
        }

        private ICallback callback;
        private String id;
        private String path;
        private int place;
        private String date;
        private boolean read;
        private boolean check;

        public VisitorData(ICallback callback, String id, String path, int place, String date, boolean read, boolean check) {
            this.callback = callback;
            this.id = id;
            this.path = path;
            this.place = place;
            this.date = date;
            this.read = read;
            this.check = check;
        }

        public ICallback getCallback() {
            return callback;
        }

        public void setCallback(ICallback callback) {
            this.callback = callback;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPlace() {
            return place;
        }

        public void setPlace(int place) {
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
