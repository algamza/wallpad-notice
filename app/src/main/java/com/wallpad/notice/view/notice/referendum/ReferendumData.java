package com.wallpad.notice.view.notice.referendum;

public class ReferendumData {
    public interface ICallback {
        void onClick(int id);
    }

    private ICallback callback;
    int id;
    String title;
    String content;
    String date;
    int state;
    int okCount;
    int noCount;
    boolean read;

    public ReferendumData(ICallback callback, int id, String title, String content, String date, int state, int okCount, int noCount, boolean read) {
        this.callback = callback;
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.state = state;
        this.okCount = okCount;
        this.noCount = noCount;
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
