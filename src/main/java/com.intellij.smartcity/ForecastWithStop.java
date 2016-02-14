package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class ForecastWithStop {

    private String currentTime;
    private BusStopWithLines busStopWithLines;

    @JsonProperty("hr")
    public String getCurrentTime() {
        return currentTime;
    }

    @JsonProperty("p")
    public BusStopWithLines getBusStopWithLines() {
        return busStopWithLines;
    }
}
