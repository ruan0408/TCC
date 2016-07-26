package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class ForecastWithStop {

    @JsonProperty("hr") public String currentTime;
    @JsonProperty("p") public StopWithTrips stopWithTrips;

    public TripNow[] getBusLines() {
        if (stopWithTrips == null) return null;
        return stopWithTrips.getTripsNow();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("currentTime", currentTime)
                .append("stopWithTrips", stopWithTrips)
                .toString();
    }
}
