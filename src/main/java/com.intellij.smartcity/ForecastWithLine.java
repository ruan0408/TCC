package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class ForecastWithLine {

    private String currentTime;
    private BusStopNow[] busStops;

    @JsonCreator
    public ForecastWithLine(@JsonProperty("hr") String currentTime,
                            @JsonProperty("ps") BusStopNow[] busStops) {

        this.currentTime = currentTime;
        this.busStops = busStops;
    }
}
