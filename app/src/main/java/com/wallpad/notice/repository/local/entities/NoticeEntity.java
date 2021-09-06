package com.wallpad.notice.repository.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NoticeEntity {
    @PrimaryKey
    private int id;
    private String title;
    private String content;
    private String date;
    private String path;

    public NoticeEntity(int id, String title, String content, String date, String path) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
