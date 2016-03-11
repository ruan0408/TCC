package com.intellij.gtfsapi;

import com.intellij.utils.APIConnectionException;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 27/02/2016.
 */
public class GtfsAPI {

    private GtfsDaoImpl gtfsAcessor;
    private GtfsHandler gtfsHandler;

    public GtfsAPI(String login, String password) {
        gtfsHandler = new GtfsHandler(login, password);
    }

    public void init() {
        try {
            gtfsAcessor = gtfsHandler.getGtfsAcessor();
        } catch (IOException e) {
            throw APIConnectionException.throwGTFSConnectionException();
        }
    }

    public <T> List<T> filterToList(String methodName, Predicate<T> predicate) {
        try {
            Method method = gtfsAcessor.getClass().getMethod(methodName);
            List<T> filtered = ((Collection<T>)method.invoke(gtfsAcessor))
                    .parallelStream()
                    .filter(predicate)
                    .collect(Collectors.toList());

            return filtered;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T filterToElement(String methodName, Predicate<T> predicate) {
        List<T> filtered = filterToList(methodName, predicate);

        if (filtered.isEmpty())
            return null;

        return filtered.get(0);
    }

}
