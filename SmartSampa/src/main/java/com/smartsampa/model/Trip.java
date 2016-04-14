package com.smartsampa.model;

import com.smartsampa.busapi.BusAPIManager;
import com.smartsampa.utils.Utils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public abstract class Trip {

    private String destinationSign;
    private String numberSign;
    private String workingDays;
    private Integer heading;
    private Shape shape;
    private Set<Stop> stops;
    private Double farePrice;
    private Boolean isCircular;

    private Integer olhovivoId;
    private String gtfsId;

    //TODO fix problem when querying for two words
    public static Set<Trip> getTripsByTerm(String term) {
        Set<Trip> gtfsTrips = BusAPIManager.gtfs.getTripsByTerm(term);
        Set<Trip> olhovivoTrips = BusAPIManager.olhovivo.getTripsByTerm(term);
        return mergeSets(gtfsTrips, olhovivoTrips);
    }

    private static Set<Trip> mergeSets(Set<Trip> trips1, Set<Trip> trips2) {
        Map<Trip, Trip> mirror2 = Utils.mirroredMap(trips2);
        Set<Trip> mergedSets = new HashSet<>();

        for (Trip trip : trips1) {
            Trip equalTrip = mirror2.get(trip);
            if (equalTrip != null) {
                mergedSets.add(merge(trip, equalTrip));
                mirror2.remove(equalTrip);
            }
            else mergedSets.add(trip);
        }

        mergedSets.addAll(mirror2.keySet().stream().collect(Collectors.toSet()));

        return mergedSets;
    }

    private static Trip merge(Trip trip1, Trip trip2) {
        if (trip1.getDestinationSign() == null) trip1.setDestinationSign(trip2.getDestinationSign());
        if (trip1.getFarePrice() == null) trip1.setFarePrice(trip2.getFarePrice());
        if (trip1.getGtfsId() == null) trip1.setGtfsId(trip2.getGtfsId());
        if (trip1.getHeading() == null) trip1.setHeading(trip2.getHeading());
        if (trip1.getNumberSign() == null) trip1.setNumberSign(trip2.getNumberSign());
        if (trip1.getOlhovivoId() == null) trip1.setOlhovivoId(trip2.getOlhovivoId());
        if (trip1.getShape() == null) trip1.setShape(trip2.getShape());
        if (trip1.getStops() == null) trip1.setStops(trip2.getStops());
        if (trip1.getWorkingDays() == null) trip1.setWorkingDays(trip2.getWorkingDays());
        if (trip1.isCircular() == null) trip1.setCircular(trip2.isCircular());
        return trip1;
    }

    //TODO implement getAllStops
    public List<Stop> getAllStops() {
        return null;
    }

    public Set<Bus> getAllRunningBuses() {
        return BusAPIManager.olhovivo.getAllRunningBusesOfTrip(olhovivoId);
    }

    public Map<Stop, List<PredictedBus>> getAllPredictions() {
        return BusAPIManager.olhovivo.getPredictionsOfTrip(olhovivoId);
    }

    public List<PredictedBus> getPredictionsAtStop(Stop stop) {
        return BusAPIManager.olhovivo.getPredictionsOfTripAtStop(olhovivoId, stop.getOlhovivoId());
    }

    public String getDestinationSign() {
        return destinationSign;
    }

    public void setDestinationSign(String destinationSign) {
        this.destinationSign = destinationSign;
    }

    public String getNumberSign() {
        return numberSign;
    }

    public void setNumberSign(String numberSign) {
        this.numberSign = numberSign;
    }

    public String getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }

    public Integer getHeading() {
        return heading;
    }

    public void setHeading(Integer heading) {
        this.heading = heading;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Set<Stop> getStops() {
        return stops;
    }

    public void setStops(Set<Stop> stops) {
        this.stops = stops;
    }

    public Double getFarePrice() {
        return farePrice;
    }

    public void setFarePrice(Double farePrice) {
        this.farePrice = farePrice;
    }

    public Boolean isCircular() {
        return isCircular;
    }

    public void setCircular(boolean circular) {
        isCircular = circular;
    }

    public Integer getOlhovivoId() {
        return olhovivoId;
    }

    public void setOlhovivoId(Integer olhovivoId) {
        this.olhovivoId = olhovivoId;
    }

    public String getGtfsId() {
        return gtfsId;
    }

    public void setGtfsId(String gtfsId) {
        this.gtfsId = gtfsId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Trip)) return false;

        Trip that = (Trip) o;

        if (getNumberSign().equals(that.getNumberSign()) && getHeading() == that.getHeading())
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getNumberSign().hashCode();
        result = 31 * result + getHeading();
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("destinationSign", getDestinationSign())
                .append("numberSign", getNumberSign())
                .append("workingDays", getWorkingDays())
                .append("heading", getHeading())
                .append("shape", getShape())
                .append("stops", getStops())
                .append("farePrice", getFarePrice())
                .append("isCircular", isCircular())
                .append("olhovivoId", getOlhovivoId())
                .append("gtfsId", getGtfsId())
                .toString();
    }
}
