package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class ForecastWithLine {

    private String currentTime;
    private BusStopNow[] busStops;

    @JsonProperty("hr")
    public String getCurrentTime() {
        return currentTime;
    }

    @JsonProperty("ps")
    public BusStopNow[] getBusStops() {
        return busStops;
    }
}
