package com.wallpad.parking.repository.remote.entities;

import java.util.List;

public class RemoteParkingEntity {
    private Resource Resource;

    public RemoteParkingEntity(RemoteParkingEntity.Resource resource) {
        Resource = resource;
    }

    public RemoteParkingEntity.Resource getResource() {
        return Resource;
    }

    public void setResource(RemoteParkingEntity.Resource resource) {
        Resource = resource;
    }

    public static class Resource {
        private String Total_Count;
        private List<Delivery_Info_List> Delivery_Info_List;

        public Resource(String total_Count, List<RemoteParkingEntity.Resource.Delivery_Info_List> Delivery_Info_List) {
            Total_Count = total_Count;
            this.Delivery_Info_List = Delivery_Info_List;
        }

        public String getTotal_Count() {
            return Total_Count;
        }

        public void setTotal_Count(String total_Count) {
            Total_Count = total_Count;
        }

        public List<RemoteParkingEntity.Resource.Delivery_Info_List> getDelivery_Info_List() {
            return Delivery_Info_List;
        }

        public void setDelivery_Info_List(List<RemoteParkingEntity.Resource.Delivery_Info_List> Delivery_Info_List) {
            this.Delivery_Info_List = Delivery_Info_List;
        }

        public static class Delivery_Info_List {
            private String Car_No;
            private String Coordinate_X;
            private String Coordinate_Y;
            private String Parking_Location;
            private String Parking_Location_Image;
            private String Parking_Time;

            public Delivery_Info_List(String car_No, String coordinate_X, String coordinate_Y, String parking_Location, String parking_Location_Image, String parking_Time) {
                Car_No = car_No;
                Coordinate_X = coordinate_X;
                Coordinate_Y = coordinate_Y;
                Parking_Location = parking_Location;
                Parking_Location_Image = parking_Location_Image;
                Parking_Time = parking_Time;
            }

            public String getCar_No() {
                return Car_No;
            }

            public void setCar_No(String car_No) {
                Car_No = car_No;
            }

            public String getCoordinate_X() {
                return Coordinate_X;
            }

            public void setCoordinate_X(String coordinate_X) {
                Coordinate_X = coordinate_X;
            }

            public String getCoordinate_Y() {
                return Coordinate_Y;
            }

            public void setCoordinate_Y(String coordinate_Y) {
                Coordinate_Y = coordinate_Y;
            }

            public String getParking_Location() {
                return Parking_Location;
            }

            public void setParking_Location(String parking_Location) {
                Parking_Location = parking_Location;
            }

            public String getParking_Location_Image() {
                return Parking_Location_Image;
            }

            public void setParking_Location_Image(String parking_Location_Image) {
                Parking_Location_Image = parking_Location_Image;
            }

            public String getParking_Time() {
                return Parking_Time;
            }

            public void setParking_Time(String parking_Time) {
                Parking_Time = parking_Time;
            }
        }
    }
}
