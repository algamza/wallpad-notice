package com.wallpad.notice.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.wallpad.IWallpadData;
import com.wallpad.notice.model.DeliveryModel;
import com.wallpad.notice.model.NoticeModel;
import com.wallpad.notice.model.VoteModel;
import com.wallpad.notice.model.VisitorModel;
import com.wallpad.notice.repository.common.Mapper;
import com.wallpad.notice.repository.local.dao.DeliveryDao;
import com.wallpad.notice.repository.local.dao.NoticeDao;
import com.wallpad.notice.repository.local.dao.VoteDao;
import com.wallpad.notice.repository.local.dao.VisitorDao;
import com.wallpad.notice.repository.local.entities.DeliveryEntity;
import com.wallpad.notice.repository.local.entities.NoticeEntity;
import com.wallpad.notice.repository.local.entities.VoteDetailEntity;
import com.wallpad.notice.repository.local.entities.VoteEntity;
import com.wallpad.notice.repository.local.entities.VisitorEntity;
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

        notices = Transformations.map(noticeDao.getEntities(), entities -> {
            List<NoticeModel> models = new ArrayList<>();
            for ( NoticeEntity entity : entities ) models.add(Mapper.mapToNoticeModel(entity));
            return models;
        });

        vote = Transformations.map(voteDao.getEntities(), entities -> {
            List<VoteModel> models = new ArrayList<>();
            for ( VoteEntity entity : entities ) models.add(Mapper.mapToModel(entity));
            return models;
        });

        deliveries = Transformations.map(deliveryDao.getEntities(), entities -> {
            List<DeliveryModel> models = new ArrayList<>();
            for ( DeliveryEntity entity : entities ) models.add(Mapper.mapToDeliveryModel(entity));
            return models;
        });

        visitors = Transformations.map(visitorDao.getEntities(), entities -> {
            List<VisitorModel> models = new ArrayList<>();
            for ( VisitorEntity entity : entities ) models.add(Mapper.mapToVisitorModel(entity));
            return models;
        });
    }

    public void injectIWallpadService(IWallpadData iWallpadData) {
        contentProviderHelper.setCallback(contentProviderCallback);
        iWallpadServiceHelper.setIWallpadService(iWallpadData);
    }

    public LiveData<List<NoticeModel>> getNotices() {
        // TODO:
        executorService.execute(contentProviderHelper::testNoticeUpdate);
        return notices;
    }

    public void readNoticeNotification(int id) {
        executorService.execute(() -> noticeDao.updateRead(id, true));
    }

    public LiveData<List<VoteModel>> getVote() {
        executorService.execute(iWallpadServiceHelper::refreshVoteInfo);
        return vote;
    }
    public void readNoticeVote(int id) {
        executorService.execute(() -> voteDao.updateRead(id, true));
    }
    public void requestVote(int masterId, int voteCode) {
        iWallpadServiceHelper.requestVoting(masterId, voteCode);
    }

    public LiveData<List<DeliveryModel>> getDeliveries() {
        executorService.execute(iWallpadServiceHelper::refreshParcelInfo);
        return deliveries;
    }

    public void readNoticeDelivery(long id) {
        executorService.execute(() -> deliveryDao.updateRead(id, true));
    }

    public LiveData<List<VisitorModel>> getVisitors() {
        executorService.execute(contentProviderHelper::testVisitorUpdate);
        return visitors;
    }
    public void readNoticeVisitor(int id) { executorService.execute(() -> visitorDao.updateRead(id, true)); }

    public void deleteVisitors(List<Integer> ids) {
        // TODO:
        executorService.execute(() -> {
            contentProviderHelper.deleteVisitors(ids);
            visitorDao.deleteNotInclude(Mapper.getVisitorIds(contentProviderHelper.getVisitor()));
            visitorDao.insertEntities(contentProviderHelper.getVisitor());
        });
    }

    public void deleteVisitor(int id) {
        // TODO:
        executorService.execute(() -> {
            contentProviderHelper.deleteVisitor(id);
            visitorDao.deleteNotInclude(Mapper.getVisitorIds(contentProviderHelper.getVisitor()));
            visitorDao.insertEntities(contentProviderHelper.getVisitor());
        });
    }

    private final ContentProviderHelper.ICallback contentProviderCallback = new ContentProviderHelper.ICallback() {
        @Override
        public void onUpdateParcels(List<DeliveryEntity> entities) {
            if ( entities == null || entities.size() == 0 ) return;
            executorService.execute(() -> {
                deliveryDao.deleteNotInclude(Mapper.getDeliveryKeys(entities));
                deliveryDao.insertEntities(entities);
            });
        }

        @Override
        public void onUpdateVotes(List<VoteInfoEntity> entities) {
            if ( entities == null || entities.size() == 0 ) return;
            executorService.execute(() -> {
                voteDao.deleteNotInEntities(Mapper.getVoteKeys(entities));
                voteDao.insertInfos(entities);
                for ( VoteInfoEntity entity : entities ) {
                    iWallpadServiceHelper.refreshVoteDetail(String.valueOf(entity.getMasterKey()));
                }
            });
        }

        @Override
        public void onUpdateDetailVotes(List<VoteDetailEntity> entities) {
            if ( entities == null || entities.size() == 0 ) return;
            executorService.execute(() -> {
                voteDao.insertDetails(entities);
            });
        }

        @Override
        public void onUpdateNotice(List<NoticeEntity> entities) {
            if ( entities == null || entities.size() == 0 ) return;
            executorService.execute(() -> {
                noticeDao.deleteNotInclude(Mapper.getNoticeIds(entities));
                noticeDao.insertEntities(entities);
            });
        }

        @Override
        public void onUpdateVisitor(List<VisitorEntity> entities) {
            if ( entities == null || entities.size() == 0 ) return;
            executorService.execute(() -> {
                visitorDao.deleteNotInclude(Mapper.getVisitorIds(entities));
                visitorDao.insertEntities(entities);
            });
        }
    };
}
