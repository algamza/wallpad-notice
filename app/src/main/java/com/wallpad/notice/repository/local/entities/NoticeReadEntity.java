package com.wallpad.notice.repository.local.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class NoticeReadEntity {
    @Embedded
    private NoticeEntity notice;
    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            entity = ReadNoticeEntity.class
    )
    private ReadNoticeEntity read;

    public NoticeReadEntity(NoticeEntity notice, ReadNoticeEntity read) {
        this.notice = notice;
        this.read = read;
    }

    public NoticeEntity getNotice() {
        return notice;
    }

    public void setNotice(NoticeEntity notice) {
        this.notice = notice;
    }

    public ReadNoticeEntity getRead() {
        return read;
    }

    public void setRead(ReadNoticeEntity read) {
        this.read = read;
    }
}
