package com.intellij.publictransportapi.implementation;

import org.apache.commons.lang.text.StrBuilder;
import org.onebusaway.gtfs.model.Frequency;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        if (route.getTripMTST().equals(this))
            return route.fullNumberSign()+"1";
        return route.fullNumberSign()+"-0";
    }

    public Route getRoute() {return route;}

    public String getDestinationSign() { return destinationSign;}

    public int getDepartureInterval(String hhmm) throws ParseException {
        Date date = new SimpleDateFormat("HH:mm").parse(hhmm);
        Date date0 = new SimpleDateFormat("HH:mm").parse("00:00");
        long time = (date.getTime() - date0.getTime())/1000;

        Collection<Frequency> allFrequencies = API.store.getAllFrequencies();
        allFrequencies.removeIf(f ->
                !(f.getTrip().getId().getId().equals(getGtfsId()) &&
                        (f.getStartTime() <= time) && (time < f.getEndTime()))
        );

        System.out.println(allFrequencies.size());
        return allFrequencies.iterator().next().getHeadwaySecs();
    }

    public int getDepartureIntervalNow() {return 0;}

    public String getWorkingDays() {
        return null;
    }

    public String getDetails() {
        return null;
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
