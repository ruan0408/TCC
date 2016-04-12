package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
        if (busStopWithLines == null) return null;
        return busStopWithLines.getBusStop();
    }

    /**
     * @return The bus lines that pass by this bus stop.
     */
    public BusLineNow[] getBusLines() {
        if (busStopWithLines == null) return null;
        return busStopWithLines.getBusLines();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("currentTime", currentTime)
                .append("busStopWithLines", busStopWithLines)
                .toString();
    }
}
