package com.wallpad.notice.repository.local.entities;

import androidx.room.Entity;

@Entity( primaryKeys = {"masterKey", "detailCode"})
public class VoteDetailEntity {
    private int masterKey;
    private int detailCode;
    private String title;
    private String description;
    private boolean vote;
    private int voteCount;

    public VoteDetailEntity(int masterKey, int detailCode, String title, String description, boolean vote, int voteCount) {
        this.masterKey = masterKey;
        this.detailCode = detailCode;
        this.title = title;
        this.description = description;
        this.vote = vote;
        this.voteCount = voteCount;
    }

    public int getMasterKey() {
        return masterKey;
    }

    public void setMasterKey(int masterKey) {
        this.masterKey = masterKey;
    }

    public int getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(int detailCode) {
        this.detailCode = detailCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
