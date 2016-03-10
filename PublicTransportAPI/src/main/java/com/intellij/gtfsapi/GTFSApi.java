package com.intellij.gtfsapi;

import com.intellij.utils.APIConnectionException;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.serialization.GtfsReader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ruan0408 on 27/02/2016.
 */
public class GTFSApi {

    private static final int UPDATE_INTERVAL = 3;//days
    private static final String SMART_SAMPA_PATH = System.getProperty("user.home")+"/"+"SmartSampa/";
    private static final String GTFS_PATH = SMART_SAMPA_PATH + "gtfs-sp";
    private static final String DOWNLOAD_GTFS_SCRIPT_PATH =
            GTFSApi.class.getClassLoader().getResource("downloadGtfs.py").getPath();

    private GtfsReader gtfsReader;
    private GtfsDaoImpl store;
    private String login;
    private String password;

    public GTFSApi(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void init() {
        gtfsReader = new GtfsReader();
        store = new GtfsDaoImpl();
        checkDirs();
        updateGtfs();
        try {
            gtfsReader.setInputLocation(new File(GTFS_PATH));
            gtfsReader.setEntityStore(store);
            gtfsReader.run();
        } catch (IOException e) {
            throw APIConnectionException.throwGTFSConnectionException();
        }
    }

    public <T> List<T>
    filterToList(String methodName, Predicate<T> filter) {
        try {
            Method method = store.getClass().getMethod(methodName);
            Stream<T> all = ((Collection<T>)method.invoke(store)).stream();

            List<T> filtered = all.filter(filter).collect(Collectors.toList());
            return filtered;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T filterToElement(String methodName, Predicate<T> filter) {
        List<T> filtered = filterToList(methodName, filter);

        if (filtered.isEmpty()) return null;
        return filtered.get(0);
    }

    private boolean checkDirs() {
        File sampaDir = new File(SMART_SAMPA_PATH);
        if (sampaDir.exists()) return true;
        if (sampaDir.mkdirs()) return true;
        return false;
    }

    private void updateGtfs() {
        File gtfsDir = new File(GTFS_PATH);
        long currentTime = System.currentTimeMillis();
        long interval = Duration.ofDays(UPDATE_INTERVAL).toMillis();

        if (!gtfsDir.exists() || currentTime - gtfsDir.lastModified() > interval)
            downloadGtfs();

    }

    private void downloadGtfs() {

        ProcessBuilder pb = new ProcessBuilder("python", DOWNLOAD_GTFS_SCRIPT_PATH,
                login, password, GTFS_PATH);
        try {
            Process process = pb.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            throw APIConnectionException.throwGTFSConnectionException();
        }

    }
}
