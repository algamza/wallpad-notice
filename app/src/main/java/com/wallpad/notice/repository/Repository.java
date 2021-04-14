package com.wallpad.notice.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.wallpad.IWallpadData;
import com.wallpad.notice.model.DeliveryModel;
import com.wallpad.notice.model.NoticeModel;
import com.wallpad.notice.model.ReferendumModel;
import com.wallpad.notice.model.VisitorModel;
import com.wallpad.notice.repository.common.Mapper;
import com.wallpad.notice.repository.local.dao.DeliveryDao;
import com.wallpad.notice.repository.local.dao.NoticeDao;
import com.wallpad.notice.repository.local.dao.ReferendumDao;
import com.wallpad.notice.repository.local.dao.VisitorDao;
import com.wallpad.notice.repository.local.entities.DeliveryEntity;
import com.wallpad.notice.repository.local.entities.NoticeEntity;
import com.wallpad.notice.repository.local.entities.ReferendumEntity;
import com.wallpad.notice.repository.local.entities.VisitorEntity;
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
    private final ReferendumDao referendumDao;
    private final DeliveryDao deliveryDao;
    private final VisitorDao visitorDao;

    private final LiveData<List<NoticeModel>> notices;
    private final LiveData<List<ReferendumModel>> referendums;
    private final LiveData<List<DeliveryModel>> deliveries;
    private final LiveData<List<VisitorModel>> visitors;

    @Inject public Repository( TestHelper testHelper,
        ContentProviderHelper contentProviderHelper,
        IWallpadServiceHelper iWallpadServiceHelper,
                               NoticeDao noticeDao,
                               ReferendumDao referendumDao,
                               DeliveryDao deliveryDao,
                               VisitorDao visitorDao
    ) {
        this.testHelper = testHelper;
        this.contentProviderHelper = contentProviderHelper;
        this.iWallpadServiceHelper = iWallpadServiceHelper;
        this.noticeDao = noticeDao;
        this.referendumDao = referendumDao;
        this.deliveryDao = deliveryDao;
        this.visitorDao = visitorDao;

        notices = Transformations.map(noticeDao.getEntities(), entities -> {
            List<NoticeModel> models = new ArrayList<>();
            for ( NoticeEntity entity : entities ) models.add(Mapper.mapToNoticeModel(entity));
            return models;
        });

        referendums = Transformations.map(referendumDao.getEntities(), entities -> {
            List<ReferendumModel> models = new ArrayList<>();
            for ( ReferendumEntity entity : entities ) models.add(Mapper.mapToReferendumModel(entity));
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
        //executorService.execute(() -> setNotices(contentProviderHelper.getNotices()));
        return notices;
    }
    public void setNotices(List<NoticeModel> models) {
        List<NoticeEntity> entities = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        for ( NoticeModel model : models ) {
            entities.add(Mapper.mapToNoticeEntity(model));
            ids.add(model.getId());
        }
        executorService.execute(() -> {
            noticeDao.deleteNotInclude(ids);
            noticeDao.insertEntities(entities);
        });
    }
    public void readNoticeNotification(int id) {
        executorService.execute(() -> noticeDao.updateRead(id, true));
    }

    public LiveData<List<ReferendumModel>> getReferendums() {
        //executorService.execute(() -> setReferendums(contentProviderHelper.getReferendums()));
        return referendums;
    }
    public void setReferendums(List<ReferendumModel> models) {
        List<ReferendumEntity> entities = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        for ( ReferendumModel model : models ) {
            entities.add(Mapper.mapToReferendumEntity(model));
            ids.add(model.getId());
        }
        executorService.execute(() -> {
            referendumDao.deleteNotInclude(ids);
            referendumDao.insertEntities(entities);
        });
    }
    public void readNoticeReferendums(int id) {
        executorService.execute(() -> referendumDao.updateRead(id, true));
    }

    public LiveData<List<DeliveryModel>> getDeliveries() {
        //executorService.execute(() -> setDeliveries(contentProviderHelper.getDeliveries()));
        return deliveries;
    }
    private void setDeliveries(List<DeliveryModel> models) {
        List<DeliveryEntity> entities = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        for ( DeliveryModel model : models ) {
            entities.add(Mapper.mapToDeliveryEntity(model));
            ids.add(model.getId());
        }
        executorService.execute(() -> {
            deliveryDao.deleteNotInclude(ids);
            deliveryDao.insertEntities(entities);
        });
    }
    public void readNoticeDelivery(int id) {
        executorService.execute(() -> deliveryDao.updateRead(id, true));
    }

    public LiveData<List<VisitorModel>> getVisitors() {
        //executorService.execute(() -> setVisitors(contentProviderHelper.getVisitors()));
        return visitors;
    }
    public void setVisitors(List<VisitorModel> models) {
        List<VisitorEntity> entities = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        for ( VisitorModel model : models ) {
            entities.add(Mapper.mapToVisitorEntity(model));
            ids.add(model.getId());
        }
        executorService.execute(() -> {
            visitorDao.deleteNotInclude(ids);
            visitorDao.insertEntities(entities);
        });
    }
    public void readNoticeVisitor(int id) {
        executorService.execute(() -> visitorDao.updateRead(id, true));
    }


    private final ContentProviderHelper.ICallback contentProviderCallback = new ContentProviderHelper.ICallback() {
        @Override
        public void onUpdateParcels(List<DeliveryEntity> entities) {
            if ( entities == null || entities.size() == 0 ) return;
            executorService.execute(() -> {
                deliveryDao.deleteNotInclude(Mapper.getKeys(entities));
                deliveryDao.insertEntities(entities);
            });
        }
    };
}
