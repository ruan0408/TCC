package com.intellij.publictransportapi.implementation;

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

    //TODO refactor this method
    public static List<Trip> searchTrip(String term) {
        BusLine[] busLines = olhoVivoAPI.searchBusLines(term);
        List<Trip> trips = new ArrayList<>(busLines.length);

        for (int i = 0; i < busLines.length; i++) {
            try {
                Pair<BusLine, BusLine> pair = olhoVivoAPI.getBothTrips(busLines[i].getNumberSign());
                Trip tripMTST = new Trip();
                Trip tripSTMT = null;

                Route route = new Route(busLines[i].getNumberSign(), busLines[i].getType(),
                        busLines[i].isCircular(), busLines[i].getInfo());
                route.setSTMT(tripSTMT);

                tripMTST.setInternalId(pair.getFirst().getCode());
                tripMTST.setDestinationSign(pair.getFirst().getDestinationSignMTST());
                tripMTST.setRoute(route);

                if (pair.getSecond() != null) {
                    tripSTMT = new Trip();
                    tripSTMT.setInternalId(pair.getSecond().getCode());
                    tripSTMT.setDestinationSign(pair.getSecond().getDestinationSignSTMT());
                    tripSTMT.setRoute(route);
                }
                route.setSTMT(tripSTMT);

                if (busLines[i].getHeading() == 1) trips.add(tripMTST);
                else trips.add(tripSTMT);
            } catch (Exception e) {
                System.err.println("Error when getting both trips");
                e.printStackTrace();
            }
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

    protected static BusStop[] getStopsByCorridor(int corridorId) {
        return olhoVivoAPI.searchBusStopsByCorridor(corridorId);
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
