package com.wallpad.notice.view.visitor;

public class VisitorData {
    public interface ICallback {
        void onClick(int id);
        void onClickCheck(int id, boolean check);
    }

    private ICallback callback;
    private int id;
    private Integer screen;
    private String place;
    private String date;
    private boolean read;
    private boolean check;

    public VisitorData(ICallback callback, int id, Integer screen, String place, String date, boolean read, boolean check) {
        this.callback = callback;
        this.id = id;
        this.screen = screen;
        this.place = place;
        this.date = date;
        this.read = read;
        this.check = check;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScreen() {
        return screen;
    }

    public void setScreen(int screen) {
        this.screen = screen;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
