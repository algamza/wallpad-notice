package com.wallpad.notice.repository.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VisitorEntity {
    @PrimaryKey
    @NonNull
    private String id;
    private String screen;
    private String place;
    private String date;

    public VisitorEntity(String id, String screen, String place, String date) {
        this.id = id;
        this.screen = screen;
        this.place = place;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
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
}
