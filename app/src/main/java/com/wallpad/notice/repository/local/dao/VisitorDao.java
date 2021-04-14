package com.wallpad.notice.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wallpad.notice.repository.local.entities.VisitorEntity;

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

    @Query("UPDATE VisitorEntity SET read=:read WHERE id=:id")
    void updateRead(int id, boolean read);

    @Query("DELETE FROM VisitorEntity WHERE id = :id")
    void deleteEntity(int id);

    @Query("DELETE FROM VisitorEntity")
    void deleteEntities();

    @Query("DELETE FROM VisitorEntity WHERE id NOT IN (:ids)")
    void deleteNotInclude(List<Integer> ids);
}
