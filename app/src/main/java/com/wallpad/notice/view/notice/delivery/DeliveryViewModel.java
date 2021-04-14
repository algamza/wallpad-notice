package com.wallpad.notice.view.notice.delivery;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wallpad.notice.model.DeliveryModel;
import com.wallpad.notice.repository.Repository;

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
                        model.getPickupTime(),
                        model.getArriveTime(),
                        model.getBoxNum(),
                        model.isReceipt(),
                        model.isRead()
                ));
            }
            return data;
        });
    }
    private final DeliveryData.ICallback callback = this::readNotice;
    private void readNotice(int id) { repository.readNoticeDelivery(id); }
    public LiveData<List<DeliveryData>> getDeliveries() { return deliveries; }
}
