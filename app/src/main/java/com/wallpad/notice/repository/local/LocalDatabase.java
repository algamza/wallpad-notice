package com.wallpad.notice.repository.local;

import androidx.room.RoomDatabase;

import com.wallpad.notice.repository.local.dao.DeliveryDao;
import com.wallpad.notice.repository.local.dao.NoticeDao;
import com.wallpad.notice.repository.local.dao.ReferendumDao;
import com.wallpad.notice.repository.local.dao.VisitorDao;
import com.wallpad.notice.repository.local.entities.DeliveryEntity;
import com.wallpad.notice.repository.local.entities.NoticeEntity;
import com.wallpad.notice.repository.local.entities.ReferendumEntity;
import com.wallpad.notice.repository.local.entities.VisitorEntity;

@androidx.room.Database(entities = {
        NoticeEntity.class,
        ReferendumEntity.class,
        DeliveryEntity.class,
        VisitorEntity.class
}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract NoticeDao noticeDao();
    public abstract ReferendumDao referendumDao();
    public abstract DeliveryDao deliveryDao();
    public abstract VisitorDao visitorDao();
}
