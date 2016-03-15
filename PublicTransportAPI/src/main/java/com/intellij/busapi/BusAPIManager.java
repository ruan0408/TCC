package com.intellij.busapi;

import com.intellij.gtfsapi.GtfsAPI;
import com.intellij.olhovivoapi.OlhoVivoAPI;

/**
 * Created by ruan0408 on 12/03/2016.
 */
public class BusAPIManager {
    private static final BusAPIManager ourInstance = new BusAPIManager();

    protected static GtfsAPI gtfs;
    protected static OlhoVivoAPI olhovivo;

    private static String sptransLogin;
    private static String sptransPassword;
    private static String olhovivoKey;

    public static BusAPIManager getInstance() {
        return ourInstance;
    }

    private BusAPIManager() {}

    public void init() {
        gtfs = GtfsAPI.getInstance();
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

    public String getSptransLogin() {
        return sptransLogin;
    }

    public String getSptransPassword() {
        return sptransPassword;
    }

    public String getOlhovivoKey() {
        return olhovivoKey;
    }
}
