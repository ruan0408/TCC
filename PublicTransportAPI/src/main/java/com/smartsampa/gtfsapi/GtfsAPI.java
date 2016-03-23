package com.smartsampa.gtfsapi;

import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.model.*;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 27/02/2016.
 */
public class GtfsAPI {

    public static final Comparator<StopTime> compByStopSequence = (s1, s2) -> {
        if (s1.getStopSequence() < s2.getStopSequence()) return -1;
        if (s1.getStopSequence() > s2.getStopSequence()) return 1;
        return 0;
    };
    public static final Comparator<ShapePoint> compByShapePointSequence = (p1, p2) -> {
        if (p1.getSequence() < p2.getSequence()) return -1;
        if (p1.getSequence() > p2.getSequence()) return 1;
        return 0;
    };

    private GtfsDaoImpl gtfsAcessor;
    private GtfsHandler gtfsHandler;

    public GtfsAPI(String login, String password) {
        gtfsHandler = new GtfsHandler(login, password);
    }

    public void init() {
        gtfsAcessor = gtfsHandler.getGtfsAcessor();
    }

    public List<StopTime> getAllStopTimesFromTripId(String gtfsTripId) {
        Predicate<StopTime> predicate;
        predicate = s -> s.getTrip().getId().getId().equals(gtfsTripId);

        return filterToList("getAllStopTimes", predicate);
    }

    public String getWorkingDays(String gtfsTripId) {
        Trip gtfsTrip = getTripById(gtfsTripId);
        return gtfsTrip.getServiceId().getId();
    }

    public List<ShapePoint> getShape(String shapeId) {
        Predicate<ShapePoint> predicate;
        predicate = point -> point.getShapeId().getId().equals(shapeId);

        List<ShapePoint> shapes = filterToList("getAllShapePoints", predicate);

        return shapes;
    }

    public double getFarePrice(String fullNumberSign) {
        Predicate<FareRule> predicate;
        predicate = f -> f.getRoute().getId().getId().equals(fullNumberSign);

        FareRule rule = filterToElement("getAllFareRules", predicate);
        return rule.getFare().getPrice();
    }

    public int getDepartureIntervalAtTime(String gtfsTripId, String hhmm) {
        try {
            Date date = new SimpleDateFormat("HH:mm").parse(hhmm);
            Date date0 = new SimpleDateFormat("HH:mm").parse("00:00");
            long time = (date.getTime() - date0.getTime())/1000;

            return getDepartureInterval(gtfsTripId, time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Trip> getAllTrips(int stopId) {
        Predicate<StopTime> predicate =
                s -> s.getStop().getId().getId().equals(stopId+"");

        List<StopTime> stopTimes = filterToList("getAllStopTimes", predicate);

        return stopTimes.parallelStream()
                .map(s -> s.getTrip())
                .collect(Collectors.toList());
    }

    public Trip getTripById(String tripId) {
        Predicate<org.onebusaway.gtfs.model.Trip> predicate;
        predicate = trip -> trip.getId().getId().equals(tripId);

        return filterToElement("getAllTrips", predicate);
    }

    private int
    getDepartureInterval(String gtfsTripId, long time) {
        Predicate<Frequency> predicate;
        predicate = f -> f.getTrip().getId().getId().equals(gtfsTripId) &&
                (f.getStartTime() <= time) && (time < f.getEndTime());

        Frequency f = filterToElement("getAllFrequencies", predicate);
        return f.getHeadwaySecs();
    }

    private <T> List<T> filterToList(String methodName, Predicate<T> predicate) {
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

    private <T> T filterToElement(String methodName, Predicate<T> predicate) {
        List<T> filtered = filterToList(methodName, predicate);

        if (filtered.isEmpty())
            return null;

        return filtered.get(0);
    }
}
