package com.smartsampa.busapi2.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruan0408 on 28/04/2016.
 */
public interface Trip {

    Map<Stop, List<PredictedBus>> getAllPredictions();

    List<PredictedBus> getPredictionsAtStop(Stop stop);

    Set<Bus> getAllRunningBuses();

    List<Stop> getStops();

    Shape getShape();

    int getDepartureIntervalInSecondsAtTime(String hhmm);

    String getNumberSign();

    Heading getHeading();

    String getDestinationSign();

    String getWorkingDays();

    Double getFarePrice();

    Boolean isCircular();

    Integer getOlhovivoId();

    String getGtfsId();
}
