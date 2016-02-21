package com.intellij.publictransportapi.implementation;

import com.intellij.olhovivoapi.BusStop;
import org.apache.commons.lang.text.StrBuilder;

import java.util.ArrayList;
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

    protected Stop() {}

    public Stop(int id, String name, Point location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public int getId() {return id;}

    public String getName() {return name;}

    public String getAddress() {return address;}

    public Point getLocation() {return location;}

    public void setAddress(String address) {this.address = address;}

    public Trip getAllTrips() {
        return null;
    }

    public List<PredictedBus> getPredictedBuses(Trip trip) {
        return null;
    }

    public Map<Trip, List<PredictedBus>> getAllPredictions() {
        return null;
    }

    protected static
    List<Stop> convert(List<org.onebusaway.gtfs.model.Stop> stops) {
        List<Stop> list = new ArrayList<>(stops.size());
        for (org.onebusaway.gtfs.model.Stop s: stops)
            list.add(Stop.buildFromGtfsStop(s));
        return list;
    }

    protected static List<Stop> convert(BusStop[] stops) {
        List<Stop> list = new ArrayList<>(stops.length);

        for (int i = 0; i < stops.length; i++)
            list.add(Stop.buildFromBusStop(stops[i]));
        return list;
    }

    protected static Stop buildFromBusStop(BusStop busStop) {
        Stop newStop = new Stop();
        newStop.id = busStop.getCode();
        newStop.name = busStop.getName();
        newStop.address = busStop.getAddress();
        newStop.location = new Point(busStop.getLatitude(), busStop.getLongitude());
        return newStop;
    }

    private static
    Stop buildFromGtfsStop(org.onebusaway.gtfs.model.Stop stop) {
        Stop newStop = new Stop();
        newStop.id = Integer.parseInt(stop.getId().getId());
        newStop.name = stop.getName();
        newStop.address = "";
        newStop.location = new Point(stop.getLat(), stop.getLon());
        return newStop;
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
