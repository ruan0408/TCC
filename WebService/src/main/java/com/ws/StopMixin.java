package com.ws;

import com.fasterxml.jackson.annotation.JsonView;
import com.smartsampa.busapi.PredictedBus;
import com.smartsampa.busapi.Trip;
import com.smartsampa.utils.Point;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruan0408 on 21/05/2016.
 */
abstract public class StopMixin {

    @JsonView(View.StopSummary.class)
    abstract public Integer getId();

    @JsonView(View.StopSummary.class)
    abstract public String getName();

    @JsonView(View.StopSummary.class)
    abstract public String getReference();

    @JsonView(View.StopSummary.class)
    abstract public String getAddress();

    @JsonView(View.StopSummary.class)
    abstract public Point getLocation();

    @JsonView(View.StopComplete.class)
    abstract public Set<Trip> getTrips();

    @JsonView(View.NotIncluded.class)
    abstract public Map<Trip, List<PredictedBus>> getPredictionsPerTrip();

    @JsonView(View.NotIncluded.class)
    abstract public List<PredictedBus> getPredictedBusesOfTrip(Trip trip);

}
