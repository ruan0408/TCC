package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class ForecastWithTrip {

    @JsonProperty("hr") public String currentTime;
    @JsonProperty("ps") public StopNow[] stopsNow;

    public StopNow[] getStopsNow() {
        return stopsNow;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("currentTime", currentTime)
                .append("stopsNow", stopsNow)
                .toString();
    }
}
