package com.wallpad.parking.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.wallpad.IWallpadData;
import com.wallpad.parking.model.ParkingInfoModel;
import com.wallpad.parking.repository.common.Mapper;
import com.wallpad.parking.repository.local.dao.ParkingInfoDao;
import com.wallpad.parking.repository.local.entities.ParkingInfoEntity;
import com.wallpad.parking.repository.remote.IWallpadServiceHelper;
import com.wallpad.parking.repository.remote.TestHelper;
import com.wallpad.parking.repository.remote.entities.RemoteParkingEntity;

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
    private final TestHelper testHelper;

    private final ParkingInfoDao parkingInfoDao;
    private final LiveData<List<ParkingInfoModel>> parkingInfos;

    @Inject public Repository( TestHelper testHelper,
        IWallpadServiceHelper iWallpadServiceHelper,
        ParkingInfoDao parkingInfoDao
    ) {
        this.testHelper = testHelper;
        this.iWallpadServiceHelper = iWallpadServiceHelper;
        this.parkingInfoDao = parkingInfoDao;

        parkingInfos = Transformations.map(parkingInfoDao.getEntities(), entities -> {
            List<ParkingInfoModel> models = new ArrayList<>();
            for (ParkingInfoEntity entity: entities) models.add(Mapper.mapToParkingInfoModel(entity));
            return models;
        });
    }

    public void injectIWallpadService(IWallpadData iWallpadData) {
        iWallpadServiceHelper.setIWallpadService(iWallpadData, iCallback);
    }

    public LiveData<List<ParkingInfoModel>> getParkingInfo() {
        executorService.execute(iWallpadServiceHelper::refreshParkingLocationInfo);
        return parkingInfos;
    }
    private void setParkingInfo(List<ParkingInfoEntity> entities) {
        executorService.execute(() -> {
            parkingInfoDao.deleteEntities(Mapper.mapToParkingIds(entities));
            parkingInfoDao.insertEntities(entities);
        });
    }
    public void requestParkingLocation(int id) {
        testHelper.requestParkingLocation(id);
    }

    private final IWallpadServiceHelper.ICallback iCallback = entity -> {
        if ( entity == null ) return;
        executorService.execute(() -> setParkingInfo(Mapper.mapToParkingInfoEntity(entity)));
    };
}
