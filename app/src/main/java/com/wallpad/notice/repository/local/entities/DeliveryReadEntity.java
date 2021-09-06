package com.wallpad.notice.repository.local.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class DeliveryReadEntity {
    @Embedded
    private DeliveryEntity delivery;
    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            entity = ReadDeliveryEntity.class
    )
    private ReadDeliveryEntity read;

    public DeliveryReadEntity(DeliveryEntity delivery, ReadDeliveryEntity read) {
        this.delivery = delivery;
        this.read = read;
    }

    public DeliveryEntity getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryEntity delivery) {
        this.delivery = delivery;
    }

    public ReadDeliveryEntity getRead() {
        return read;
    }

    public void setRead(ReadDeliveryEntity read) {
        this.read = read;
    }
}
