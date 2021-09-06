package com.wallpad.notice.repository.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DeliveryEntity {
    @PrimaryKey
    private long id;
    private String arriveTime;
    private String pickupTime;
    private int boxNum;
    private boolean receipt;

    public DeliveryEntity(long id, String arriveTime, String pickupTime, int boxNum, boolean receipt) {
        this.id = id;
        this.arriveTime = arriveTime;
        this.pickupTime = pickupTime;
        this.boxNum = boxNum;
        this.receipt = receipt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public int getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
    }

    public boolean isReceipt() {
        return receipt;
    }

    public void setReceipt(boolean receipt) {
        this.receipt = receipt;
    }
}
