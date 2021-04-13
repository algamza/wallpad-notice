package com.wallpad.parking.repository.local;

import androidx.room.RoomDatabase;

import com.wallpad.parking.repository.local.dao.ParkingInfoDao;
import com.wallpad.parking.repository.local.entities.ParkingInfoEntity;

@androidx.room.Database(entities = {
        ParkingInfoEntity.class,
}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract ParkingInfoDao parkingInfoDao();
}
