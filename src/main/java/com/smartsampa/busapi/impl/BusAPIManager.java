package com.smartsampa.busapi.impl;

import com.smartsampa.gtfsapi.GtfsAPI;
import com.smartsampa.gtfsapi.GtfsDownloader;
import com.smartsampa.gtfsapi.GtfsHandler;
import com.smartsampa.olhovivoapi.OlhoVivoAPI;
import com.smartsampa.shapefileapi.ShapefileAPI;

/**
 * Created by ruan0408 on 12/03/2016.
 */
public class BusAPIManager {

    public static GtfsAPI gtfs;
    public static OlhoVivoAPI olhovivo;
    public static ShapefileAPI shapefile;

    private static final BusAPIManager ourInstance = new BusAPIManager();

    private static String sptransLogin;
    private static String sptransPassword;
    private static String olhovivoKey;

    public static BusAPIManager getInstance() {
        return ourInstance;
    }

    private BusAPIManager() {}

    public void init() {
        GtfsDownloader gtfsDownloader = new GtfsDownloader(sptransLogin, sptransPassword);
        GtfsHandler gtfsHandler = new GtfsHandler(gtfsDownloader);
        gtfs = new GtfsAPI(gtfsHandler.getGtfsDao());

        //TODO put this in the same architecture as the others
        shapefile = new ShapefileAPI("faixa_onibus/sirgas_faixa_onibus.shp");

        olhovivo = new OlhoVivoAPI(olhovivoKey);
        olhovivo.authenticate();
    }

    public BusAPIManager setSptransLogin(String login) {
        sptransLogin = login;
        return this;
    }

    public BusAPIManager setSptransPassword(String password) {
        sptransPassword = password;
        return this;
    }

    public BusAPIManager setOlhovivoKey(String key) {
        olhovivoKey = key;
        return this;
    }
}