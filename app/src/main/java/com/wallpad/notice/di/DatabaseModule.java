package com.wallpad.notice.di;

import android.content.Context;

import androidx.room.Room;

import com.wallpad.notice.repository.local.LocalDatabase;
import com.wallpad.notice.repository.local.dao.DeliveryDao;
import com.wallpad.notice.repository.local.dao.NoticeDao;
import com.wallpad.notice.repository.local.dao.ReferendumDao;
import com.wallpad.notice.repository.local.dao.VisitorDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@InstallIn(ApplicationComponent.class)
@Module
public class DatabaseModule {
    @Provides
    @Singleton
    public LocalDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, LocalDatabase.class, "local.db").build();
    }

    @Provides
    public NoticeDao provideNoticeDao(LocalDatabase database) { return database.noticeDao(); }

    @Provides
    public ReferendumDao provideReferendumDao(LocalDatabase database) { return database.referendumDao(); }

    @Provides
    public DeliveryDao provideDeliveryDao(LocalDatabase database) { return database.deliveryDao(); }

    @Provides
    public VisitorDao provideVisitorDao(LocalDatabase database) { return database.visitorDao(); }
}
