package com.wallpad.parking.repository.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ParkingInfoEntity {
    @PrimaryKey
    private int id;

    private String carNumber;
    private String parkingTime;
    private String parkingPlace;
    private String parkingUri;

    public ParkingInfoEntity(int id, String carNumber, String parkingTime, String parkingPlace, String parkingUri) {
        this.id = id;
        this.carNumber = carNumber;
        this.parkingTime = parkingTime;
        this.parkingPlace = parkingPlace;
        this.parkingUri = parkingUri;
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

    public String getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(String parkingTime) {
        this.parkingTime = parkingTime;
    }

    public String getParkingPlace() {
        return parkingPlace;
    }

    public void setParkingPlace(String parkingPlace) {
        this.parkingPlace = parkingPlace;
    }

    public String getParkingUri() {
        return parkingUri;
    }

    public void setParkingUri(String parkingUri) {
        this.parkingUri = parkingUri;
    }
}
