package com.wallpad.notice.repository.local;

import androidx.room.RoomDatabase;

import com.wallpad.notice.repository.local.dao.DeliveryDao;
import com.wallpad.notice.repository.local.dao.NoticeDao;
import com.wallpad.notice.repository.local.dao.VoteDao;
import com.wallpad.notice.repository.local.dao.VisitorDao;
import com.wallpad.notice.repository.local.entities.DeliveryEntity;
import com.wallpad.notice.repository.local.entities.NoticeEntity;
import com.wallpad.notice.repository.local.entities.ReadDeliveryEntity;
import com.wallpad.notice.repository.local.entities.ReadNoticeEntity;
import com.wallpad.notice.repository.local.entities.ReadVisitorEntity;
import com.wallpad.notice.repository.local.entities.ReadVoteEntity;
import com.wallpad.notice.repository.local.entities.VoteDetailEntity;
import com.wallpad.notice.repository.local.entities.VoteEntity;
import com.wallpad.notice.repository.local.entities.VisitorEntity;
import com.wallpad.notice.repository.local.entities.VoteInfoEntity;

@androidx.room.Database(entities = {
        NoticeEntity.class,
        VoteInfoEntity.class,
        VoteDetailEntity.class,
        DeliveryEntity.class,
        VisitorEntity.class,
        ReadVisitorEntity.class,
        ReadVoteEntity.class,
        ReadNoticeEntity.class,
        ReadDeliveryEntity.class
}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract NoticeDao noticeDao();
    public abstract VoteDao referendumDao();
    public abstract DeliveryDao deliveryDao();
    public abstract VisitorDao visitorDao();
}
