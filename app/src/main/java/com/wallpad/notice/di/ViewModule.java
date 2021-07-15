package com.wallpad.notice.di;

import android.media.MediaPlayer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@InstallIn(ApplicationComponent.class)
@Module
public class ViewModule {
    @Provides
    @Singleton
    public MediaPlayer provideMediaPlayer() {
        return new MediaPlayer();
    }
}