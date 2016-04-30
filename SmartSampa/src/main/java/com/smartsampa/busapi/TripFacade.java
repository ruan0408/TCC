package com.smartsampa.busapi;

import com.smartsampa.gtfsapi.GtfsAPI;
import com.smartsampa.busapi2.model.Heading;
import com.smartsampa.olhovivoapi.*;
import com.smartsampa.utils.Point;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.StopTime;

import java.text.ParseException;
import java.util.*;

import static com.smartsampa.busapi2.impl.BusAPIManager.gtfs;
import static com.smartsampa.busapi2.impl.BusAPIManager.olhovivo;

/**
 * Created by ruan0408 on 12/03/2016.
 */
public class TripFacade {

    private int olhovivoTripId;
    private String gtfsTripId;
    private String tripShapeId;

    public TripFacade(Trip trip) {
        String fullNumberSign = trip.getRouteFullNumberSign();
        String heading = trip.getHeading();
        boolean isCircular = trip.isRouteCircular();

        APIToDataConverter converter = new APIToDataConverter();
        gtfsTripId = converter.getGtfsIdFromTrip(fullNumberSign, heading, isCircular);
        olhovivoTripId = converter.getOlhovivoTripId(fullNumberSign, heading);
        tripShapeId = converter.getTripShapeIdFromGtfsId(gtfsTripId);
    }

    public static List<Trip> searchTripByTerm(String term) {
        BusLine[] busLines = olhovivo.searchBusLines(term);
        List<Trip> trips = busLinesArrayToTrips(busLines);
        return trips;
    }

    private static List<Trip> busLinesArrayToTrips(BusLine[] busLines) {

        if (busLines == null) return new ArrayList<>();

        List<Trip> trips = Utils.convertKArrayToTList(busLines, busLine -> busLineToTrip(busLine));

        return trips;
    }

    protected static Trip busLineToTrip(BusLine line) {
        String fullNumberSign = line.getNumberSign()+"-"+line.getType();

        //due to weird behaviour of olhovivo
        if (line.getNumberSign().matches("\\w{4}-\\w{2}"))
            fullNumberSign = line.getNumberSign();

        String heading = line.getHeading() == Heading.SECONDARY_TERMINAL ? "mtst" : "stmt";

        return Trip.getTrip(fullNumberSign, heading);
    }

    public List<Stop> getAllStops() {
        List<StopTime> stopTimes = gtfs.getAllStopTimesFromTripId(gtfsTripId);

        stopTimes = orderStopTimesBySequenceNumber(stopTimes);

        List<Stop> stopsFromGtfs = stopTimesToStops(stopTimes);
        List<Stop> stopsFromOlhoVivo =
                StopFacade.busStopArrayToStops(olhovivo.searchBusStopsByLine(olhovivoTripId));

        return getStopsWithAddresses(stopsFromGtfs, stopsFromOlhoVivo);
    }

    private List<StopTime> orderStopTimesBySequenceNumber(List<StopTime> stopTimes) {
        stopTimes.sort(GtfsAPI.compByStopSequence);
        return stopTimes;
    }

    private static List<Stop> stopTimesToStops(List<StopTime> stopTimes) {
        return Utils.convertKListToT(stopTimes, stopTime -> gtfsStopToStop(stopTime.getStop()));
    }

    private List<Stop> getStopsWithAddresses(List<Stop> stopsFromGtfs, List<Stop> stopsFromOlhoVivo) {

        Map<Stop, Stop> map = new Hashtable<>(stopsFromOlhoVivo.size());

        for (Stop stop : stopsFromOlhoVivo)
            map.put(stop, stop);

        for (Stop stop : stopsFromGtfs) {
            Stop fromOlhoVivo = map.get(stop);
            if (fromOlhoVivo != null)
                stop.setAddress(fromOlhoVivo.getAddress());
        }

        return stopsFromGtfs;
    }

    private static Stop gtfsStopToStop(org.onebusaway.gtfs.model.Stop gtfsStop) {
        Stop newStop = new Stop();
        newStop.setId(Integer.parseInt(gtfsStop.getId().getId()));
        newStop.setName(gtfsStop.getName());
        newStop.setAddress("");
        newStop.setLocation(new Point(gtfsStop.getLat(), gtfsStop.getLon()));
        return newStop;
    }

    public String getWorkingDays() {
        return gtfs.getWorkingDays(gtfsTripId);
    }

    public Shape getShape() {
        List<ShapePoint> shapePoints = gtfs.getShape(tripShapeId);
        return ShapeFacade.shapePointsToShape(shapePoints);
    }

    public int getDepartureIntervalAtTime(String hhmm) {
        try {
            return gtfs.getDepartureIntervalAtTime(gtfsTripId, hhmm);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Error when parsing "+hhmm+
                    " in method getDepartureIntervalAtTime");
        }
    }

    public List<Bus> getAllRunningBuses() {
        BusLinePositions busesWithTrip = olhovivo.searchBusesByLine(olhovivoTripId);
        return BusFacade.olhovivoBusArrayToBuses(busesWithTrip.getVehicles());
    }

    public Map<Stop, List<PredictedBus>> getAllPredictions() {
        ForecastWithLine forecast = olhovivo.getForecastWithLine(olhovivoTripId);
        BusStopNow[] busStopNowArray = forecast.getBusStops();

        Map<Stop, List<PredictedBus>> map = new HashMap<>(busStopNowArray.length);

        for (int i = 0; i < busStopNowArray.length; i++) {
            BusStopNow stopNow = busStopNowArray[i];
            map.put(StopFacade.busStopToStop(stopNow.getBusStop()),
                    PredictedBusFacade.busNowArrayToPredictedBuses(stopNow.getVehicles()));
        }
        return map;
    }

    public List<PredictedBus> getPredictionsAtStop(Stop stop) {
        ForecastWithStopAndLine forecast =
                olhovivo.getForecastWithStopAndLine(stop.getId(), olhovivoTripId);
        BusNow[] busNowArray = forecast.getBuses();
        return PredictedBusFacade.busNowArrayToPredictedBuses(busNowArray);
    }

    public int getOlhovivoTripId() {
        return olhovivoTripId;
    }


}
