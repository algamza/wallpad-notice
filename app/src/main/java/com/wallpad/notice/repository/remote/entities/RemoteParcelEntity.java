package com.wallpad.notice.repository.remote.entities;

import java.util.List;

public class RemoteParcelEntity {
    private int Error_Cd;
    private String Error_Nm;
    private Resource Resource;

    public RemoteParcelEntity(int error_Cd, String error_Nm, Resource resource) {
        Error_Cd = error_Cd;
        Error_Nm = error_Nm;
        Resource = resource;
    }

    public int getError_Cd() {
        return Error_Cd;
    }

    public void setError_Cd(int error_Cd) {
        Error_Cd = error_Cd;
    }

    public String getError_Nm() {
        return Error_Nm;
    }

    public void setError_Nm(String error_Nm) {
        Error_Nm = error_Nm;
    }

    public Resource getResource() {
        return Resource;
    }

    public void setResource(Resource resource) {
        Resource = resource;
    }

    public static class Resource {
        private String Total_Count;
        private List<Delivery_Info_List> Delivery_Info_List;

        public Resource(String total_Count, List<RemoteParcelEntity.Resource.Delivery_Info_List> delivery_Info_List) {
            Total_Count = total_Count;
            Delivery_Info_List = delivery_Info_List;
        }

        public String getTotal_Count() {
            return Total_Count;
        }

        public void setTotal_Count(String total_Count) {
            Total_Count = total_Count;
        }

        public List<RemoteParcelEntity.Resource.Delivery_Info_List> getDelivery_Info_List() {
            return Delivery_Info_List;
        }

        public void setDelivery_Info_List(List<RemoteParcelEntity.Resource.Delivery_Info_List> delivery_Info_List) {
            Delivery_Info_List = delivery_Info_List;
        }

        public static class Delivery_Info_List {
            private String Delivery_Type;
            private String Delivery_Box_Info;
            private String Delivery_Event_Type;
            private String Arrive_Time;
            private String Receive_Time;

            public Delivery_Info_List(String delivery_Type, String delivery_Box_Info, String delivery_Event_Type, String arrive_Time, String receive_Time) {
                Delivery_Type = delivery_Type;
                Delivery_Box_Info = delivery_Box_Info;
                Delivery_Event_Type = delivery_Event_Type;
                Arrive_Time = arrive_Time;
                Receive_Time = receive_Time;
            }

            public String getDelivery_Type() {
                return Delivery_Type;
            }

            public void setDelivery_Type(String delivery_Type) {
                Delivery_Type = delivery_Type;
            }

            public String getDelivery_Box_Info() {
                return Delivery_Box_Info;
            }

            public void setDelivery_Box_Info(String delivery_Box_Info) {
                Delivery_Box_Info = delivery_Box_Info;
            }

            public String getDelivery_Event_Type() {
                return Delivery_Event_Type;
            }

            public void setDelivery_Event_Type(String delivery_Event_Type) {
                Delivery_Event_Type = delivery_Event_Type;
            }

            public String getArrive_Time() {
                return Arrive_Time;
            }

            public void setArrive_Time(String arrive_Time) {
                Arrive_Time = arrive_Time;
            }

            public String getReceive_Time() {
                return Receive_Time;
            }

            public void setReceive_Time(String receive_Time) {
                Receive_Time = receive_Time;
            }
        }
    }
}
