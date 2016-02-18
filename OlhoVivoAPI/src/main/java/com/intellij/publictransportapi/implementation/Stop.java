package com.intellij.publictransportapi.implementation;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class Stop {

    public int getId() {
        return 0;
    }

    public String getName() {
        return null;
    }

    public String getAddress() {
        return null;
    }

    public Point getLocation() {
        return null;
    }

    public String getDescription() {
        return null;
    }

    public Trip getAllTrips() {
        return null;
    }

    public List<PredictedBus> getPredictedBuses(Trip trip) {
        return null;
    }

    public Map<Trip, List<PredictedBus>> getAllPredictions() {
        return null;
    }
}
