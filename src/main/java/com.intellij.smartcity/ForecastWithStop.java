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
    public ForecastWithStop(@JsonProperty("hr") String currentTime,
                            @JsonProperty("p")BusStopWithLines busStopWithLines) {

        this.currentTime = currentTime;
        this.busStopWithLines = busStopWithLines;
    }
}
