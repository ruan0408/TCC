package com.smartsampa.busapi;

import org.apache.commons.lang3.text.StrBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ruan0408 on 12/03/2016.
 */
public class Trip {

    private TripFacade tripFacade;
    private Route route;
    private String destinationSign;

    protected Trip(){}

    public static Trip getTrip(String fullNumberSign, String heading) {
        Trip newTrip = buildTrip(fullNumberSign, heading);
        newTrip.tripFacade = new TripFacade(newTrip);
        return newTrip;
    }

    private static Trip buildTrip(String fullNumberSign, String heading) {
        Route route = Route.buildFrom(fullNumberSign);
        if (heading.equalsIgnoreCase("mtst"))
            return route.getTripMTST();
        else
            return route.getTripSTMT();
    }

    public static List<Trip> searchTripByTerm(String term) {
        return TripFacade.searchTripByTerm(term);
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
        return tripFacade.getAllStops();
    }

    public List<PredictedBus> getPredictionsAtStop(Stop stop) {
        return tripFacade.getPredictionsAtStop(stop);
    }

    public int getDepartureIntervalAtTime(String hhmm) {
        return tripFacade.getDepartureIntervalAtTime(hhmm);
    }

    public int getDepartureIntervalNow() {
        String timeNow = new SimpleDateFormat("HH:mm").format(new Date());
        return getDepartureIntervalAtTime(timeNow);
    }

    public Shape getShape() {
        return tripFacade.getShape();
    }

    public List<Bus> getAllRunningBuses() {
        return tripFacade.getAllRunningBuses();
    }

    public String getWorkingDays() {
        return tripFacade.getWorkingDays();
    }

    public Map<Stop, List<PredictedBus>> getAllPredictions() {
        return tripFacade.getAllPredictions();
    }

    public double getFarePrice() {
        return route.getFarePrice();
    }

    public int getOlhovivoTripId() {
        return tripFacade.getOlhovivoTripId();
    }

    public String getRouteFullNumberSign() {
        return route.fullNumberSign();
    }

    public boolean isRouteCircular() {
        return route.isCircular();
    }

    public String getHeading() {
        if (route.getTripMTST().equals(this))
            return "mtst";
        if (route.getTripSTMT().equals(this))
            return "stmt";

        throw new RuntimeException("Couldn't find out the heading");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trip)) return false;

        Trip that = (Trip) obj;
        if (this.getRoute().equals(that.getRoute()) &&
                this.getDestinationSign().equals(that.getDestinationSign()))
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + route.hashCode();
        result = 31 * result + destinationSign.hashCode();
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
