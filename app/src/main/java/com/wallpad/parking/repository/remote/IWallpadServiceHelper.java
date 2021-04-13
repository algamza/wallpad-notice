package com.wallpad.parking.repository.remote;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;

import com.google.gson.Gson;
import com.wallpad.IWallpadData;
import com.wallpad.parking.repository.remote.entities.RemoteParkingEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IWallpadServiceHelper {
    public interface ICallback {
        void onUpdateRemote(RemoteParkingEntity entity);
    }

    private final String TAG = this.getClass().getSimpleName();
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private Context context;
    private Gson gson;
    private ICallback callback;
    private IWallpadData iWallpadData;

    private static final String CONTENT_URI = "content://com.wallpad.service.provider.InfoContentProvider/t_info";
    private static final String CONTENT_KEY = "content";
    private static final String CONTENT_ID = "id";
    private static final String ID_PARKING_LOCATION_INFO = "7";        //2CI01_04
    private static final String ID_PARKING_LOCATION_NOTIFY = "8";      //2CI01_03

    public IWallpadServiceHelper(Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
        registObserver();
    }

    public void setIWallpadService(IWallpadData iWallpadData, ICallback callback) {
        this.iWallpadData = iWallpadData;
        this.callback = callback;
        refreshParkingLocationInfo();
    }

    public void refreshParkingLocationInfo() {
        if ( iWallpadData == null ) return;
        try {
            iWallpadData.refreshParkingLocationInfo("128", "1");
        } catch (RemoteException e) {
            Log.e(TAG, "error="+e);
        }
    }

    private void registObserver() {
        context.getContentResolver().registerContentObserver(Uri.parse(CONTENT_URI+"/"+ID_PARKING_LOCATION_INFO), false, observer);
        context.getContentResolver().registerContentObserver(Uri.parse(CONTENT_URI+"/"+ID_PARKING_LOCATION_NOTIFY), false, observer);
    }

    private final ContentObserver observer = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            updateInfo(uri.toString());
        }
    };

    private void updateInfo(String uri) {
        if ( uri == null ) return;
        String id = uri.substring(uri.lastIndexOf(CONTENT_URI)+CONTENT_URI.length()+1);
        switch (id) {
            case ID_PARKING_LOCATION_INFO: updateParkingInfo(Integer.parseInt(ID_PARKING_LOCATION_INFO)); break;
        }
    }

    private void updateParkingInfo(int id) {
        executorService.execute(() -> {
            RemoteParkingEntity entity = null;
            try (Cursor cursor = context.getContentResolver().query(Uri.parse(CONTENT_URI), null, null, null, null)) {
                if (cursor == null || !cursor.moveToFirst()) return;
                do {
                    if ( Integer.parseInt(cursor.getString(cursor.getColumnIndex(CONTENT_ID))) == id ) {
                        String parking = cursor.getString(cursor.getColumnIndex(CONTENT_KEY));
                        entity = gson.fromJson(parking, RemoteParkingEntity.class);

                    }
                } while (cursor.moveToNext());
            } catch (Exception ignored) { }

            if ( callback == null || entity == null ) return;
            callback.onUpdateRemote(entity);
        });
    }
}
