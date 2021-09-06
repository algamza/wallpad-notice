package com.wallpad.notice.repository.local.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class VisitorReadEntity {
    @Embedded
    private VisitorEntity visitor;
    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            entity = ReadVisitorEntity.class
    )
    private ReadVisitorEntity read;

    public VisitorReadEntity(VisitorEntity visitor, ReadVisitorEntity read) {
        this.visitor = visitor;
        this.read = read;
    }

    public VisitorEntity getVisitor() {
        return visitor;
    }

    public void setVisitor(VisitorEntity visitor) {
        this.visitor = visitor;
    }

    public ReadVisitorEntity getRead() {
        return read;
    }

    public void setRead(ReadVisitorEntity read) {
        this.read = read;
    }
}
