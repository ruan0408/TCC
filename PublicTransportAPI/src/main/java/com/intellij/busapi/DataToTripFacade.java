package com.intellij.busapi;

import com.intellij.gtfsapi.GtfsAPI;
import com.intellij.olhovivoapi.*;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.StopTime;

import java.util.*;

import static com.intellij.busapi.BusAPIManager.gtfs;
import static com.intellij.busapi.BusAPIManager.olhovivo;

/**
 * Created by ruan0408 on 12/03/2016.
 */
public class DataToTripFacade {

    private int olhovivoTripId;
    private String gtfsTripId;
    private String tripShapeId;

    public DataToTripFacade(String fullNumberSign, String heading, boolean isCircular) {
        APIToDataConverter converter = new APIToDataConverter();
        gtfsTripId = converter.getGtfsIdFromTrip(fullNumberSign, heading, isCircular);
        olhovivoTripId = converter.getOlhovivoTripId(fullNumberSign, heading);
        tripShapeId = converter.getTripShapeIdFromGtfsId(gtfsTripId);
    }

    public static List<Trip> searchTripByTerm(String term) {
        BusLine[] busLines = olhovivo.searchBusLines(term);
        List<Trip> trips = DataToAPIConverter.busLinesArrayToTrips(busLines);
        return trips;
    }

    public List<Stop> getAllStops() {
        List<StopTime> stopTimes = gtfs.getAllStopTimesFromTripId(gtfsTripId);

        stopTimes = orderStopTimesBySequenceNumber(stopTimes);

        List<Stop> stopsFromGtfs = DataToAPIConverter.stopTimesToStops(stopTimes);
        List<Stop> stopsFromOlhoVivo = DataToAPIConverter.busStopArrayToStops(
                olhovivo.searchBusStopsByLine(olhovivoTripId));

        return getStopsWithAddresses(stopsFromGtfs, stopsFromOlhoVivo);
    }

    public String getWorkingDays() {
        return gtfs.getWorkingDays(gtfsTripId);
    }

    public Shape getShape() {
        List<ShapePoint> shapePoints = gtfs.getShape(tripShapeId);
        return DataToAPIConverter.shapePointsToShape(shapePoints);
    }

    public int getDepartureIntervalAtTime(String hhmm) {
        return gtfs.getDepartureIntervalAtTime(gtfsTripId, hhmm);
    }

    public List<Bus> getAllRunningBuses() {
        BusLinePositions busesWithTrip = olhovivo.searchBusesByLine(olhovivoTripId);
        return DataToAPIConverter.olhovivoBusArrayToBuses(busesWithTrip.getVehicles());
    }

    public Map<Stop, List<PredictedBus>> getAllPredictions() {
        ForecastWithLine forecast = olhovivo.getForecastWithLine(olhovivoTripId);
        BusStopNow[] busStopNowArray = forecast.getBusStops();
        Map<Stop, List<PredictedBus>> map = new HashMap<>(busStopNowArray.length);

        for (int i = 0; i < busStopNowArray.length; i++) {
            BusStopNow stopNow = busStopNowArray[i];
            map.put(DataToAPIConverter.busStopToStop(stopNow.getBusStop()),
                    DataToAPIConverter.busNowArrayToPredictedBuses(stopNow.getVehicles()));
        }
        return map;
    }

    public List<PredictedBus> getPredictionsAtStop(Stop stop) {
        ForecastWithStopAndLine forecast =
                olhovivo.getForecastWithStopAndLine(stop.getId(), olhovivoTripId);

        BusNow[] predictedBuses = forecast.getBuses();
        if (predictedBuses == null)
            return new ArrayList<>();

        return DataToAPIConverter.busNowArrayToPredictedBuses(predictedBuses);
    }

    public int getOlhovivoTripId() {
        return olhovivoTripId;
    }


    private List<Stop> getStopsWithAddresses(List<Stop> stopsFromGtfs, List<Stop> stopsFromOlhoVivo) {
        Map<Stop, Stop> map = new Hashtable<>(stopsFromOlhoVivo.size());
        for (Stop stop : stopsFromOlhoVivo) map.put(stop, stop);

        for (Stop stop : stopsFromGtfs) {
            Stop fromOlhoVivo = map.get(stop);
            if (fromOlhoVivo != null)
                stop.setAddress(fromOlhoVivo.getAddress());
        }

        return stopsFromGtfs;
    }

    private List<StopTime> orderStopTimesBySequenceNumber(List<StopTime> stopTimes) {
        stopTimes.sort(GtfsAPI.compByStopSequence);
        return stopTimes;
    }
}
