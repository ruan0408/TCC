package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class ForecastWithStopAndTrip {

    public String currentTime;
    public StopWithTrips stopWithTrips;

    @JsonCreator
    protected ForecastWithStopAndTrip(@JsonProperty("hr") String currentTime,
                                      @JsonProperty("p") StopWithTrips stopWithTrips) {

        this.currentTime = currentTime;
        this.stopWithTrips = stopWithTrips;
    }

    public BusNow[] getBuses() {
        if (stopWithTrips == null) return null;
        return stopWithTrips.getBuses();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("currentTime", currentTime)
                .append("stopWithTrips", stopWithTrips)
                .toString();
    }
}
