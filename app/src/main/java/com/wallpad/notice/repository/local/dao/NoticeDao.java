package com.wallpad.notice.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wallpad.notice.repository.local.entities.NoticeEntity;

import java.util.List;

@Dao
public interface NoticeDao {
    @Query("SELECT * FROM NoticeEntity")
    LiveData<List<NoticeEntity>> getEntities();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEntities(List<NoticeEntity> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntity(NoticeEntity entity);

    @Update
    void updateEntity(NoticeEntity entity);

    @Query("UPDATE NoticeEntity SET read=:read WHERE id=:id")
    void updateRead(int id, boolean read);

    @Query("DELETE FROM NoticeEntity WHERE id = :id")
    void deleteEntity(int id);

    @Query("DELETE FROM NoticeEntity")
    void deleteEntities();

    @Query("DELETE FROM DeliveryEntity WHERE id NOT IN (:ids)")
    void deleteNotInclude(List<Integer> ids);

}
