package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class ForecastWithStopAndLine {

    private String currentTime;
    private BusStopWithLines busStopWithLines;

    @JsonCreator
    protected ForecastWithStopAndLine(@JsonProperty("hr") String currentTime,
                                      @JsonProperty("p")BusStopWithLines busStopWithLines) {

        this.currentTime = currentTime;
        this.busStopWithLines = busStopWithLines;
    }

    /**
     * @return The time this response was sent.
     */
    public String getCurrentTime() {
        return currentTime;
    }


    public BusStop getBusStop() {
        return busStopWithLines.getBusStop();
    }

    /**
     * @return The bus lines related to the request. It should be an array of one.
     * This is just in case it's not a one element array. For the expected case,
     * use getBuses().
     */
    public BusLineNow[] getBusLines() {
        return busStopWithLines.getBusLines();
    }

    /**
     * @return The current state of the buses running on the related line,
     * with respect to the related bus stop.
     */
    public BusNow[] getBuses() {
        if (busStopWithLines == null) return null;
        return busStopWithLines.getBuses();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("currentTime", currentTime)
                .add("busStopWithLines", busStopWithLines)
                .toString();
    }
}
