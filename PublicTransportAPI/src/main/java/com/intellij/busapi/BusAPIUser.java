package com.intellij.busapi;

import com.intellij.gtfsapi.GtfsAPI;
import com.intellij.olhovivoapi.OlhoVivoAPI;

/**
 * Created by ruan0408 on 14/03/2016.
 */
public class BusAPIUser {

    protected static final OlhoVivoAPI olhoVivoAPI = BusAPIManager.olhovivo;
    protected static final GtfsAPI gtfsAPI = BusAPIManager.gtfs;
}
