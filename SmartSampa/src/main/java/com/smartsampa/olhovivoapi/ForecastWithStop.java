package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class ForecastWithStop {

    @JsonProperty("hr") public String currentTime;
    @JsonProperty("p") public BusStopWithLines busStopWithLines;

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
