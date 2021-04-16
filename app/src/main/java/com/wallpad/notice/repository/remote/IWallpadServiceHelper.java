package com.wallpad.notice.repository.remote;

import android.os.RemoteException;
import android.util.Log;

import com.wallpad.IWallpadData;

import javax.inject.Inject;


public class IWallpadServiceHelper {

    private final String TAG = this.getClass().getSimpleName();

    private IWallpadData iWallpadData;

    @Inject
    public IWallpadServiceHelper() { }

    public void setIWallpadService(IWallpadData iWallpadData) {
        this.iWallpadData = iWallpadData;
        refreshParcelInfo();
        refreshVoteInfo();
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

    public void refreshVoteDetail(String id) {
        if ( iWallpadData == null ) return;
        try {
            iWallpadData.refreshVoteDetailInfo(id);
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
}
