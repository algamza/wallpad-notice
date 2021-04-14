package com.wallpad.notice.repository.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DeliveryEntity {
    @PrimaryKey
    private int id;
    private String arriveTime;
    private String pickupTime;
    private int boxNum;
    private boolean receipt;
    private boolean read;

    public DeliveryEntity(int id, String arriveTime, String pickupTime, int boxNum, boolean receipt, boolean read) {
        this.id = id;
        this.arriveTime = arriveTime;
        this.pickupTime = pickupTime;
        this.boxNum = boxNum;
        this.receipt = receipt;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
