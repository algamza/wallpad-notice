package com.wallpad.notice.repository.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ReadNoticeEntity {
    @PrimaryKey
    private int id;

    public ReadNoticeEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
