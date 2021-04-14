package com.wallpad.notice.view.notice.delivery;

public class DeliveryData {
    public interface ICallback {
        void onClick(int id);
    }

    private ICallback callback;
    private int id;
    private String arriveTime;
    private String pickupTime;
    private Integer boxNum;
    private boolean receipt;
    private boolean read;

    public DeliveryData(ICallback callback, int id, String arriveTime, String pickupTime, Integer boxNum, boolean receipt, boolean read) {
        this.callback = callback;
        this.id = id;
        this.arriveTime = arriveTime;
        this.pickupTime = pickupTime;
        this.boxNum = boxNum;
        this.receipt = receipt;
        this.read = read;
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

    public Integer getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(Integer boxNum) {
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
