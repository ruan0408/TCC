package com.ws;

import com.fasterxml.jackson.annotation.JsonView;
import com.smartsampa.busapi.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruan0408 on 21/05/2016.
 */
abstract class TripMixin {

    @JsonView(View.TripSummary.class)
    public abstract String getId();

    @JsonView(View.TripSummary.class)
    public abstract String getNumberSign();

    @JsonView(View.TripSummary.class)
    public abstract Heading getHeading();

    @JsonView(View.TripSummary.class)
    public abstract String getDestinationSign();

    @JsonView(View.TripSummary.class)
    public abstract String getWorkingDays();

    @JsonView(View.TripSummary.class)
    public abstract Double getFarePrice();

    @JsonView(View.TripSummary.class)
    public abstract Boolean isCircular();

    @JsonView(View.TripComplete.class)
    public abstract List<Stop> getStops();

    @JsonView(View.TripComplete.class)
    public abstract Shape getShape();

    @JsonView(View.TripComplete.class)
    public abstract int getDepartureIntervalInSecondsNow();

    @JsonView(View.NotIncluded.class)
    public abstract Map<Stop, List<PredictedBus>> getPredictionsPerStop();

    @JsonView(View.NotIncluded.class)
    public abstract Set<Bus> getAllRunningBuses();

    @JsonView(View.NotIncluded.class)
    public abstract List<PredictedBus> getPredictionsAtStop(Stop stop);

    @JsonView(View.NotIncluded.class)
    public abstract Integer getOlhovivoId();

    @JsonView(View.NotIncluded.class)
    public abstract String getGtfsId();

}
