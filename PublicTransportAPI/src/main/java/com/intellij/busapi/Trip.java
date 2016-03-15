package com.intellij.busapi;

import org.apache.commons.lang.text.StrBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ruan0408 on 12/03/2016.
 */
public class Trip {

    private DataToTripFacade adapter;
    private Route route;
    private String destinationSign;

    private Trip(){}

    protected Trip(String fullNumberSign, String heading, boolean isCircular) {
        adapter = new DataToTripFacade(fullNumberSign, heading, isCircular);
    }

    public static Trip getTrip(String fullNumberSign, String heading) {
        Trip trip = new Trip();
        Route route = Route.buildFrom(fullNumberSign, heading, trip);
        trip.adapter = new DataToTripFacade(fullNumberSign, heading, route.isCircular());
        trip.setRoute(route);
        return trip;
    }

    public static List<Trip> searchTripByTerm(String term) {
        return DataToTripFacade.searchTripByTerm(term);
    }

    protected void setRoute(Route route) {
        this.route = route;
    }

    protected void setDestinationSign(String destinationSign) {
        this.destinationSign = destinationSign;
    }

    public Route getRoute() {
        return route;
    }

    public String getDestinationSign() {
        return destinationSign;
    }

    public List<Stop> getAllStops() {
        return adapter.getAllStops();
    }

    public List<PredictedBus> getPredictionsAtStop(Stop stop) {
        return adapter.getPredictionsAtStop(stop);
    }

    public int getDepartureIntervalAtTime(String hhmm) {
        return adapter.getDepartureIntervalAtTime(hhmm);
    }

    public int getDepartureIntervalNow() {
        String timeNow = new SimpleDateFormat("HH:mm").format(new Date());
        return getDepartureIntervalAtTime(timeNow);
    }

    public Shape getShape() {
        return adapter.getShape();
    }

    public List<Bus> getAllRunningBuses() {
        return adapter.getAllRunningBuses();
    }

    public String getWorkingDays() {
        return adapter.getWorkingDays();
    }

    public Map<Stop, List<PredictedBus>> getAllPredictions() {
        return adapter.getAllPredictions();
    }

    public double getFarePrice() {
        return route.getFarePrice();
    }

    public int getOlhovivoTripId() {
        return adapter.getOlhovivoTripId();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trip)) return false;

        Trip that = (Trip) obj;
        if (this.getOlhovivoTripId() != that.getOlhovivoTripId())
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getOlhovivoTripId();
        return result;
    }

    @Override
    public String toString() {
        return new StrBuilder().
                append(route.basicToString()).
                appendln("internalId: " + getOlhovivoTripId()).
                appendln("destinationSign: " + destinationSign).
                toString();
    }
}
