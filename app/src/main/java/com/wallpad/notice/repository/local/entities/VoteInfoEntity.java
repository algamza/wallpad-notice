package com.wallpad.notice.repository.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VoteInfoEntity {
    @PrimaryKey private int masterKey;
    private int type;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private int optionCount;
    private int voteSystem;
    private int status;
    private boolean read;

    public VoteInfoEntity(int masterKey, int type, String title, String description, String startDate, String endDate, int optionCount, int voteSystem, int status, boolean read) {
        this.masterKey = masterKey;
        this.type = type;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.optionCount = optionCount;
        this.voteSystem = voteSystem;
        this.status = status;
        this.read = read;
    }

    public int getMasterKey() {
        return masterKey;
    }

    public void setMasterKey(int masterKey) {
        this.masterKey = masterKey;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getVoteSystem() {
        return voteSystem;
    }

    public void setVoteSystem(int voteSystem) {
        this.voteSystem = voteSystem;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
