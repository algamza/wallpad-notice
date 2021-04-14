package com.wallpad.notice.di;

import android.content.Context;

import com.google.gson.Gson;
import com.wallpad.notice.repository.Repository;
import com.wallpad.notice.repository.local.dao.DeliveryDao;
import com.wallpad.notice.repository.local.dao.NoticeDao;
import com.wallpad.notice.repository.local.dao.ReferendumDao;
import com.wallpad.notice.repository.local.dao.VisitorDao;
import com.wallpad.notice.repository.remote.ContentProviderHelper;
import com.wallpad.notice.repository.remote.IWallpadServiceHelper;
import com.wallpad.notice.repository.remote.TestHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@InstallIn(ApplicationComponent.class)
@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public ContentProviderHelper provideContentProviderHelper(@ApplicationContext Context context, Gson gson) {
        return new ContentProviderHelper(context, gson);
    }

    @Provides
    @Singleton
    public IWallpadServiceHelper provideAidlHelper(@ApplicationContext Context context) {
        return new IWallpadServiceHelper(context);
    }

    @Provides
    @Singleton
    public Repository provideRepository(
            TestHelper testHelper,
            ContentProviderHelper contentProviderHelper,
            IWallpadServiceHelper iWallpadServiceHelper,
            NoticeDao noticeDao,
            ReferendumDao referendumDao,
            DeliveryDao deliveryDao,
            VisitorDao visitorDao
            ) {
        return new Repository(
                testHelper,
                contentProviderHelper,
                iWallpadServiceHelper,
                noticeDao,
                referendumDao,
                deliveryDao,
                visitorDao
        );
    }
}
