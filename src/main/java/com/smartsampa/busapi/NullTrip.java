package com.smartsampa.busapi;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruan0408 on 5/07/2016.
 */
final class NullTrip implements Trip {

    private static final Trip ourInstance = new NullTrip();

    private NullTrip() {}

    static Trip getInstance() { return ourInstance; }

    @Override
    public Map<Stop, List<PredictedBus>> getPredictionsPerStop() {
        return Collections.emptyMap();
    }

    @Override
    public List<PredictedBus> getPredictionsAtStop(Stop stop) {
        return Collections.emptyList();
    }

    @Override
    public Set<Bus> getAllRunningBuses() {
        return Collections.emptySet();
    }

    @Override
    public String getId() { return null;}

    @Override
    public String getNumberSign() {
        return null;
    }

    @Override
    public Heading getHeading() {
        return null;
    }

    @Override
    public String getDestinationSign() {
        return null;
    }

    @Override
    public String getWorkingDays() {
        return null;
    }

    @Override
    public Double getFarePrice() {
        return null;
    }

    @Override
    public Boolean isCircular() {
        return null;
    }

    @Override
    public List<Stop> getStops() {
        return null;
    }

    @Override
    public Shape getShape() {
        return null;
    }

    @Override
    public int getDepartureIntervalInSecondsAtTime(String hhmm) {
        return 0;
    }

    @Override
    public int getDepartureIntervalInSecondsNow() {
        return 0;
    }

    @Override
    public Integer getOlhovivoId() {
        return null;
    }

    @Override
    public String getGtfsId() {
        return null;
    }

    @Override
    public void merge(Mergeable m) {}
}
