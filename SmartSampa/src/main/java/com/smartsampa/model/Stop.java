package com.smartsampa.model;

import com.smartsampa.busapi.BusAPIManager;
import com.smartsampa.utils.Point;
import com.smartsampa.utils.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public abstract class Stop {

    private String name;
    private String address;
    private Point location;

    private String gtfsId;
    private Integer olhovivoId;


    public static Set<Stop> searchStopsByTerm(String term) {
        Set<Stop> gtfsStops = BusAPIManager.gtfs.getStopsByTerm(term);
        Set<Stop> olhovivoStops = BusAPIManager.olhovivo.getStopsByTerm(term);
        return mergeSets(gtfsStops, olhovivoStops);
    }

    //TODO maybe move this code to superclass with getIds too.
    private static Set<Stop> mergeSets(Set<Stop> stops1, Set<Stop> stops2) {
        Map<Stop, Stop> mirror1 = Utils.mirroredMap(stops1);
        Map<Stop, Stop> mirror2 = Utils.mirroredMap(stops2);
        Set<Stop> result = new HashSet<>();

        for (Stop stop : stops1) {
            Stop equalStop = mirror2.get(stop);
            if (equalStop != null) {
                result.add(merge(stop, equalStop));
                mirror2.remove(equalStop);
            }
            else result.add(stop);
        }

        result.addAll(mirror2.keySet().stream().collect(Collectors.toSet()));

        return result;
    }

    private static Stop merge(Stop stop1, Stop stop2) {
        if (stop1.getAddress() == null) stop1.setAddress(stop2.getAddress());
        if (stop1.getGtfsId() == null) stop1.setGtfsId(stop2.getGtfsId());
        if (stop1.getLocation() == null) stop1.setLocation(stop2.getLocation());
        if (stop1.getName() == null) stop1.setName(stop2.getName());
        if (stop1.getOlhovivoId() == null) stop1.setOlhovivoId(stop2.getOlhovivoId());
        return stop1;
    }

    private static Stop mergeStops(Stop stop1, Stop stop2) {
        return null;
    }

    //TODO implement getAllTrips
    public Set<Trip> getAllTrips() {
        return null;
    }

    public Map<Trip, List<PredictedBus>> getAllPredictions() {
        return BusAPIManager.olhovivo.getPredictionsAtStop(olhovivoId);
    }

    public List<PredictedBus> getPredictedBusesOfTrip(Trip trip) {
        return BusAPIManager.olhovivo.getPredictionsOfTripAtStop(trip.getOlhovivoId(), olhovivoId);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Point getLocation() {
        return location;
    }

    public String getGtfsId() {
        return gtfsId;
    }

    public Integer getOlhovivoId() {
        return olhovivoId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setGtfsId(String gtfsId) {
        this.gtfsId = gtfsId;
    }

    public void setOlhovivoId(Integer olhovivoId) {
        this.olhovivoId = olhovivoId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Stop)) return false;

        Stop that = (Stop) o;

        if (getName().equalsIgnoreCase(that.getName()))
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getName().hashCode();
        return result;
    }
}
