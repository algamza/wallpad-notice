package com.wallpad.notice.repository.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ReferendumEntity {
    @PrimaryKey
    private int id;
    private String title;
    private String content;
    private String date;
    private int state;
    private int okCount;
    private int noCount;
    private boolean read;

    public ReferendumEntity(int id, String title, String content, String date, int state, int okCount, int noCount, boolean read) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.state = state;
        this.okCount = okCount;
        this.noCount = noCount;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOkCount() {
        return okCount;
    }

    public void setOkCount(int okCount) {
        this.okCount = okCount;
    }

    public int getNoCount() {
        return noCount;
    }

    public void setNoCount(int noCount) {
        this.noCount = noCount;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
