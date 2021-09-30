package com.wallpad.notice.repository.remote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.RemoteException;
import android.util.Log;

import com.wallpad.IWallpadData;

import javax.inject.Inject;


public class IWallpadServiceHelper {
    public interface ICallback {
        void onUpdateLogin(boolean on);
    }
    private final String TAG = this.getClass().getSimpleName();

    public static final String INTENT_FILTER_LOGIN_STATUS = "com.wallpad.intentAction.loginStatus";
    public static final String INTENT_ACTION_LOGIN_DATA_KEY = "com.wallpad.intentAction.loginStatus.dataKey";

    private IWallpadData iWallpadData;
    private final Context context;
    private ICallback callback;
    private boolean login = false;

    @Inject
    public IWallpadServiceHelper(Context context) {
        this.context = context;
        registReceiver();
    }

    private void registReceiver() {
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateLogin(intent.getExtras().getBoolean(INTENT_ACTION_LOGIN_DATA_KEY));
            }
        }, new IntentFilter(INTENT_FILTER_LOGIN_STATUS));
    }
    public void updateLogin(boolean login) {
        if ( this.login == login ) return;
        this.login = login;
        callback.onUpdateLogin(login);
    }

    public void setIWallpadService(IWallpadData iWallpadData, ICallback callback) {
        this.callback = callback;
        setIWallpadService(iWallpadData);
    }

    public void setIWallpadService(IWallpadData iWallpadData) {
        this.iWallpadData = iWallpadData;
        refreshParcelInfo();
        refreshVoteInfo();
    }

    public void refreshNotificationInfo() {
        if ( iWallpadData == null ) return;
        try {
            iWallpadData.refreshNoticeInfo("128", "1");
        } catch (RemoteException e) {
            Log.e(TAG, "error="+e);
        }
    }

    public void refreshParcelInfo() {
        if ( iWallpadData == null ) return;
        try {
            iWallpadData.refreshParcelInfo("128", "1");
        } catch (RemoteException e) {
            Log.e(TAG, "error="+e);
        }
    }

    public void refreshVoteInfo() {
        if ( iWallpadData == null ) return;
        try {
            iWallpadData.refreshVoteInfo("128", "1");
        } catch (RemoteException e) {
            Log.e(TAG, "error="+e);
        }
    }

    public void refreshVoteDetail(int masterKey) {
        if ( iWallpadData == null ) return;
        try {
            iWallpadData.refreshVoteDetailInfo(String.valueOf(masterKey));
        } catch (RemoteException e) {
            Log.e(TAG, "error="+e);
        }
    }

    public void requestVoting(int masterId, int voteCode) {
        if ( iWallpadData == null ) return;
        try {
            iWallpadData.requestVoting(String.valueOf(masterId), String.valueOf(voteCode));
        } catch (RemoteException e) {
            Log.e(TAG, "error="+e);
        }
    }

    public void refreshLogin() {
        if ( iWallpadData == null ) return;
        try {
            iWallpadData.refreshLoginInfo();
        } catch (RemoteException e) {
            Log.e(TAG, "error="+e);
        }
    }

    public void requestVisitorImageConfirm(String path, String fileName) {
        if ( iWallpadData == null ) return;
        try {
            iWallpadData.requestVisitorImageConfirm(path, fileName);
        } catch (RemoteException e) {
            Log.e(TAG, "error="+e);
        }
    }

    public void requestVisitorImageDelete(String path, String fileName) {
        if ( iWallpadData == null ) return;
        try {
            iWallpadData.requestVisitorImageDelete(path, fileName);
        } catch (RemoteException e) {
            Log.e(TAG, "error="+e);
        }
    }
}
