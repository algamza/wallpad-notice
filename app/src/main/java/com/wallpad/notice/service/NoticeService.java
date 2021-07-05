package com.wallpad.notice.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.wallpad.IWallpadData;
import com.wallpad.notice.model.DeliveryModel;
import com.wallpad.notice.model.NoticeModel;
import com.wallpad.notice.model.VisitorModel;
import com.wallpad.notice.model.VoteModel;
import com.wallpad.notice.repository.Repository;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NoticeService extends LifecycleService {
    @Inject
    Repository repository;
    private int deliveryNewCount = 0;
    private int notificationNewCount = 0;
    private int referendumNewCount = 0;
    private int visitorNewCount = 0;
    private static final String SETTINGS_NEW_MESSAGE_COUNT = "com.wallpad.settings.massage";
    private boolean bound = false;

    @Override
    public void onCreate() {
        super.onCreate();
        bindIGSmartService();
        repository.getDeliveries().observe(this, models -> {
            int count = 0;
            for ( DeliveryModel model : models ) if ( !model.isRead() ) count++;
            updateCount(count, 0);
        });
        repository.getNotices().observe(this, models -> {
            int count = 0;
            for ( NoticeModel model : models ) if ( !model.isRead() ) count++;
            updateCount(count, 1);
        });
        repository.getVote().observe(this, models -> {
            int count = 0;
            for ( VoteModel model : models ) if ( !model.isRead() ) count++;
            updateCount(count, 2);
        });
        repository.getVisitors().observe(this, models -> {
            int count = 0;
            for ( VisitorModel model : models ) if ( !model.isRead() ) count++;
            updateCount(count, 3);
        });
    }

    @Override
    public void onDestroy() {
        unBindIGSmartService();
        super.onDestroy();
    }

    private void updateCount(int count, int type) {
        switch (type) {
            case 0: deliveryNewCount = count; break;
            case 1: notificationNewCount = count; break;
            case 2: referendumNewCount = count; break;
            case 3: visitorNewCount = count; break;
        }
        Settings.Global.putInt(getContentResolver(), SETTINGS_NEW_MESSAGE_COUNT,
                deliveryNewCount + notificationNewCount + referendumNewCount + visitorNewCount);
    }

    private void bindIGSmartService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.wallpad.service", "com.wallpad.service.KDService"));
        this.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unBindIGSmartService() {
        if ( !bound ) return;
        this.unbindService(serviceConnection);
        bound = false;
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            repository.injectIWallpadService(IWallpadData.Stub.asInterface(service), callback);
            bound = true;
            repository.refreshLogin();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            repository.injectIWallpadService(null);
            bound = false;
        }
    };

    private final Repository.Callback callback = new Repository.Callback() {
        @Override
        public void onLogin(boolean on) {
            if ( !on ) return;
            repository.refreshNotice();
        }
    };
}
