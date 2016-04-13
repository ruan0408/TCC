package com.smartsampa.busapi;

import com.smartsampa.utils.Point;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    private StopFacade stopFacade;

    protected Stop() {
        stopFacade = new StopFacade();
    }

    protected Stop(int id, String name, Point location) {
        this();
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public static List<Stop> searchStopsByTerm(String term) {
        return StopFacade.searchForStopsByTerm(term);
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
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("address", address)
                .append("location", location)
                .append("stopFacade", stopFacade)
                .toString();
    }
}
