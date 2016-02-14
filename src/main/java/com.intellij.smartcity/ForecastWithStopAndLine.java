package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class ForecastWithStopAndLine {

    private String currentTime;
    private BusStopWithLines busStopWithLines;

    @JsonCreator
    public ForecastWithStopAndLine(@JsonProperty("hr") String currentTime,
                                   @JsonProperty("p")BusStopWithLines busStopWithLines) {

        this.currentTime = currentTime;
        this.busStopWithLines = busStopWithLines;
    }
}
