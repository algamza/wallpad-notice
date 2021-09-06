package com.wallpad.notice.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.wallpad.IWallpadData;
import com.wallpad.notice.model.DeliveryModel;
import com.wallpad.notice.model.NoticeModel;
import com.wallpad.notice.model.VisitorModel;
import com.wallpad.notice.model.VoteModel;
import com.wallpad.notice.repository.common.Mapper;
import com.wallpad.notice.repository.local.dao.DeliveryDao;
import com.wallpad.notice.repository.local.dao.NoticeDao;
import com.wallpad.notice.repository.local.dao.VisitorDao;
import com.wallpad.notice.repository.local.dao.VoteDao;
import com.wallpad.notice.repository.local.entities.DeliveryEntity;
import com.wallpad.notice.repository.local.entities.DeliveryReadEntity;
import com.wallpad.notice.repository.local.entities.NoticeEntity;
import com.wallpad.notice.repository.local.entities.NoticeReadEntity;
import com.wallpad.notice.repository.local.entities.ReadDeliveryEntity;
import com.wallpad.notice.repository.local.entities.ReadNoticeEntity;
import com.wallpad.notice.repository.local.entities.ReadVisitorEntity;
import com.wallpad.notice.repository.local.entities.ReadVoteEntity;
import com.wallpad.notice.repository.local.entities.VisitorEntity;
import com.wallpad.notice.repository.local.entities.VisitorReadEntity;
import com.wallpad.notice.repository.local.entities.VoteDetailEntity;
import com.wallpad.notice.repository.local.entities.VoteEntity;
import com.wallpad.notice.repository.local.entities.VoteInfoEntity;
import com.wallpad.notice.repository.remote.ContentProviderHelper;
import com.wallpad.notice.repository.remote.IWallpadServiceHelper;
import com.wallpad.notice.repository.remote.TestHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Repository {
    public interface Callback {
        void onLogin(boolean on);
    }
    private final ExecutorService executorService = Executors.newFixedThreadPool(6);
    private final IWallpadServiceHelper iWallpadServiceHelper;
    private final ContentProviderHelper contentProviderHelper;
    private final TestHelper testHelper;

    private final NoticeDao noticeDao;
    private final VoteDao voteDao;
    private final DeliveryDao deliveryDao;
    private final VisitorDao visitorDao;

    private final LiveData<List<NoticeModel>> notices;
    private final LiveData<List<VoteModel>> vote;
    private final LiveData<List<DeliveryModel>> deliveries;
    private final LiveData<List<VisitorModel>> visitors;
    private Callback callback;

    @Inject public Repository( TestHelper testHelper,
        ContentProviderHelper contentProviderHelper,
        IWallpadServiceHelper iWallpadServiceHelper,
                               NoticeDao noticeDao,
                               VoteDao voteDao,
                               DeliveryDao deliveryDao,
                               VisitorDao visitorDao
    ) {
        this.testHelper = testHelper;
        this.contentProviderHelper = contentProviderHelper;
        this.iWallpadServiceHelper = iWallpadServiceHelper;
        this.noticeDao = noticeDao;
        this.voteDao = voteDao;
        this.deliveryDao = deliveryDao;
        this.visitorDao = visitorDao;

        notices = Transformations.map(noticeDao.getNoticeReadEntities(), entities -> {
            List<NoticeModel> models = new ArrayList<>();
            for ( NoticeReadEntity entity : entities ) models.add(Mapper.mapToNoticeModel(entity));
            return models;
        });

        vote = Transformations.map(voteDao.getEntities(), entities -> {
            List<VoteModel> models = new ArrayList<>();
            for ( VoteEntity entity : entities ) models.add(Mapper.mapToModel(entity));
            return models;
        });

        deliveries = Transformations.map(deliveryDao.getDeliveryReadEntities(), entities -> {
            List<DeliveryModel> models = new ArrayList<>();
            for ( DeliveryReadEntity entity : entities ) models.add(Mapper.mapToDeliveryModel(entity));
            return models;
        });

        visitors = Transformations.map(visitorDao.getVisitorReadEntities(), entities -> {
            List<VisitorModel> models = new ArrayList<>();
            for ( VisitorReadEntity entity : entities ) models.add(Mapper.mapToVisitorModel(entity));
            return models;
        });
    }

    public void injectIWallpadService(IWallpadData iWallpadData, Callback callback) {
        this.callback = callback;
        injectIWallpadService(iWallpadData);
    }

    public void injectIWallpadService(IWallpadData iWallpadData) {
        contentProviderHelper.setCallback(contentProviderCallback);
        iWallpadServiceHelper.setIWallpadService(iWallpadData, iCallback);
    }

    private IWallpadServiceHelper.ICallback iCallback = new IWallpadServiceHelper.ICallback() {
        @Override
        public void onUpdateLogin(boolean on) {
            if ( !on || callback == null) return;
            callback.onLogin(on);
        }
    };

    public LiveData<List<NoticeModel>> getNotices() {
        executorService.execute(iWallpadServiceHelper::refreshNotificationInfo);
        return notices;
    }

    public void readNoticeNotification(int id) {
        executorService.execute(() -> noticeDao.insertNoticeReadEntity(new ReadNoticeEntity(id)));
    }

    public LiveData<List<VoteModel>> getVote() {
        executorService.execute(iWallpadServiceHelper::refreshVoteInfo);
        return vote;
    }

    public LiveData<VoteModel> getVoteDetail(int masterKey) {
        executorService.execute(()-> iWallpadServiceHelper.refreshVoteDetail(masterKey));
        return Transformations.map(voteDao.getVoteEntity(masterKey), Mapper::mapToModel);
    }

    public void readNoticeVote(int id) {
        executorService.execute(() -> voteDao.insertVoteReadEntity(new ReadVoteEntity(id)));
    }
    public void requestVote(int masterId, int voteCode) {
        iWallpadServiceHelper.requestVoting(masterId, voteCode);
    }

    public void refreshNotice() {
        executorService.execute(iWallpadServiceHelper::refreshParcelInfo);
        executorService.execute(iWallpadServiceHelper::refreshVoteInfo);
        executorService.execute(iWallpadServiceHelper::refreshNotificationInfo);
        executorService.execute(contentProviderHelper::requestVisitorInfo);
    }

    public LiveData<List<DeliveryModel>> getDeliveries() {
        executorService.execute(iWallpadServiceHelper::refreshParcelInfo);
        return deliveries;
    }

    public void readNoticeDelivery(long id) {
        executorService.execute(() -> deliveryDao.insertDeliveryReadEntity(new ReadDeliveryEntity(id)));
    }

    public LiveData<List<VisitorModel>> getVisitors() {
        executorService.execute(contentProviderHelper::requestVisitorInfo);
        return visitors;
    }
    public void readNoticeVisitor(String id) { executorService.execute(() -> visitorDao.insertVisitorReadEntity(new ReadVisitorEntity(id))); }

    public void deleteVisitors(List<String> ids, boolean isAll) {
        executorService.execute(() -> {
            contentProviderHelper.deleteVisitors(ids, isAll);
            contentProviderHelper.requestVisitorInfo();
        });
    }

    public void deleteVisitor(String id) {
        executorService.execute(() -> {
            contentProviderHelper.deleteVisitor(id);
            contentProviderHelper.requestVisitorInfo();
        });
    }

    public void refreshLogin() { iWallpadServiceHelper.refreshLogin(); }

    private final ContentProviderHelper.ICallback contentProviderCallback = new ContentProviderHelper.ICallback() {
        @Override
        public void onUpdateParcels(List<DeliveryEntity> entities) {
            if ( entities == null ) return;
            executorService.execute(() -> {
                deliveryDao.deleteNotInclude(Mapper.getDeliveryKeys(entities));
                deliveryDao.insertEntities(entities);
            });
        }

        @Override
        public void onUpdateParcelNotify(DeliveryEntity entity) {
            if ( entity == null ) return;
            executorService.execute(() -> deliveryDao.insertEntity(entity));
        }

        @Override
        public void onUpdateVotes(List<VoteInfoEntity> entities) {
            if ( entities == null ) return;
            executorService.execute(() -> {
                voteDao.deleteNotInEntities(Mapper.getVoteKeys(entities));
                voteDao.insertInfos(entities);
            });
        }

        @Override
        public void onUpdateDetailVotes(List<VoteDetailEntity> entities) {
            if ( entities == null ) return;
            executorService.execute(() -> voteDao.insertDetails(entities));
        }

        @Override
        public void onUpdateNotice(List<NoticeEntity> entities) {
            if ( entities == null ) return;
            executorService.execute(() -> {
                noticeDao.deleteNotInclude(Mapper.getNoticeIds(entities));
                noticeDao.insertEntities(entities);
            });
        }

        @Override
        public void onUpdateNoticeNotify(NoticeEntity entity) {
            if ( entity == null ) return;
            executorService.execute(() -> noticeDao.insertEntity(entity));
        }

        @Override
        public void onUpdateVisitor(List<VisitorEntity> entities) {
            if ( entities == null ) return;
            executorService.execute(() -> {
                visitorDao.deleteNotInclude(Mapper.getVisitorIds(entities));
                visitorDao.insertEntities(entities);
            });
        }
    };
}
