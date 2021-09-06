package com.wallpad.notice.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.wallpad.notice.repository.local.entities.DeliveryReadEntity;
import com.wallpad.notice.repository.local.entities.ReadVisitorEntity;
import com.wallpad.notice.repository.local.entities.VisitorEntity;
import com.wallpad.notice.repository.local.entities.VisitorReadEntity;

import java.util.List;

@Dao
public interface VisitorDao {
    @Query("SELECT * FROM VisitorEntity")
    LiveData<List<VisitorEntity>> getEntities();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEntities(List<VisitorEntity> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntity(VisitorEntity entity);

    @Update
    void updateEntity(VisitorEntity entity);

    @Query("DELETE FROM VisitorEntity WHERE id = :id")
    void deleteEntity(String id);

    @Query("DELETE FROM VisitorEntity")
    void deleteEntities();

    @Query("DELETE FROM VisitorEntity WHERE id NOT IN (:ids)")
    void deleteNotInclude(List<String> ids);

    @Transaction
    @Query("SELECT * FROM VisitorEntity")
    LiveData<List<VisitorReadEntity>> getVisitorReadEntities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVisitorReadEntity(ReadVisitorEntity entity);
}
