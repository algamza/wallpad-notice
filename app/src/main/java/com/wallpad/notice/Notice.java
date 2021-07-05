package com.wallpad.notice;

import android.app.Application;
import android.content.Intent;

import com.wallpad.notice.service.NoticeService;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class Notice extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, NoticeService.class));
    }
}