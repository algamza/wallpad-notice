package com.wallpad.notice.repository.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ReadVisitorEntity {
    @PrimaryKey
    @NonNull
    private String id;

    public ReadVisitorEntity(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}
