package com.smartsampa.busapi;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruan0408 on 28/04/2016.
 */
public interface Trip extends Mergeable {

    String getId();

    String getNumberSign();

    Heading getHeading();

    String getDestinationSign();

    String getWorkingDays();

    Double getFarePrice();

    Boolean isCircular();

    List<Stop> getStops();

    Shape getShape();

    int getDepartureIntervalInSecondsAtTime(String hhmm);

    int getDepartureIntervalInSecondsNow();

    Integer getOlhovivoId();

    String getGtfsId();

    Map<Stop, List<PredictedBus>> getPredictionsPerStop();

    List<PredictedBus> getPredictionsAtStop(Stop stop);

    Set<Bus> getAllRunningBuses();
}
