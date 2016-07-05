package com.smartsampa.busapi;

import com.smartsampa.utils.Point;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruan0408 on 5/07/2016.
 */
final class NullStop implements Stop {

    private static final Stop ourInstance = new NullStop();

    private NullStop() {}

    static Stop getInstance() {return ourInstance;}

    @Override
    public Map<Trip, List<PredictedBus>> getPredictionsPerTrip() {
        return Collections.emptyMap();
    }

    @Override
    public List<PredictedBus> getPredictedBusesOfTrip(Trip trip) {
        return Collections.emptyList();
    }

    @Override
    public Set<Trip> getTrips() {
        return Collections.emptySet();
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getReference() {
        return null;
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public Point getLocation() {
        return null;
    }

    @Override
    public void merge(Mergeable m) {}
}
