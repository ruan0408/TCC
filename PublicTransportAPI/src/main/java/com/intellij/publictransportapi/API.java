package com.intellij.publictransportapi;

import com.intellij.olhovivoapi.*;
import com.intellij.openapi.util.Pair;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.serialization.GtfsReader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class API {

    private static final String GTFS_PATH = "gtfs-sp";//TODO remove this
    protected static OlhoVivoAPI olhoVivoAPI;
    private static GtfsReader gtfsReader;
    protected static GtfsDaoImpl store;

    public static void init(String key) {
        olhoVivoAPI = new OlhoVivoAPI(key);
        olhoVivoAPI.authenticate();//TODO not sure about this.
        gtfsReader = new GtfsReader();

        store = new GtfsDaoImpl();
        try {
            gtfsReader.setInputLocation(new File(API.class.getClassLoader().
                    getResource(GTFS_PATH).getPath()));
            gtfsReader.setEntityStore(store);
            gtfsReader.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Trip> searchTrip(String term) {
        BusLine[] busLines = olhoVivoAPI.searchBusLines(term);
        List<Trip> trips = new ArrayList<>(busLines.length);

        for (int i = 0; i < busLines.length; i++) {
            String fullNumberSign = busLines[i].getNumberSign()+"-"+busLines[i].getType();
            Route parentRoute = Route.buildFrom(fullNumberSign);

            if (busLines[i].getHeading() == 1) trips.add(parentRoute.getTripMTST());
            else trips.add(parentRoute.getTripSTMT());
        }
        return trips;
    }

    public static List<Corridor> getAllCorridors() {
        BusCorridor[] corridors = olhoVivoAPI.getAllBusCorridors();
        return Corridor.convert(corridors);
    }

    protected static String getTripDetails(int internalTripId) {
        return olhoVivoAPI.getBusLineDetails(internalTripId);
    }

    protected static BusStop[] getStopsByTrip(int internalTripId) {
        return olhoVivoAPI.searchBusStopsByLine(internalTripId);
    }

    protected static BusLinePositions getBusesByTrip(int internalTripId) {
        return olhoVivoAPI.searchBusesByLine(internalTripId);
    }

    protected static ForecastWithStopAndLine
    getForecastByStopAndTrip(int internalTripId, int stopId) {
        return olhoVivoAPI.getForecastWithStopAndLine(stopId, internalTripId);
    }

    protected static ForecastWithLine
    getForecastByTrip(int internalTripId) {
        return olhoVivoAPI.getForecastWithLine(internalTripId);
    }

    protected static ForecastWithStop
    getForecastByStop(int stopId) {
        return olhoVivoAPI.getForecastWithStop(stopId);
    }

    protected static BusStop[] getStopsByCorridor(int corridorId) {
        return olhoVivoAPI.searchBusStopsByCorridor(corridorId);
    }

    protected static Pair<BusLine, BusLine> getBothTrips(String fullNumberSign) {
        try {
            return olhoVivoAPI.getBothTrips(fullNumberSign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static <T> List<T> filterGtfsToList(String methodName, Predicate<T> filter) {
        try {
            Method method = API.store.getClass().getMethod(methodName);
            Stream<T> all = ((Collection<T>)method.invoke(API.store)).stream();

            List<T> filtered = all.filter(filter).collect(Collectors.toList());
            return filtered;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static  <T> T filterGtfsToElement(String methodName, Predicate<T> filter) {
        List<T> filtered = filterGtfsToList(methodName, filter);

        if (filtered.isEmpty()) return null;
        return filtered.get(0);
    }


}
