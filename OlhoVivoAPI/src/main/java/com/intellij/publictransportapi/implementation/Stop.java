package com.intellij.publictransportapi.implementation;

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

    public Stop(int id, String name, String address, Point location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.location = location;
    }

    public int getId() {return id;}

    public String getName() {return name;}

    public String getAddress() {return address;}

    public Point getLocation() {return location;}

    public Trip getAllTrips() {
        return null;
    }

    public List<PredictedBus> getPredictedBuses(Trip trip) {
        return null;
    }

    public Map<Trip, List<PredictedBus>> getAllPredictions() {
        return null;
    }

    @Override
    public String toString() {
        StrBuilder builder = new StrBuilder();
        builder.appendln("id: "+id);
        builder.appendln("name: "+name);
        builder.appendln("address: "+address);
        builder.appendln(location.toString());
        return builder.toString();
    }
}
