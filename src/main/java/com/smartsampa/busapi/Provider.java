package com.smartsampa.busapi;

import com.smartsampa.gtfsapi.GtfsAPI;
import com.smartsampa.olhovivoapi.OlhoVivoAPI;

/**
 * Created by ruan0408 on 4/06/2016.
 */
public final class Provider {

    private static OlhoVivoAPI olhovivoAPI;
    private static GtfsAPI gtfsAPI;

    public static OlhoVivoAPI getOlhovivoAPI() {
        return olhovivoAPI;
    }

    public static void setOlhovivoAPI(OlhoVivoAPI olhovivo) {
        olhovivoAPI = olhovivo;
    }

    public static GtfsAPI getGtfsAPI() {
        return gtfsAPI;
    }

    public static void setGtfsAPI(GtfsAPI gtfs) {
        gtfsAPI = gtfs;
    }
}
