package com.wallpad.parking.di;

import android.content.Context;

import androidx.room.Room;

import com.wallpad.parking.repository.local.LocalDatabase;
import com.wallpad.parking.repository.local.dao.ParkingInfoDao;

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
    public ParkingInfoDao provideParkingInfoDao(LocalDatabase database) { return database.parkingInfoDao(); }
}
