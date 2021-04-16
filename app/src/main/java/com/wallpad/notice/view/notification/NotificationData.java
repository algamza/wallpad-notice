package com.wallpad.notice.view.notification;

public class NotificationData {
    public interface ICallback {
        void onClick(int id);
    }

    private ICallback callback;
    private int id;
    private String title;
    private String content;
    private String date;
    private boolean read;

    public NotificationData(ICallback callback, int id, String title, String content, String date, boolean read) {
        this.callback = callback;
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.read = read;
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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
