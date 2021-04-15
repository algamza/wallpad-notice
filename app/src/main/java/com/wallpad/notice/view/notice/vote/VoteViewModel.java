package com.wallpad.notice.view.notice.vote;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wallpad.notice.R;
import com.wallpad.notice.model.VoteModel;
import com.wallpad.notice.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class VoteViewModel extends ViewModel {
    private final Repository repository;
    private MutableLiveData<VoteItem> voteItem = new MutableLiveData<>();
    private final LiveData<List<VoteData>> voteDatas;

    @ViewModelInject public VoteViewModel(Repository repository) {
        this.repository = repository;
        voteDatas = Transformations.map(repository.getVote(), models -> {
            List<VoteData> data = new ArrayList<>();
            for ( VoteModel model : models ) {
                data.add(new VoteData(callback, model.getMasterId(), model.getTitle(),
                        mapToChargingTime(model.getStartDate()),
                        mapToResIdTextState(model.getState()), model.isRead()));
            }
            return data;
        });
    }

    private final VoteData.Callback callback = id -> voteItem.postValue(new VoteItem(id));
    public LiveData<List<VoteData>> getVoteData() { return voteDatas; }
    public LiveData<VoteItem> getVoteItem() { return voteItem; }

    private String mapToChargingTime(String start) {
        String time = "";
        if ( start.length() < 14 ) return time;
        time = start;
        int hour = Integer.parseInt(time.substring(8, 10));
        String apm = " AM";
        if ( hour > 12 ) apm = " PM";
        time = time.substring(0, 4)+"."+time.substring(4, 6)+"."+time.substring(6, 8)+apm+hour+":"+time.substring(10, 12);
        return time;
    }

    private int mapToResIdTextState(VoteModel.STATE state) {
        switch (state) {
            case VOTE_PROGRESS: return R.string.STR_PROCEEDING;
            case VOTE_END: return R.string.STR_PROGRESS_COMPLETED;
            case VOTE_BEFORE: return R.string.STR_BEFORE_PROCEEDING;
        }
        return 0;
    }

    public static class VoteItem {
        private int id;

        public VoteItem(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class VoteData implements Comparable {
        public interface Callback {
            void onClick(int id);
        }
        private Callback callback;
        private int masterId;
        private String title;
        private String date;
        private int resIdTextStatus;
        private boolean read;

        public VoteData(Callback callback, int masterId, String title, String date, int resIdTextStatus, boolean read) {
            this.callback = callback;
            this.masterId = masterId;
            this.title = title;
            this.date = date;
            this.resIdTextStatus = resIdTextStatus;
            this.read = read;
        }

        @Override
        public int compareTo(Object o) {
            VoteData data = (VoteData)o;
            return Integer.compare(data.masterId, this.masterId);
        }

        public Callback getCallback() {
            return callback;
        }

        public void setCallback(Callback callback) {
            this.callback = callback;
        }

        public int getMasterId() {
            return masterId;
        }

        public void setMasterId(int masterId) {
            this.masterId = masterId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getResIdTextStatus() {
            return resIdTextStatus;
        }

        public void setResIdTextStatus(int resIdTextStatus) {
            this.resIdTextStatus = resIdTextStatus;
        }

        public boolean isRead() {
            return read;
        }

        public void setRead(boolean read) {
            this.read = read;
        }
    }
}
