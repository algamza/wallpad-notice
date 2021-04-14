package com.wallpad.notice.repository.common;

import com.wallpad.notice.model.DeliveryModel;
import com.wallpad.notice.model.NoticeModel;
import com.wallpad.notice.model.ReferendumModel;
import com.wallpad.notice.model.VisitorModel;
import com.wallpad.notice.repository.local.entities.DeliveryEntity;
import com.wallpad.notice.repository.local.entities.NoticeEntity;
import com.wallpad.notice.repository.local.entities.ReferendumEntity;
import com.wallpad.notice.repository.local.entities.VisitorEntity;
import com.wallpad.notice.repository.remote.entities.RemoteParcelEntity;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static NoticeModel mapToNoticeModel(NoticeEntity entity) {
        return new NoticeModel(entity.getId(), entity.getTitle(), entity.getContent(), entity.getDate(), entity.isRead());
    }
    public static NoticeEntity mapToNoticeEntity(NoticeModel model) {
        return new NoticeEntity(model.getId(), model.getTitle(), model.getContent(), model.getDate(), model.isRead());
    }

    public static ReferendumModel mapToReferendumModel(ReferendumEntity entity) {
        return new ReferendumModel(entity.getId(), entity.getTitle(), entity.getContent(), entity.getDate(), entity.getState(), entity.getOkCount(), entity.getNoCount(), entity.isRead());
    }
    public static ReferendumEntity mapToReferendumEntity(ReferendumModel model) {
        return new ReferendumEntity(model.getId(), model.getTitle(), model.getContent(), model.getDate(), model.getState(), model.getOkCount(), model.getNoCount(), model.isRead());
    }

    public static DeliveryModel mapToDeliveryModel(DeliveryEntity entity) {
        return new DeliveryModel(entity.getId(), entity.getArriveTime(), entity.getPickupTime(), entity.getBoxNum(), entity.isReceipt(), entity.isRead());
    }
    public static DeliveryEntity mapToDeliveryEntity(DeliveryModel model) {
        return new DeliveryEntity(model.getId(), model.getPickupTime(), model.getArriveTime(), model.getBoxNum(), model.isReceipt(), model.isRead());
    }
    public static List<DeliveryEntity> mapToDeliveryEntities(RemoteParcelEntity parcel) {
        List<DeliveryEntity> entities = new ArrayList<>();
        if ( parcel == null ) return entities;
        RemoteParcelEntity.Resource resource = parcel.getResource();
        String count = resource.getTotal_Count();
        List<RemoteParcelEntity.Resource.Delivery_Info_List> infos = resource.getDelivery_Info_List();
        if ( infos == null || infos.size() == 0 ) return entities;
        for ( RemoteParcelEntity.Resource.Delivery_Info_List delivery : infos ) {
            entities.add(new DeliveryEntity(getBigKey((int)Long.parseLong(delivery.getArrive_Time()),
                    Integer.parseInt(delivery.getDelivery_Box_Info())),
                    delivery.getArrive_Time(),
                    delivery.getReceive_Time(),
                    Integer.parseInt(delivery.getDelivery_Box_Info()),
                    Integer.parseInt(delivery.getDelivery_Event_Type()) == 1,
                    false));
        }
        return entities;
    }
    public static List<Integer> getKeys(List<DeliveryEntity> entities) {
        List<Integer> keys = new ArrayList<>();
        for ( DeliveryEntity entity : entities ) {
            keys.add(getBigKey((int)Long.parseLong(entity.getArriveTime()), entity.getBoxNum()));
        }
        return keys;
    }

    public static VisitorModel mapToVisitorModel(VisitorEntity entity) {
        return new VisitorModel(entity.getId(), entity.getScreen(), entity.getPlace(), entity.getDate(), entity.isRead());
    }
    public static VisitorEntity mapToVisitorEntity(VisitorModel model) {
        return new VisitorEntity(model.getId(), model.getScreen(), model.getPlace(), model.getDate(), model.isRead());
    }

    public static int getBigKey(int group, int channel) {
        return group*1000+channel;
    }
}
