package com.wallpad.notice.view.common;

public class Mapper {
    public static String mapToChargingTime(String start) {
        String time = "";
        if ( start == null || start.length() < 14 ) return time;
        time = start;
        int hour = Integer.parseInt(time.substring(8, 10));
        String apm = " AM";
        if ( hour > 12 ) {
            apm = " PM";
            hour = hour - 12;
        }
        time = time.substring(0, 4)+"."+time.substring(4, 6)+"."+time.substring(6, 8)+apm+String.format("%02d", hour)+":"+time.substring(10, 12);
        return time;
    }
}
