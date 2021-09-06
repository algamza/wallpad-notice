package com.wallpad.notice.repository.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ReadVoteEntity {
    @PrimaryKey
    private int id;

    public ReadVoteEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
