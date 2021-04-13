package com.wallpad.parking.di;

import android.content.Context;

import com.google.gson.Gson;
import com.wallpad.parking.repository.Repository;
import com.wallpad.parking.repository.local.dao.ParkingInfoDao;
import com.wallpad.parking.repository.remote.IWallpadServiceHelper;
import com.wallpad.parking.repository.remote.TestHelper;

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
    public IWallpadServiceHelper provideAidlHelper(@ApplicationContext Context context, Gson gson) {
        return new IWallpadServiceHelper(context, gson);
    }

    @Provides
    @Singleton
    public Repository provideRepository(
            TestHelper testHelper,
            IWallpadServiceHelper IGServiceHelper,
            ParkingInfoDao dao
            ) {
        return new Repository(
                testHelper,
                IGServiceHelper,
                dao
        );
    }
}
