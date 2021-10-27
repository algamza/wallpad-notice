package com.wallpad.notice.model;

import java.util.List;

public class VoteModel {
    public enum TYPE {
        ALL,
        SELECT
    }
    public enum SYSTEM {
        SINGLE,
        MULTIPLE
    }
    public enum STATE {
        VOTE_BEFORE,
        VOTE_PROGRESS,
        VOTE_END,
        ERROR
    }
    private int masterId;
    private TYPE type;
    private String title;
    private String content;
    private String startDate;
    private String endDate;
    private int optionCount;
    private SYSTEM system;
    private STATE state;
    private boolean read;
    private List<Detail> details;

    public VoteModel(int masterId, TYPE type, String title, String content, String startDate, String endDate, int optionCount, SYSTEM system, STATE state, boolean read, List<Detail> details) {
        this.masterId = masterId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.optionCount = optionCount;
        this.system = system;
        this.state = state;
        this.read = read;
        this.details = details;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getOptionCount() {
        return optionCount;
    }

    public void setOptionCount(int optionCount) {
        this.optionCount = optionCount;
    }

    public SYSTEM getSystem() {
        return system;
    }

    public void setSystem(SYSTEM system) {
        this.system = system;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public static class Detail {
        private int voteCode;
        private String title;
        private String content;
        private boolean vote;
        private int voteCount;

        public Detail(int voteCode, String title, String content, boolean vote, int voteCount) {
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
