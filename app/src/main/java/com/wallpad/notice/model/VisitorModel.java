package com.wallpad.notice.model;

public class VisitorModel {
    private int id;
    private int screen;
    private String place;
    private String date;
    private boolean read;

    public VisitorModel(int id, int screen, String place, String date, boolean read) {
        this.id = id;
        this.screen = screen;
        this.place = place;
        this.date = date;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScreen() {
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
}
