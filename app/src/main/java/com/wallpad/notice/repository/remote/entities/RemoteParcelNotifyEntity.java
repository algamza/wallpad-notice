package com.wallpad.notice.repository.remote.entities;

public class RemoteParcelNotifyEntity {
    private String Delivery_Type;
    private String Delivery_Box_Info;
    private String Delivery_Event_Type;
    private String Event_Time;

    public RemoteParcelNotifyEntity(String delivery_Type, String delivery_Box_Info, String delivery_Event_Type, String event_Time) {
        Delivery_Type = delivery_Type;
        Delivery_Box_Info = delivery_Box_Info;
        Delivery_Event_Type = delivery_Event_Type;
        Event_Time = event_Time;
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

    public String getEvent_Time() {
        return Event_Time;
    }

    public void setEvent_Time(String event_Time) {
        Event_Time = event_Time;
    }
}
