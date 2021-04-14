package com.wallpad.notice.repository.remote;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import com.wallpad.IWallpadData;


public class IWallpadServiceHelper {

    private final String TAG = this.getClass().getSimpleName();
    private final Context context;

    private IWallpadData iWallpadData;
    private boolean login = false;

    public IWallpadServiceHelper(Context context) {
        this.context = context;
    }

    public void setIWallpadService(IWallpadData iWallpadData) {
        this.iWallpadData = iWallpadData;
        refreshParcelInfo();
    }

    public void refreshParcelInfo() {
        if ( iWallpadData == null ) return;
        try {
            iWallpadData.refreshParcelInfo("128", "1");
        } catch (RemoteException e) {
            Log.e(TAG, "error="+e);
        }
    }
}
