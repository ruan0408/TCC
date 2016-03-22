package com.smartsampa.busapi;

import com.google.common.base.Objects;

import java.util.List;
import java.util.Map;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class Stop {

    private int id;
    private String name;
    private String address;
    private Point location;

    private DataToStopFacade stopFacade;

    protected Stop() {
        stopFacade = new DataToStopFacade();
    }

    protected Stop(int id, String name, Point location) {
        this();
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public static List<Stop> searchStopsByTerm(String term) {
        return DataToStopFacade.searchForStopsByTerm(term);
    }

    public List<Trip> getAllTrips() {
        return stopFacade.getAllTrips(id);
    }

    public List<PredictedBus> getPredictedBusesOfTrip(Trip trip) {
        return stopFacade.getPredictedBusesOfTrip(id, trip.getOlhovivoTripId());
    }

    public Map<Trip, List<PredictedBus>> getAllPredictions() {
        return stopFacade.getAllPredictions(id);
    }

    public int getId() {return id;}

    public String getName() {return name;}

    public String getAddress() {return address;}

    public Point getLocation() {return location;}

    public void setAddress(String address) {this.address = address;}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Stop)) return false;

        Stop that = (Stop) obj;
        if (this.id == that.id)
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("address", address)
                .add("location", location)
                .toString();
    }
}
