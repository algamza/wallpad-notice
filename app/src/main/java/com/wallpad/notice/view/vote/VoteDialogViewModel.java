package com.wallpad.notice.view.vote;

import android.util.Log;

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

public class VoteDialogViewModel extends ViewModel {
    private final Repository repository;
    private LiveData<Vote> vote;
    private MutableLiveData<Integer> voteCode = new MutableLiveData<>(0);
    private int masterId = 0;

    @ViewModelInject public VoteDialogViewModel(Repository repository) {
        this.repository = repository;
    }

    public void setId(int masterId) {
        this.masterId = masterId;
        vote = Transformations.map(repository.getVoteDetail(masterId), model -> {
            if ( model == null ) return null;
            String title = model.getTitle();
            String content =  model.getContent();
            boolean voteAble = false;
            int resIdTextState = mapToResIdTextState(model.getState());
            int voteCount = 0;
            List<Vote.Menu> menu = new ArrayList<>();
            if ( model.getDetails() != null ) {
                for ( VoteModel.Detail detail : model.getDetails() ) {
                    if ( detail.isVote() ) voteCount++;
                    menu.add(new Vote.Menu(detail.getVoteCode(), detail.getTitle(), detail.getContent(), detail.isVote(), detail.getVoteCount()));
                }
            }
            if ( model.getState() == VoteModel.STATE.VOTE_PROGRESS ) {
                if ( model.getSystem() == VoteModel.SYSTEM.SINGLE ) voteAble = (voteCount == 0);
                else voteAble = true;
            }
            return new Vote(masterId, title, content, resIdTextState, voteAble, menu);
        });
    }

    public LiveData<Vote> getVote() { return vote; }
    public LiveData<Integer> getVoteCode() { return voteCode; }
    public void setVoteCode(int voteCode) { this.voteCode.postValue(voteCode); }
    public void applyVote() {
        repository.requestVote(masterId, voteCode.getValue());
    }

    private int mapToResIdTextState(VoteModel.STATE state) {
        switch (state) {
            case VOTE_PROGRESS: return R.string.STR_IS_PROCEEDING;
            case VOTE_END: return R.string.STR_IS_PROGRESS_COMPLETED;
            case VOTE_BEFORE: return R.string.STR_IS_BEFORE_PROCEEDING;
        }
        return 0;
    }

    public static class Vote {
        private int masterId;
        private String title;
        private String content;
        private int resIdTextState;
        private boolean voteAble;
        private List<Menu> menus;

        public Vote(int masterId, String title, String content, int resIdTextState, boolean voteAble, List<Menu> menus) {
            this.masterId = masterId;
            this.title = title;
            this.content = content;
            this.resIdTextState = resIdTextState;
            this.voteAble = voteAble;
            this.menus = menus;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getResIdTextState() {
            return resIdTextState;
        }

        public void setResIdTextState(int resIdTextState) {
            this.resIdTextState = resIdTextState;
        }

        public boolean isVoteAble() {
            return voteAble;
        }

        public void setVoteAble(boolean voteAble) {
            this.voteAble = voteAble;
        }

        public List<Menu> getMenus() {
            return menus;
        }

        public void setMenus(List<Menu> menus) {
            this.menus = menus;
        }

        public static class Menu {
            private int voteCode;
            private String title;
            private String content;
            private boolean vote;
            private int voteCount;

            public Menu(int voteCode, String title, String content, boolean vote, int voteCount) {
                this.voteCode = voteCode;
                this.title = title;
                this.content = content;
                this.vote = vote;
                this.voteCount = voteCount;
            }

            public int getVoteCode() {
                return voteCode;
            }

            public void setVoteCode(int voteCode) {
                this.voteCode = voteCode;
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

            public boolean isVote() {
                return vote;
            }

            public void setVote(boolean vote) {
                this.vote = vote;
            }

            public int getVoteCount() {
                return voteCount;
            }

            public void setVoteCount(int voteCount) {
                this.voteCount = voteCount;
            }
        }
    }
}
