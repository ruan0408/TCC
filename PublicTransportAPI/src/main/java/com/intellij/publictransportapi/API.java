package com.intellij.publictransportapi;

import com.intellij.utils.APIConnectionException;
import com.intellij.gtfsapi.GTFSApi;
import com.intellij.olhovivoapi.*;
import com.intellij.openapi.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class API {

    protected static OlhoVivoAPI olhoVivoApi;
    protected static GTFSApi gtfsApi;

    public static void
    init(String key, String login, String password) throws APIConnectionException {
        olhoVivoApi = OlhoVivoAPI.getInstance(key);
        gtfsApi = GTFSApi.getInstance(login, password);
    }

    public static List<Trip> searchTrip(String term) throws APIConnectionException {
        BusLine[] busLines = olhoVivoApi.searchBusLines(term);
        List<Trip> trips = new ArrayList<>(busLines.length);

        for (int i = 0; i < busLines.length; i++) {
            String fullNumberSign = busLines[i].getNumberSign()+"-"+busLines[i].getType();
            Route parentRoute = Route.buildFrom(fullNumberSign);

            if (busLines[i].getHeading() == 1) trips.add(parentRoute.getTripMTST());
            else trips.add(parentRoute.getTripSTMT());
        }
        return trips;
    }

    public static List<Corridor> getAllCorridors() throws APIConnectionException {
        BusCorridor[] corridors = olhoVivoApi.getAllBusCorridors();
        return Corridor.convert(corridors);
    }

    protected static String getTripDetails(int internalTripId) throws IOException {
        return olhoVivoApi.getBusLineDetails(internalTripId);
    }

    protected static BusStop[] getStopsByTrip(int internalTripId) throws APIConnectionException {
        return olhoVivoApi.searchBusStopsByLine(internalTripId);
    }

    protected static BusLinePositions getBusesByTrip(int internalTripId) throws APIConnectionException {
        return olhoVivoApi.searchBusesByLine(internalTripId);
    }

    protected static ForecastWithStopAndLine
    getForecastByStopAndTrip(int internalTripId, int stopId) throws APIConnectionException {
        return olhoVivoApi.getForecastWithStopAndLine(stopId, internalTripId);
    }

    protected static ForecastWithLine
    getForecastByTrip(int internalTripId) throws APIConnectionException {
        return olhoVivoApi.getForecastWithLine(internalTripId);
    }

    protected static ForecastWithStop
    getForecastByStop(int stopId) throws APIConnectionException {
        return olhoVivoApi.getForecastWithStop(stopId);
    }

    protected static BusStop[] getStopsByCorridor(int corridorId) throws APIConnectionException {
        return olhoVivoApi.searchBusStopsByCorridor(corridorId);
    }

    protected static Pair<BusLine, BusLine> getBothTrips(String fullNumberSign) {
        try {
            return olhoVivoApi.getBothTrips(fullNumberSign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static <T> List<T> filterGtfsToList(String methodName, Predicate<T> filter) {
        return gtfsApi.filterToList(methodName, filter);
    }

    protected static  <T> T filterGtfsToElement(String methodName, Predicate<T> filter) {
        return gtfsApi.filterToElement(methodName, filter);
    }


}
