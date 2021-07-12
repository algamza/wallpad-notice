package com.wallpad.notice.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.wallpad.notice.repository.local.entities.VoteDetailEntity;
import com.wallpad.notice.repository.local.entities.VoteEntity;
import com.wallpad.notice.repository.local.entities.VoteInfoEntity;

import java.util.List;

@Dao
public interface VoteDao {
    @Transaction
    @Query("SELECT * FROM VoteInfoEntity")
    LiveData<List<VoteEntity>> getEntities();

    @Transaction
    @Query("SELECT * FROM VoteDetailEntity WHERE masterKey =:masterKey")
    LiveData<List<VoteDetailEntity>> getVoteDetailEntities(int masterKey);

    @Transaction
    @Query("SELECT * FROM VoteInfoEntity WHERE masterKey =:masterKey")
    LiveData<VoteEntity> getVoteEntity(int masterKey);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertInfos(List<VoteInfoEntity> infos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDetails(List<VoteDetailEntity> details);

    @Query("DELETE FROM VoteInfoEntity")
    void deleteEntities();

    @Query("DELETE FROM VoteInfoEntity WHERE masterKey = :masterKey")
    void deleteEntity(int masterKey);

    @Query("DELETE FROM VoteInfoEntity WHERE masterKey NOT IN (:keys)")
    void deleteNotInEntities(List<Integer> keys);

    @Query("UPDATE VoteInfoEntity SET read=:read WHERE masterKey=:masterKey")
    void updateRead(int masterKey, boolean read);
}
