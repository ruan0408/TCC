package com.intellij.publictransportapi;

import com.intellij.utils.APIConnectionException;
import com.intellij.olhovivoapi.*;
import org.apache.commons.lang.text.StrBuilder;
import org.onebusaway.gtfs.model.StopTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by ruan0408 on 17/02/2016.
 */
//TODO maybe add a getDescription.
public class Stop {

    private Integer id = null;
    private String name;
    private String address;
    private Point location;

    protected Stop() {}

    protected Stop(int id, String name, Point location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public int getId() {return id;}

    public String getName() {return name;}

    public String getAddress() {return address;}

    public Point getLocation() {return location;}

    public void setAddress(String address) {this.address = address;}

    public List<Trip> getAllTrips() {
        Predicate<StopTime> predicate =
                s -> s.getStop().getId().getId().equals(id+"");

        List<StopTime> stopTimes = API.filterGtfsToList("getAllStopTimes", predicate);

        List<Trip> trips = new ArrayList<>(stopTimes.size());
        for (StopTime stopTime : stopTimes)
            trips.add(Trip.buildFrom(stopTime.getTrip()));

        return trips;
    }

    public List<PredictedBus> getPredictedBuses(Trip trip) throws APIConnectionException {
        ForecastWithStopAndLine forecast =
                API.getForecastByStopAndTrip(trip.getInternalId(), id);

        if (forecast.getBuses() == null) return new ArrayList<>();

        return PredictedBus.convert(forecast.getBuses());
    }

    public Map<Trip, List<PredictedBus>> getAllPredictions() throws APIConnectionException {
        ForecastWithStop forecast = API.getForecastByStop(id);

        BusLineNow[] busLineNowArray = forecast.getBusLines();
        Map<Trip, List<PredictedBus>> map = new HashMap<>(busLineNowArray.length);

        for (int i = 0; i < busLineNowArray.length; i++) {
            BusLineNow lineNow = busLineNowArray[i];
            map.put(Trip.buildFrom(lineNow.getBusLine()),
                    PredictedBus.convert(lineNow.getVehicles()));
        }
        return map;
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
    public boolean equals(Object obj) {
        if (!(obj instanceof Stop)) return false;

        Stop that = (Stop) obj;
        if (this.id != null && this.id == that.id) return true;
        if (this.name.equals(that.name) && this.address.equals(that.address) &&
                this.getLocation().equals(that.location))
            return true;

        return false;
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
