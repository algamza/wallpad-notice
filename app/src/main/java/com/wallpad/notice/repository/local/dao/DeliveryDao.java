package com.wallpad.notice.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.wallpad.notice.repository.local.entities.DeliveryEntity;
import com.wallpad.notice.repository.local.entities.DeliveryReadEntity;
import com.wallpad.notice.repository.local.entities.ReadDeliveryEntity;

import java.util.List;

@Dao
public interface DeliveryDao {
    @Query("SELECT * FROM DeliveryEntity")
    LiveData<List<DeliveryEntity>> getEntities();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEntities(List<DeliveryEntity> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntity(DeliveryEntity entity);

    @Update
    void updateEntity(DeliveryEntity entity);

    @Query("DELETE FROM DeliveryEntity WHERE id = :id")
    void deleteEntity(long id);

    @Query("DELETE FROM DeliveryEntity")
    void deleteEntities();

    @Query("DELETE FROM DeliveryEntity WHERE id NOT IN (:ids)")
    void deleteNotInclude(List<Long> ids);

    @Transaction
    @Query("SELECT * FROM DeliveryEntity")
    LiveData<List<DeliveryReadEntity>> getDeliveryReadEntities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDeliveryReadEntity(ReadDeliveryEntity entity);
}
