package com.wallpad.parking.view.parking;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.wallpad.parking.model.ParkingInfoModel;
import com.wallpad.parking.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class ParkingViewModel extends ViewModel {
    private Repository repository;
    private final LiveData<List<ParkingInfo>> infos;

    @ViewModelInject
    public ParkingViewModel(Repository repository) {
        this.repository = repository;
        infos = Transformations.map(repository.getParkingInfo(), models -> {
            List<ParkingInfo> list = new ArrayList<>();
            for (ParkingInfoModel model : models) {
                list.add(new ParkingInfo(callback, model.getId(), model.getCarNumber(), model.getParkingPlace(), mapToChargingTime(model.getParkingTime()), model.getParkingUri()));
            }
            return list;
        });
    }

    public LiveData<List<ParkingInfo>> getInfos() { return infos; }

    private final ParkingInfo.Callback callback = uri -> {
        // TODO :
    };

    private String mapToChargingTime(String start) {
        String time = "";
        if ( start.length() < 14 ) return time;
        time = start;
        int hour = Integer.parseInt(time.substring(8, 10));
        String apm = " AM";
        if ( hour > 12 ) apm = " PM";
        time = time.substring(0, 4)+"."+time.substring(4, 6)+"."+time.substring(6, 8)+apm+hour+":"+time.substring(10, 12);
        return time;
    }

    public static class ParkingInfo {
        public interface Callback {
            void onClickShowLocation(String uri);
        }
        private Callback callback;
        private int id;
        private String carNumber;
        private String parkingLocation;
        private String parkingTime;
        private String parkingLocationUri;

        public ParkingInfo(Callback callback, int id, String carNumber, String parkingLocation, String parkingTime, String parkingLocationUri) {
            this.callback = callback;
            this.id = id;
            this.carNumber = carNumber;
            this.parkingLocation = parkingLocation;
            this.parkingTime = parkingTime;
            this.parkingLocationUri = parkingLocationUri;
        }

        public Callback getCallback() {
            return callback;
        }

        public void setCallback(Callback callback) {
            this.callback = callback;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }

        public String getParkingLocation() {
            return parkingLocation;
        }

        public void setParkingLocation(String parkingLocation) {
            this.parkingLocation = parkingLocation;
        }

        public String getParkingTime() {
            return parkingTime;
        }

        public void setParkingTime(String parkingTime) {
            this.parkingTime = parkingTime;
        }

        public String getParkingLocationUri() {
            return parkingLocationUri;
        }

        public void setParkingLocationUri(String parkingLocationUri) {
            this.parkingLocationUri = parkingLocationUri;
        }
    }

}