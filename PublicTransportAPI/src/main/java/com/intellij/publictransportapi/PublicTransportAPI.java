package com.intellij.publictransportapi;

import com.intellij.gtfsapi.GtfsAPI;
import com.intellij.olhovivoapi.*;
import com.intellij.openapi.util.Pair;
import com.intellij.utils.APIConnectionException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class PublicTransportAPI {

    private static PublicTransportAPI myInstance = new PublicTransportAPI();
    private static OlhoVivoAPI olhoVivoApi;
    private static GtfsAPI gtfsApi;

    private PublicTransportAPI() {}

    public static PublicTransportAPI getInstance() {
        return myInstance;
    }

    public void init(String key, String login, String password) {
        olhoVivoApi = new OlhoVivoAPI(key);
        olhoVivoApi.authenticate();
        gtfsApi = new GtfsAPI(login, password);
        gtfsApi.init();
    }

    public List<Trip> searchTrip(String term) {
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

    public List<Corridor> getAllCorridors() {
        BusCorridor[] corridors = olhoVivoApi.getAllBusCorridors();
        return Corridor.convert(corridors);
    }

    public List<Bus> getAllBuses() {
        Predicate<org.onebusaway.gtfs.model.Trip> busOnly =
                t ->  t.getId().getId().matches("\\w{4}-\\w{2}-\\w");

        List<org.onebusaway.gtfs.model.Trip> allBusTrips;
        allBusTrips = gtfsApi.filterToList("getAllTrips", busOnly);
        List<Bus> synchronizedList =
                Collections.synchronizedList(new ArrayList<>(allBusTrips.size()));
        Trip.convert(allBusTrips).parallelStream().forEach(trip -> {
            try {
                synchronizedList.addAll(trip.getAllBuses());
            } catch (APIConnectionException e) {
                e.printStackTrace();
            }
        });
        return synchronizedList;
    }

    protected String getTripDetails(int internalTripId) {
        return olhoVivoApi.getBusLineDetails(internalTripId);
    }

    protected BusStop[] getStopsByTrip(int internalTripId) {
        return olhoVivoApi.searchBusStopsByLine(internalTripId);
    }

    protected BusLinePositions getBusesByTrip(int internalTripId) {
        return olhoVivoApi.searchBusesByLine(internalTripId);
    }

    protected ForecastWithStopAndLine
    getForecastByStopAndTrip(int internalTripId, int stopId) {
        return olhoVivoApi.getForecastWithStopAndLine(stopId, internalTripId);
    }

    protected ForecastWithLine
    getForecastByTrip(int internalTripId) {
        return olhoVivoApi.getForecastWithLine(internalTripId);
    }

    protected ForecastWithStop
    getForecastByStop(int stopId) {
        return olhoVivoApi.getForecastWithStop(stopId);
    }

    protected BusStop[] getStopsByCorridor(int corridorId) {
        return olhoVivoApi.searchBusStopsByCorridor(corridorId);
    }

    protected <T> List<T> filterGtfsToList(String methodName, Predicate<T> filter) {
        return gtfsApi.filterToList(methodName, filter);
    }

    protected <T> T filterGtfsToElement(String methodName, Predicate<T> filter) {
        return gtfsApi.filterToElement(methodName, filter);
    }

    protected Pair<BusLine, BusLine> getBothTrips(String fullNumberSign) {
        try {
            return olhoVivoApi.getBothTrips(fullNumberSign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
