package com.wallpad.parking.repository.common;

import android.util.Log;

import com.wallpad.parking.model.ParkingInfoModel;
import com.wallpad.parking.repository.local.entities.ParkingInfoEntity;
import com.wallpad.parking.repository.remote.entities.RemoteParkingEntity;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static List<Integer> mapToParkingIds(List<ParkingInfoEntity> entities) {
        List<Integer> ids = new ArrayList<>();
        for ( ParkingInfoEntity entity : entities ) {
            ids.add(entity.getId());
        }
        return ids;
    }

    public static List<ParkingInfoEntity> mapToParkingInfoEntity(RemoteParkingEntity remote) {
        List<ParkingInfoEntity> entities = new ArrayList<>();
        if ( remote == null ) return entities;
        RemoteParkingEntity.Resource resource = remote.getResource();
        if ( resource == null ) return entities;
        List<RemoteParkingEntity.Resource.Delivery_Info_List> list = resource.getDelivery_Info_List();
        if ( list == null ) return entities;
        for (RemoteParkingEntity.Resource.Delivery_Info_List parking : list) {
            entities.add(new ParkingInfoEntity((int)Long.parseLong(parking.getParking_Time()), parking.getCar_No(), parking.getParking_Time(), parking.getParking_Location(), parking.getParking_Location_Image()));
        }
        return entities;
    }

    public static ParkingInfoEntity mapToParkingInfoEntity(ParkingInfoModel model) {
        return new ParkingInfoEntity(model.getId(), model.getCarNumber(), model.getParkingTime(), model.getParkingPlace(), model.getParkingUri());
    }

    public static ParkingInfoModel mapToParkingInfoModel(ParkingInfoEntity entity) {
        return new ParkingInfoModel(entity.getId(), entity.getCarNumber(), entity.getParkingTime(), entity.getParkingPlace(), entity.getParkingUri());
    }
}
