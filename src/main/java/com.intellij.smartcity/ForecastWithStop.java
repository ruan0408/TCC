package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class ForecastWithStop {

    private String currentTime;
    private BusStopWithLines busStopWithLines;

    @JsonCreator
    protected ForecastWithStop(@JsonProperty("hr") String currentTime,
                               @JsonProperty("p")BusStopWithLines busStopWithLines) {

        this.currentTime = currentTime;
        this.busStopWithLines = busStopWithLines;
    }

    /**
     * @return The time the response was sent.
     */
    public String getCurrentTime() {
        return currentTime;
    }

    /**
     * @return The bus stop related to the request.
     */
    public BusStop getBusStop() {
        return busStopWithLines.getBusStop();
    }

    /**
     * @return The bus lines that pass by this bus stop.
     */
    public BusLineNow[] getBusLines() {
        return busStopWithLines.getBusLines();
    }
}
