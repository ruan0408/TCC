package com.smartsampa.busapi;

import com.smartsampa.gtfsapi.GtfsAPI;
import com.smartsampa.olhovivoapi.*;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.StopTime;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by ruan0408 on 12/03/2016.
 */
public class DataToTripFacade {

    private int olhovivoTripId;
    private String gtfsTripId;
    private String tripShapeId;

    public DataToTripFacade(Trip trip) {
        String fullNumberSign = trip.getRouteFullNumberSign();
        String heading = trip.getHeading();
        boolean isCircular = trip.isRouteCircular();

        APIToDataConverter converter = new APIToDataConverter();
        gtfsTripId = converter.getGtfsIdFromTrip(fullNumberSign, heading, isCircular);
        olhovivoTripId = converter.getOlhovivoTripId(fullNumberSign, heading);
        tripShapeId = converter.getTripShapeIdFromGtfsId(gtfsTripId);
    }

    public static List<Trip> searchTripByTerm(String term) {
        BusLine[] busLines = BusAPIManager.olhovivo.searchBusLines(term);
        List<Trip> trips = DataToAPIConverter.busLinesArrayToTrips(busLines);
        return trips;
    }

    public List<Stop> getAllStops() {
        List<StopTime> stopTimes = BusAPIManager.gtfs.getAllStopTimesFromTripId(gtfsTripId);

        stopTimes = orderStopTimesBySequenceNumber(stopTimes);

        List<Stop> stopsFromGtfs = DataToAPIConverter.stopTimesToStops(stopTimes);
        List<Stop> stopsFromOlhoVivo = DataToAPIConverter.busStopArrayToStops(
                BusAPIManager.olhovivo.searchBusStopsByLine(olhovivoTripId));

        return getStopsWithAddresses(stopsFromGtfs, stopsFromOlhoVivo);
    }

    public String getWorkingDays() {
        return BusAPIManager.gtfs.getWorkingDays(gtfsTripId);
    }

    public Shape getShape() {
        List<ShapePoint> shapePoints = BusAPIManager.gtfs.getShape(tripShapeId);
        return DataToAPIConverter.shapePointsToShape(shapePoints);
    }

    public int getDepartureIntervalAtTime(String hhmm) {
        try {
            return BusAPIManager.gtfs.getDepartureIntervalAtTime(gtfsTripId, hhmm);
        } catch (ParseException e) {
            throw new RuntimeException("Error when parsing "+hhmm+
                    " in method getDepartureIntervalAtTime");
        }
    }

    public List<Bus> getAllRunningBuses() {
        BusLinePositions busesWithTrip = BusAPIManager.olhovivo.searchBusesByLine(olhovivoTripId);
        return DataToAPIConverter.olhovivoBusArrayToBuses(busesWithTrip.getVehicles());
    }

    public Map<Stop, List<PredictedBus>> getAllPredictions() {
        ForecastWithLine forecast = BusAPIManager.olhovivo.getForecastWithLine(olhovivoTripId);
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
                BusAPIManager.olhovivo.getForecastWithStopAndLine(stop.getId(), olhovivoTripId);
        BusNow[] busNowArray = forecast.getBuses();
        return DataToAPIConverter.busNowArrayToPredictedBuses(busNowArray);
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
