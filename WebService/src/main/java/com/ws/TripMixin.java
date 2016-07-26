package com.ws;

import com.fasterxml.jackson.annotation.JsonView;
import com.smartsampa.busapi.Heading;
import com.smartsampa.busapi.Shape;

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
    public abstract Shape getShape();

    @JsonView(View.TripComplete.class)
    public abstract int getDepartureIntervalInSecondsNow();
}
