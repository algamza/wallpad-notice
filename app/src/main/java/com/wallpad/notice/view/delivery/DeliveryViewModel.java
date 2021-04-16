package com.wallpad.notice.view.delivery;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wallpad.notice.model.DeliveryModel;
import com.wallpad.notice.repository.Repository;
import com.wallpad.notice.view.common.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DeliveryViewModel extends ViewModel {
    private final Repository repository;
    private final LiveData<List<DeliveryData>> deliveries;

    @ViewModelInject public DeliveryViewModel(Repository repository) {
        this.repository = repository;
        deliveries = Transformations.map(repository.getDeliveries(), models -> {
            List<DeliveryData> data = new ArrayList<>();
            for ( DeliveryModel model : models ) {
                data.add(new DeliveryData(
                        callback,
                        model.getId(),
                        Mapper.mapToChargingTime(model.getArriveTime()),
                        Mapper.mapToChargingTime(model.getPickupTime()),
                        model.getBoxNum(),
                        model.isReceipt(),
                        model.isRead()
                ));
            }
            return data;
        });
    }
    private final DeliveryData.ICallback callback = this::readNotice;
    private void readNotice(long id) { repository.readNoticeDelivery(id); }
    public LiveData<List<DeliveryData>> getDeliveries() { return deliveries; }

    public static class DeliveryData implements Comparable {
        public interface ICallback {
            void onClick(long id);
        }

        private ICallback callback;
        private long id;
        private String arriveTime;
        private String pickupTime;
        private Integer boxNum;
        private boolean receipt;
        private boolean read;

        public DeliveryData(ICallback callback, long id, String arriveTime, String pickupTime, Integer boxNum, boolean receipt, boolean read) {
            this.callback = callback;
            this.id = id;
            this.arriveTime = arriveTime;
            this.pickupTime = pickupTime;
            this.boxNum = boxNum;
            this.receipt = receipt;
            this.read = read;
        }

        @Override
        public int compareTo(Object o) {
            DeliveryData data = (DeliveryData)o;
            return Long.compare(data.id, this.id);
        }

        public ICallback getCallback() {
            return callback;
        }

        public void setCallback(ICallback callback) {
            this.callback = callback;
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

}
