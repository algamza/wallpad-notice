package com.wallpad.notice.repository.remote;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.wallpad.notice.repository.common.Mapper;
import com.wallpad.notice.repository.local.entities.NoticeEntity;
import com.wallpad.notice.repository.local.entities.VisitorEntity;
import com.wallpad.notice.repository.local.entities.VoteDetailEntity;
import com.wallpad.notice.repository.local.entities.VoteEntity;
import com.wallpad.notice.repository.local.entities.VoteInfoEntity;
import com.wallpad.notice.repository.remote.entities.RemoteNoticeEntity;
import com.wallpad.notice.repository.remote.entities.RemoteNoticeNotifyEntity;
import com.wallpad.notice.repository.remote.entities.RemoteParcelEntity;
import com.wallpad.notice.repository.local.entities.DeliveryEntity;
import com.wallpad.notice.repository.remote.entities.RemoteParcelNotifyEntity;
import com.wallpad.notice.repository.remote.entities.RemoteVoteDetailEntity;
import com.wallpad.notice.repository.remote.entities.RemoteVoteEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class ContentProviderHelper {
    public interface ICallback {
        void onUpdateParcels(List<DeliveryEntity> entities);
        void onUpdateParcelNotify(DeliveryEntity entity);
        void onUpdateVotes(List<VoteInfoEntity> entities);
        void onUpdateDetailVotes(List<VoteDetailEntity> entities);
        void onUpdateNotice(List<NoticeEntity> entities);
        void onUpdateNoticeNotify(NoticeEntity entity);
        void onUpdateVisitor(List<VisitorEntity> entities);
    }
    private static final String TAG = ContentProviderHelper.class.getSimpleName();
    private static final String URI_INFO = "content://com.wallpad.service.provider.InfoContentProvider/t_info";
    public static final String CONTENT_KEY = "content";
    public static final String CONTENT_ID = "id";
    public static final String ID_PARCEL_INFO = "3";                  //2ND01_02
    public static final String ID_PARCEL_NOTIFY = "4";                //2ND01_01
    public static final String ID_VOTE_INFO = "11";                                 //2VO01_01
    public static final String ID_VOTE_DETAIL_INFO = "12";                   //2VO01_02
    public static final String ID_VOTING_COMPLETE_NOTIFY = "13";     //2VO01_03
    public static final String ID_NOTICE_INFO = "14";                                      //2NB01_01
    public static final String ID_NOTICE_NOTIFY = "15";                                    //2NB01_02

    public static final String VISITOR_CONTENT_URI = "content://com.wallpad.service.provider.VisitorInfoContentProvider/t_visitorInfo";
    public static final String KEY_VISITOR_FILE_NAME = "filename";
    public static final String KEY_VISITOR_TYPE = "type";
    public static final String KEY_VISITOR_TIME = "time";

    public static final String KEY_VISITOR_INFO_ALL_MESSAGE = "KEY_VISITOR_INFO_ALL_MESSAGE";
    public static final String KEY_VISITOR_INFO_FILENAME_MESSAGE = "KEY_VISITOR_INFO_FILENAME_MESSAGE";

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
        context.getContentResolver().registerContentObserver(Uri.parse(URI_INFO+"/"+ID_VOTE_INFO), false, observer);
        context.getContentResolver().registerContentObserver(Uri.parse(URI_INFO+"/"+ID_VOTE_DETAIL_INFO), false, observer);
        context.getContentResolver().registerContentObserver(Uri.parse(URI_INFO+"/"+ID_VOTING_COMPLETE_NOTIFY), false, observer);
        context.getContentResolver().registerContentObserver(Uri.parse(URI_INFO+"/"+ID_NOTICE_INFO), false, observer);
        context.getContentResolver().registerContentObserver(Uri.parse(URI_INFO+"/"+ID_NOTICE_NOTIFY), false, observer);
        context.getContentResolver().registerContentObserver(Uri.parse(VISITOR_CONTENT_URI), false, visitorObserver);
    }

    private final ContentObserver visitorObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            requestVisitorInfo();
        }
    };

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
            case ID_PARCEL_NOTIFY: updateParcelNotify(Integer.parseInt(ID_PARCEL_NOTIFY)); break;
            case ID_VOTE_INFO: updateVoteInfo(Integer.parseInt(ID_VOTE_INFO)); break;
            case ID_VOTE_DETAIL_INFO: updateVoteDetail(Integer.parseInt(ID_VOTE_DETAIL_INFO)); break;
            case ID_NOTICE_INFO: updateNoticeInfo(Integer.parseInt(ID_NOTICE_INFO)); break;
            case ID_NOTICE_NOTIFY: updateNoticeNotify(Integer.parseInt(ID_NOTICE_NOTIFY)); break;
        }
    }

    private void updateNoticeInfo(int id) {
        executorService.execute(() -> {
            RemoteNoticeEntity entity = null;
            try (Cursor cursor = context.getContentResolver().query(Uri.parse(URI_INFO), null, null, null, null)) {
                if (cursor == null || !cursor.moveToFirst()) return;
                do {
                    if ( Integer.parseInt(cursor.getString(cursor.getColumnIndex(CONTENT_ID))) == id ) {
                        String notice = cursor.getString(cursor.getColumnIndex(CONTENT_KEY));
                        entity = gson.fromJson(notice, RemoteNoticeEntity.class);
                    }
                } while (cursor.moveToNext());
            } catch (Exception ignored) { }
            if ( callback == null || entity == null ) return;
            callback.onUpdateNotice(Mapper.mapToEntities(entity));
        });
    }

    private void updateNoticeNotify(int id) {
        executorService.execute(() -> {
            RemoteNoticeNotifyEntity entity = null;
            try (Cursor cursor = context.getContentResolver().query(Uri.parse(URI_INFO), null, null, null, null)) {
                if (cursor == null || !cursor.moveToFirst()) return;
                do {
                    if ( Integer.parseInt(cursor.getString(cursor.getColumnIndex(CONTENT_ID))) == id ) {
                        String notice = cursor.getString(cursor.getColumnIndex(CONTENT_KEY));
                        entity = gson.fromJson(notice, RemoteNoticeNotifyEntity.class);
                    }
                } while (cursor.moveToNext());
            } catch (Exception ignored) { }
            if ( callback == null || entity == null ) return;
            callback.onUpdateNoticeNotify(Mapper.mapToEntity(entity));
        });
    }

    private void updateVoteDetail(int id) {
        executorService.execute(() -> {
            RemoteVoteDetailEntity entity = null;
            try (Cursor cursor = context.getContentResolver().query(Uri.parse(URI_INFO), null, null, null, null)) {
                if (cursor == null || !cursor.moveToFirst()) return;
                do {
                    if ( Integer.parseInt(cursor.getString(cursor.getColumnIndex(CONTENT_ID))) == id ) {
                        String vote = cursor.getString(cursor.getColumnIndex(CONTENT_KEY));
                        entity = gson.fromJson(vote, RemoteVoteDetailEntity.class);
                    }
                } while (cursor.moveToNext());
            } catch (Exception ignored) { }
            if ( callback == null || entity == null ) return;
            callback.onUpdateDetailVotes(Mapper.mapToVoteEntities(entity));
        });
    }

    private void updateVoteInfo(int id) {
        executorService.execute(() -> {
            RemoteVoteEntity entity = null;
            try (Cursor cursor = context.getContentResolver().query(Uri.parse(URI_INFO), null, null, null, null)) {
                if (cursor == null || !cursor.moveToFirst()) return;
                do {
                    if ( Integer.parseInt(cursor.getString(cursor.getColumnIndex(CONTENT_ID))) == id ) {
                        String vote = cursor.getString(cursor.getColumnIndex(CONTENT_KEY));
                        entity = gson.fromJson(vote, RemoteVoteEntity.class);
                    }
                } while (cursor.moveToNext());
            } catch (Exception ignored) { }
            if ( callback == null || entity == null ) return;
            callback.onUpdateVotes(Mapper.mapToVoteEntities(entity));
        });
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

    private void updateParcelNotify(int id) {
        executorService.execute(() -> {
            RemoteParcelNotifyEntity entity = null;
            try (Cursor cursor = context.getContentResolver().query(Uri.parse(URI_INFO), null, null, null, null)) {
                if (cursor == null || !cursor.moveToFirst()) return;
                do {
                    if ( Integer.parseInt(cursor.getString(cursor.getColumnIndex(CONTENT_ID))) == id ) {
                        String parcel = cursor.getString(cursor.getColumnIndex(CONTENT_KEY));
                        entity = gson.fromJson(parcel, RemoteParcelNotifyEntity.class);
                    }
                } while (cursor.moveToNext());
            } catch (Exception ignored) { }
            if ( callback == null || entity == null ) return;
            callback.onUpdateParcelNotify(Mapper.mapToDeliveryEntity(entity));
        });
    }

    public void requestVisitorInfo() {
        List<VisitorEntity> entities = new ArrayList<>();
        try (Cursor cursor = context.getContentResolver().query(Uri.parse(VISITOR_CONTENT_URI), null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    if (cursor.moveToFirst()) {
                        while (!cursor.isAfterLast()) {
                            try {
                                String filename = cursor.getString(cursor.getColumnIndex(KEY_VISITOR_FILE_NAME));
                                String type = cursor.getString(cursor.getColumnIndex(KEY_VISITOR_TYPE));
                                String time = cursor.getString(cursor.getColumnIndex(KEY_VISITOR_TIME));
                                entities.add(new VisitorEntity(filename, filename, type, time, false));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            cursor.moveToNext();
                        }
                    }
                } while (cursor.moveToNext());
            }
        }
        if ( callback != null ) callback.onUpdateVisitor(entities);
    }

    public void deleteVisitors(List<String> ids, boolean isAll) {
        try {
            String[] args = ids.toArray(new String[0]);
            if ( isAll ) context.getContentResolver().delete(Uri.parse(VISITOR_CONTENT_URI), KEY_VISITOR_INFO_ALL_MESSAGE, null);
            else context.getContentResolver().delete(Uri.parse(VISITOR_CONTENT_URI), KEY_VISITOR_INFO_FILENAME_MESSAGE, args);
            for ( String str: args ) deleteDir(str);
        } catch (Exception e) {
            Log.d(TAG, "deleteVisitors="+e.toString());
        }
    }

    private void deleteDir(String path) {
        try {
            //Cursor c = context.getContentResolver().query()
            File myFile = new File(path);
            if (myFile.exists()) {
                myFile.delete();
            }
        } catch (Exception ignored){
            Log.e(TAG, ignored.getMessage());
        }
    }

    public void deleteVisitor(String id) {
        try {
            String where = KEY_VISITOR_FILE_NAME+"=?";
            String[] args = {id};
            context.getContentResolver().delete(Uri.parse(VISITOR_CONTENT_URI), where, args);
            for ( String str: args ) deleteDir(str);
        } catch (Exception e) {
        }
    }
}
