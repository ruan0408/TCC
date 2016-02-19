package com.intellij.publictransportapi.implementation;

import org.apache.commons.lang.text.StrBuilder;
import org.onebusaway.gtfs.model.Frequency;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class Trip {

    private int internalId;
    private Route route;
    private String destinationSign;

    public void setInternalId(int internalId) {this.internalId = internalId;}

    public void setDestinationSign(String destinationSign) {this.destinationSign = destinationSign;}

    public void setRoute(Route route) { this.route = route;}

    public int getInternalId() {return internalId;}

    public String getGtfsId() {
        if (route.getTripMTST().equals(this) && !route.isCircular())
            return route.fullNumberSign()+"-1";

        return route.fullNumberSign()+"-0";
    }

    public Route getRoute() {return route;}

    public String getDestinationSign() { return destinationSign;}

    //TODO refactor this and getDepartureInterval to extract a pattern
    public String getWorkingDays() {
        Stream<org.onebusaway.gtfs.model.Trip> allTrips;
        List<org.onebusaway.gtfs.model.Trip> list;
        allTrips = API.store.getAllTrips().stream();

        list = allTrips.filter(t ->
                t.getId().getId().equals(getGtfsId())
        ).collect(Collectors.toList());

        Iterator<org.onebusaway.gtfs.model.Trip> iterator;
        iterator = list.iterator();
        if (!iterator.hasNext()) return "";
        return iterator.next().getServiceId().getId();
    }

    public String getDetails() {
        return API.olhoVivoAPI.getBusLineDetails(internalId);
    }

    public Shape getShape() {
        return null;
    }

    public List<Stop> getAllStops() {
        return null;
    }

    public List<Bus> getAllBuses() {
        return null;
    }

    public List<PredictedBus> getPredictedBuses(Stop stop) {
        return null;
    }

    public Map<Stop, List<PredictedBus>> getAllPredictions() {
        return null;
    }

    public int getDepartureInterval(String hhmm) {
        try {
            Date date = new SimpleDateFormat("HH:mm").parse(hhmm);
            Date date0 = new SimpleDateFormat("HH:mm").parse("00:00");
            long time = (date.getTime() - date0.getTime())/1000;

            return getDepartureInterval(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDepartureIntervalNow() {
        try {
            Date dateNow = new Date();
            DateFormat format = new SimpleDateFormat("HH:mm");
            dateNow = new SimpleDateFormat("HH:mm").parse(format.format(dateNow));
            Date date0 = new SimpleDateFormat("HH:mm").parse("00:00");

            long time = (dateNow.getTime()-date0.getTime())/1000;

            return getDepartureInterval(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getDepartureInterval(long time) {
        Stream<Frequency> allFrequencies = API.store.getAllFrequencies().stream();

        List<Frequency> list = allFrequencies.filter(f ->
            f.getTrip().getId().getId().equals(getGtfsId()) &&
                    (f.getStartTime() <= time) && (time < f.getEndTime())
        ).collect(Collectors.toList());

        Iterator<Frequency> iterator = list.iterator();
        if (!iterator.hasNext()) return 0;
        return iterator.next().getHeadwaySecs();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trip)) return false;

        Trip that = (Trip) obj;
        if (this.internalId != that.internalId) return false;

        return true;
    }

    @Override
    public String toString() {
        StrBuilder builder = new StrBuilder();
        builder.append(route.basicToString());
        builder.appendln("internalId: "+ internalId);
        builder.appendln("destinationSign: "+destinationSign);

        return builder.toString();
    }
}
