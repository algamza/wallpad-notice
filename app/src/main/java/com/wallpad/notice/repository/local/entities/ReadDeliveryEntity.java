package com.wallpad.notice.repository.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ReadDeliveryEntity {
    @PrimaryKey
    private long id;

    public ReadDeliveryEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}