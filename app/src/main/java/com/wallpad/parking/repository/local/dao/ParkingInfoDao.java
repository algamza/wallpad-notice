package com.wallpad.parking.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.wallpad.parking.repository.local.entities.ParkingInfoEntity;

import java.util.List;

@Dao
public interface ParkingInfoDao {
    @Transaction
    @Query("SELECT * FROM ParkingInfoEntity")
    LiveData<List<ParkingInfoEntity>> getEntities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntities(List<ParkingInfoEntity> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntity(ParkingInfoEntity entity);

    @Query("DELETE FROM ParkingInfoEntity")
    void deleteEntities();

    @Query("DELETE FROM ParkingInfoEntity WHERE id = :id")
    void deleteEntity(int id);

    @Query("DELETE FROM ParkingInfoEntity WHERE id NOT IN (:ids)")
    void deleteEntities(List<Integer> ids);
}
