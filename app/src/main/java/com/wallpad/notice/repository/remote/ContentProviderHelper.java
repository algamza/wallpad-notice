package com.wallpad.notice.repository.remote;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
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

    public void testNoticeUpdate() {
        executorService.execute(() -> {
            String str = "{\n" +
                    "\n" +
                    "\"Error_Cd\":0000,\n" +
                    "\"Error_Nm\":\"성공\",\n" +
                    "\n" +
                    "\"Resource\" : {\n" +
                    "\n" +
                    "           Total_Count: 8,\n" +
                    "\n" +
                    "           Notice_Board_List : [\n" +
                    "\n" +
                    "                     {\n" +
                    "\n" +
                    "                                \"Notice_Board_Seq\":\"12345\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Title\":\"반려견 금지\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Contents\":\"민원이 많이 옵니다. 키우지 마세요.\",\n" +
                    "\n" +
                    "                                \"Notice_Board_File_Path\":\"파일경로\",\n" +
                    "\n" +
                    "                                \"Reg_Date\":\"20200522154321500\"\n" +
                    "\n" +
                    "                     },\n" +
                    "                                          {\n" +
                    "\n" +
                    "                                \"Notice_Board_Seq\":\"12335\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Title\":\"화단앞 주차 금지\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Contents\":\"민원이 많이 옵니다. 주차하지 마세요.\",\n" +
                    "\n" +
                    "                                \"Notice_Board_File_Path\":\"파일경로\",\n" +
                    "\n" +
                    "                                \"Reg_Date\":\"20200521154321500\"\n" +
                    "\n" +
                    "                     },\n" +
                    "                                          {\n" +
                    "\n" +
                    "                                \"Notice_Board_Seq\":\"12245\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Title\":\"주민투표 공지\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Contents\":\"주민 투표에 참여하세요\",\n" +
                    "\n" +
                    "                                \"Notice_Board_File_Path\":\"파일경로\",\n" +
                    "\n" +
                    "                                \"Reg_Date\":\"20200520154321500\"\n" +
                    "\n" +
                    "                     },\n" +
                    "                                          {\n" +
                    "\n" +
                    "                                \"Notice_Board_Seq\":\"11345\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Title\":\"방역 작업 안내\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Contents\":\"방역 작업 진행 예정입니다.\",\n" +
                    "\n" +
                    "                                \"Notice_Board_File_Path\":\"파일경로\",\n" +
                    "\n" +
                    "                                \"Reg_Date\":\"20200519154321500\"\n" +
                    "\n" +
                    "                     },\n" +
                    "                                          {\n" +
                    "\n" +
                    "                                \"Notice_Board_Seq\":\"11245\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Title\":\"분리수거 안내\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Contents\":\"분리수거 철저하게 해주세요.\",\n" +
                    "\n" +
                    "                                \"Notice_Board_File_Path\":\"파일경로\",\n" +
                    "\n" +
                    "                                \"Reg_Date\":\"20200518154321500\"\n" +
                    "\n" +
                    "                     },\n" +
                    "                                          {\n" +
                    "\n" +
                    "                                \"Notice_Board_Seq\":\"11145\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Title\":\"택배 안내\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Contents\":\"택배 일주일 안에 받아가세요\",\n" +
                    "\n" +
                    "                                \"Notice_Board_File_Path\":\"파일경로\",\n" +
                    "\n" +
                    "                                \"Reg_Date\":\"20200517154321500\"\n" +
                    "\n" +
                    "                     },\n" +
                    "                                          {\n" +
                    "\n" +
                    "                                \"Notice_Board_Seq\":\"10345\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Title\":\"배달 음식 금지\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Contents\":\"배달 음식 금지\",\n" +
                    "\n" +
                    "                                \"Notice_Board_File_Path\":\"파일경로\",\n" +
                    "\n" +
                    "                                \"Reg_Date\":\"20200516154321500\"\n" +
                    "\n" +
                    "                     },\n" +
                    "                                          {\n" +
                    "\n" +
                    "                                \"Notice_Board_Seq\":\"10245\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Title\":\"화단 공사 안내\",\n" +
                    "\n" +
                    "                                \"Notice_Board_Contents\":\"화단 공사가 진행될 예정입니다.\",\n" +
                    "\n" +
                    "                                \"Notice_Board_File_Path\":\"파일경로\",\n" +
                    "\n" +
                    "                                \"Reg_Date\":\"20200510154321500\"\n" +
                    "\n" +
                    "                     }\n" +
                    "\n" +
                    "           ]\n" +
                    "}\n" +
                    "\n" +
                    "}";
            RemoteNoticeEntity entity = gson.fromJson(str, RemoteNoticeEntity.class);
            if ( callback == null || entity == null ) return;
            callback.onUpdateNotice(Mapper.mapToEntities(entity));
        });
    }

    // TEST
    List<VisitorEntity> visitors = new ArrayList<>();
    public void testVisitorUpdate() {
        visitors.add(new VisitorEntity(0, "/9j/4AAEAAD/2wBDAAYEBAQFBAYFBQYJBgUG/20190405132030.mp4", "02000011", "20200404132030", false));
        visitors.add(new VisitorEntity(1, "/9j/4AAEAAD/2wBDAAYEBAQFBAYFBQYJBgUG/20190305132030.mp4", "Door", "20200401132030", false));
        visitors.add(new VisitorEntity(2, "/9j/4AAEAAD/2wBDAAYEBAQFBAYFBQYJBgUG/20190205132030.mp4", "Door", "20200320132030", false));
        visitors.add(new VisitorEntity(3, "/9j/4AAEAAD/2wBDAAYEBAQFBAYFBQYJBgUG/20190105132030.mp4", "Motion", "20200319132030", false));
        visitors.add(new VisitorEntity(4, "/9j/4AAEAAD/2wBDAAYEBAQFBAYFBQYJBgUG/20180805132030.mp4", "Motion", "20200315132030", false));
        visitors.add(new VisitorEntity(5, "/9j/4AAEAAD/2wBDAAYEBAQFBAYFBQYJBgUG/20180605132030.mp4", "02000011", "20200311132030", false));
        visitors.add(new VisitorEntity(6, "/9j/4AAEAAD/2wBDAAYEBAQFBAYFBQYJBgUG/20180505132030.mp4", "02000011", "20200212132030", false));
        visitors.add(new VisitorEntity(7, "/9j/4AAEAAD/2wBDAAYEBAQFBAYFBQYJBgUG/20180405132030.mp4", "02000011", "20200211132030", false));
        visitors.add(new VisitorEntity(8, "/9j/4AAEAAD/2wBDAAYEBAQFBAYFBQYJBgUG/20180305132030.mp4", "Motion", "20200209132030", false));
        visitors.add(new VisitorEntity(9, "/9j/4AAEAAD/2wBDAAYEBAQFBAYFBQYJBgUG/20180205132030.mp4", "02000011", "20200101132030", false));
        if ( callback == null ) return;
        callback.onUpdateVisitor(visitors);
    }
    public void deleteVisitors(List<Integer> ids) {
        for ( int id : ids ) {
            VisitorEntity entity = findVisitor(id);
            if ( entity == null ) continue;
            visitors.remove(entity);
        }
    }
    public void deleteVisitor(int id) {
        VisitorEntity entity = findVisitor(id);
        if ( entity != null ) visitors.remove(entity);
    }

    public List<VisitorEntity> getVisitor() { return visitors; }

    private VisitorEntity findVisitor(int id) {
        for ( VisitorEntity visitor: visitors ) {
            if ( id == visitor.getId() ) return visitor;
        }
        return null;
    }

}
