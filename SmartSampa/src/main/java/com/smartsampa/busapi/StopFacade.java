package com.smartsampa.busapi;

import com.smartsampa.olhovivoapi.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.smartsampa.busapi.BusAPIManager.gtfs;
import static com.smartsampa.busapi.BusAPIManager.olhovivo;

/**
 * Created by ruan0408 on 13/03/2016.
 */
public class StopFacade {

    protected static List<Stop> searchForStopsByTerm(String term) {
        BusStop[] busStops = olhovivo.searchBusStops(term);
        return busStopArrayToStops(busStops);
    }

    protected static List<Stop> busStopArrayToStops(BusStop[] busStops) {

        if (busStops == null) return new ArrayList<>();

        return Arrays.asList(busStops).parallelStream()
                .map(stop -> busStopToStop(stop))
                .collect(Collectors.toList());
    }

    protected static Stop busStopToStop(BusStop busStop) {
        Stop newStop = new Stop();
        newStop.setId(busStop.getCode());
        newStop.setName(busStop.getName());
        newStop.setAddress(busStop.getAddress());
        newStop.setLocation(new Point(busStop.getLatitude(), busStop.getLongitude()));
        return newStop;
    }

    public List<Trip> getAllTrips(int stopId) {
        List<org.onebusaway.gtfs.model.Trip> allTrips = gtfs.getAllTrips(stopId);
        return gtfsTripToTrips(allTrips);
    }

    private List<Trip> gtfsTripToTrips(List<org.onebusaway.gtfs.model.Trip> list) {

        List<Trip> trips = list.parallelStream()
                .map(t -> gtfsTripToTrip(t))
                .collect(Collectors.toList());
        return trips;
    }

    private Trip gtfsTripToTrip(org.onebusaway.gtfs.model.Trip trip) {
        String fullNumberSign = trip.getRoute().getShortName();
        String heading = trip.getDirectionId().equals("1") ? "mtst" : "stmt";

        return Trip.getTrip(fullNumberSign, heading);
    }

    public Map<Trip, List<PredictedBus>> getAllPredictions(int stopId) {
        ForecastWithStop forecast = olhovivo.getForecastWithStop(stopId);
        BusLineNow[] busLineNowArray = forecast.getBusLines();

        Map<Trip, List<PredictedBus>> map = new HashMap<>();

        for (int i = 0; busLineNowArray != null && i < busLineNowArray.length; i++) {
            BusLineNow lineNow = busLineNowArray[i];
            map.put(TripFacade.busLineToTrip(lineNow.getBusLine()),
                    PredictedBusFacade.busNowArrayToPredictedBuses(lineNow.getVehicles()));
        }
        return map;
    }

    public List<PredictedBus> getPredictedBusesOfTrip(int stopId, int olhovivoTripId) {
        ForecastWithStopAndLine forecast = olhovivo
                .getForecastWithStopAndLine(stopId, olhovivoTripId);

        BusNow[] buses = forecast.getBuses();
        return PredictedBusFacade.busNowArrayToPredictedBuses(buses);
    }
}
