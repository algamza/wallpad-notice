package com.wallpad.notice.repository.remote;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import com.google.gson.Gson;
import com.wallpad.notice.repository.common.Mapper;
import com.wallpad.notice.repository.remote.entities.RemoteParcelEntity;
import com.wallpad.notice.repository.local.entities.DeliveryEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class ContentProviderHelper {
    public interface ICallback {
        void onUpdateParcels(List<DeliveryEntity> entity);
    }

    private static final String URI_INFO = "content://com.wallpad.service.provider.InfoContentProvider/t_info";

    // info
    public static final String CONTENT_KEY = "content";
    public static final String CONTENT_ID = "id";
    public static final String ID_PARCEL_INFO = "3";                  //2ND01_02
    public static final String ID_PARCEL_NOTIFY = "4";                //2ND01_01

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private final Context context;
    private ICallback callback;
    private final Gson gson;

    @Inject public ContentProviderHelper(Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
        registObserver();
    }

    public void setCallback(ICallback callback) { this.callback = callback; }


    private void registObserver() {
        context.getContentResolver().registerContentObserver(Uri.parse(URI_INFO+"/"+ID_PARCEL_INFO), false, observer);
        context.getContentResolver().registerContentObserver(Uri.parse(URI_INFO+"/"+ID_PARCEL_NOTIFY), false, observer);
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
        String id = uri.substring(uri.lastIndexOf(URI_INFO)+URI_INFO.length()+1);
        switch (id) {
            case ID_PARCEL_INFO: updateParcelInfo(Integer.parseInt(ID_PARCEL_INFO)); break;
        }
    }

    private void updateParcelInfo(int id) {
        executorService.execute(() -> {
            RemoteParcelEntity entity = null;
            try (Cursor cursor = context.getContentResolver().query(Uri.parse(URI_INFO), null, null, null, null)) {
                if (cursor == null || !cursor.moveToFirst()) return;
                do {
                    if ( Integer.parseInt(cursor.getString(cursor.getColumnIndex(CONTENT_ID))) == id ) {
                        String parcel = cursor.getString(cursor.getColumnIndex(CONTENT_KEY));
                        entity = gson.fromJson(parcel, RemoteParcelEntity.class);
                    }
                } while (cursor.moveToNext());
            } catch (Exception ignored) { }

            if ( callback == null || entity == null ) return;
            callback.onUpdateParcels(Mapper.mapToDeliveryEntities(entity));
        });
    }
}
