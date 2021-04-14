package com.wallpad.notice.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.wallpad.notice.repository.local.entities.ReferendumEntity;

import java.util.List;

@Dao
public interface ReferendumDao {
    @Query("SELECT * FROM ReferendumEntity")
    LiveData<List<ReferendumEntity>> getEntities();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertEntities(List<ReferendumEntity> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntity(ReferendumEntity entity);

    @Update
    void updateEntity(ReferendumEntity entity);

    @Query("UPDATE ReferendumEntity SET read=:read WHERE id=:id")
    void updateRead(int id, boolean read);

    @Query("DELETE FROM ReferendumEntity WHERE id = :id")
    void deleteEntity(int id);

    @Query("DELETE FROM ReferendumEntity")
    void deleteEntities();

    @Query("DELETE FROM ReferendumEntity WHERE id NOT IN (:ids)")
    void deleteNotInclude(List<Integer> ids);
}
