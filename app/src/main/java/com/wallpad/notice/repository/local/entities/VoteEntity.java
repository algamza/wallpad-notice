package com.wallpad.notice.repository.local.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class VoteEntity {
    @Embedded
    private VoteInfoEntity info;
    @Relation(
            parentColumn = "masterKey",
            entityColumn = "masterKey",
            entity = VoteDetailEntity.class
    )
    private List<VoteDetailEntity> details;

    public VoteEntity(VoteInfoEntity info, List<VoteDetailEntity> details) {
        this.info = info;
        this.details = details;
    }

    public VoteInfoEntity getInfo() {
        return info;
    }

    public void setInfo(VoteInfoEntity info) {
        this.info = info;
    }

    public List<VoteDetailEntity> getDetails() {
        return details;
    }

    public void setDetails(List<VoteDetailEntity> details) {
        this.details = details;
    }
}
