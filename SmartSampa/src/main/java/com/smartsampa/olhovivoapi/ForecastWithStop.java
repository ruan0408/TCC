package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class ForecastWithStop {
    //TODO delete all the commented shit from the olhovivo classes and rethiink the methods
    @JsonProperty("hr") public String currentTime;
    @JsonProperty("p") public BusStopWithLines busStopWithLines;

//    @JsonCreator
//    protected ForecastWithStop(@JsonProperty("hr") String currentTime,
//                               @JsonProperty("p")BusStopWithLines busStopWithLines) {
//
//        this.currentTime = currentTime;
//        this.busStopWithLines = busStopWithLines;
//    }

    public String getCurrentTime() {
        return currentTime;
    }

    public BusStop getBusStop() {
        if (busStopWithLines == null) return null;
        return busStopWithLines.getBusStop();
    }

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
