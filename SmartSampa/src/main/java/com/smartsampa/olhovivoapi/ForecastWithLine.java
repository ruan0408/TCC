package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class ForecastWithLine {

    @JsonProperty("hr") public String currentTime;
    @JsonProperty("ps") public BusStopNow[] busStops;

    public BusStopNow[] getBusStops() {
        return busStops;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("currentTime", currentTime)
                .append("busStops", busStops)
                .toString();
    }
}
