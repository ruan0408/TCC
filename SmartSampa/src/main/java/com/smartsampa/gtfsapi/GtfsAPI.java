package com.smartsampa.gtfsapi;

import com.smartsampa.utils.APIConnectionException;
import org.apache.commons.lang3.StringUtils;
import org.onebusaway.gtfs.model.*;
import org.onebusaway.gtfs.services.GtfsDao;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 27/02/2016.
 */
public class GtfsAPI {

    private GtfsDao gtfsDao;

    public GtfsAPI(GtfsDao gtfsDao) {
        this.gtfsDao = gtfsDao;
    }

    public List<Trip> getTripsWithRouteContaining(String term) {
        Predicate<Trip> containsTerm = trip -> routeContainsTerm(trip, term);
        return filterToList("getAllTrips", containsTerm);
    }

    private boolean routeContainsTerm(Trip trip, String term) {
        return StringUtils.containsIgnoreCase(trip.getRoute().getLongName(), term) ||
                StringUtils.containsIgnoreCase(trip.getRoute().getId().getId(), term);
    }

    public List<Stop> getStopsByTerm(String term) {
        Predicate<Stop> containsTerm =
                stop -> StringUtils.containsIgnoreCase(stop.getName(), term);

        return filterToList("getAllStops", containsTerm);
    }

    public Stop getStopById(int id) {
        Predicate<Stop> hasId = stop -> stop.getId().getId().equals(id + "");
        return filterToElement("getAllStops", hasId);
    }

    public List<Stop> getAllStopsOrderedFromTripId(String gtfsTripId) {
        return getAllStopTimesFromTripId(gtfsTripId)
                .stream()
                .sorted(StopTime::compareTo)
                .map(StopTime::getStop)
                .collect(Collectors.toList());
    }

    private List<StopTime> getAllStopTimesFromTripId(String gtfsTripId) {
        Predicate<StopTime> predicate;
        predicate = s -> s.getTrip().getId().getId().equals(gtfsTripId);
        return filterToList("getAllStopTimes", predicate);
    }

    public boolean isTripCircular(Trip trip) {
        String gtfsRouteId = trip.getRoute().getShortName();
        Predicate<org.onebusaway.gtfs.model.Trip> sameRoute =
                t -> t.getRoute().getShortName().equals(gtfsRouteId);

        return filterToList("getAllTrips", sameRoute).size() == 1;
    }

    public List<ShapePoint> getShape(String shapeId) {
        Predicate<ShapePoint> predicate;
        predicate = point -> point.getShapeId().getId().equals(shapeId);

        return filterToList("getAllShapePoints", predicate);
    }

    public double getFarePrice(String fullNumberSign) {
        Predicate<FareRule> predicate;
        predicate = f -> f.getRoute().getId().getId().equals(fullNumberSign);

        FareRule rule = filterToElement("getAllFareRules", predicate);
        return rule.getFare().getPrice();
    }

    public List<Trip> getAllTrips(int stopId) {
        Predicate<StopTime> predicate = stopTime -> stopTime.getStop().getId().getId().equals(stopId+"");

        List<StopTime> stopTimes = filterToList("getAllStopTimes", predicate);
        return stopTimes
                .stream()
                .map(StopTime::getTrip)
                .collect(Collectors.toList());
    }

    public int getDepartureIntervalAtTime(String gtfsTripId, String hhmm) {
        try {
            Date date = new SimpleDateFormat("HH:mm").parse(hhmm);
            Date date0 = new SimpleDateFormat("HH:mm").parse("00:00");
            long time = (date.getTime() - date0.getTime())/1000;
            return getDepartureInterval(gtfsTripId, time);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new APIConnectionException("There was a problem when finding the " +
                    "departure interval at the time "+hhmm);
        }
    }

    private int getDepartureInterval(String gtfsTripId, long time) {
        Predicate<Frequency> predicate;
        predicate = f -> f.getTrip().getId().getId().equals(gtfsTripId) &&
                (f.getStartTime() <= time) && (time < f.getEndTime());

        Frequency f = filterToElement("getAllFrequencies", predicate);
        return f.getHeadwaySecs();
    }

    private <T> List<T> filterToList(String methodName, Predicate<T> predicate) {
        try {
            Method method = gtfsDao.getClass().getMethod(methodName);
            return ((Collection<T>)method.invoke(gtfsDao))
                    .parallelStream()
                    .filter(predicate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private <T> T filterToElement(String methodName, Predicate<T> predicate) {
        List<T> filtered = filterToList(methodName, predicate);

        if (filtered.isEmpty())
            throw new APIConnectionException("No result found for method "+methodName+
                    "and predicate "+predicate);

        return filtered.get(0);
    }
}
