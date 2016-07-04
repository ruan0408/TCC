package com.smartsampa.busapi;

import java.time.LocalTime;

/**
 * Created by ruan0408 on 13/03/2016.
 */
public class BusAPITestUtils {

    public static void setUpDataSources() {
        BusAPI.setSptransLogin("ruan0408");
        BusAPI.setSptransPassword("costaruan");
        BusAPI.setOlhovivoKey("3de5ce998806e0c0750b1434e17454b6490ccf0a595f3884795da34460a7e7b3");
        BusAPI.initialize();
    }

    public static boolean isAfter4amAndBeforeMidnight() {
        LocalTime now = LocalTime.now();
        return now.isAfter(LocalTime.of(4,0)) && now.isBefore(LocalTime.MAX);
    }
}
