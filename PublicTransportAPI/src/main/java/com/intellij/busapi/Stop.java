package com.intellij.busapi;

import org.apache.commons.lang.text.StrBuilder;

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

    private DataToStopFacade adapter;

    protected Stop() {
        adapter = new DataToStopFacade();
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
        return adapter.getAllTrips(id);
    }

    public List<PredictedBus> getPredictedBusesOfTrip(Trip trip) {
        return adapter.getPredictedBusesOfTrip(id, trip.getOlhovivoTripId());
    }

    public Map<Trip, List<PredictedBus>> getAllPredictions() {
        return adapter.getAllPredictions(id);
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
        StrBuilder builder = new StrBuilder();
        builder.appendln("id: "+id);
        builder.appendln("name: "+name);
        builder.appendln("address: "+address);
        builder.append(location.toString());
        return builder.toString();
    }
}
