package com.wallpad.notice.model;

public class NoticeModel {
    private int id;
    private String title;
    private String content;
    private String date;
    private String path;
    private boolean read;

    public NoticeModel(int id, String title, String content, String date, String path, boolean read) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
