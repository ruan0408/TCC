package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class BusStopNow {

    private BusStop busStop;
    private BusNow[] vehicles;

    @JsonCreator
    public BusStopNow(@JsonProperty("cp") int busStopCode,
                      @JsonProperty("np") String busStopName,
                      @JsonProperty("py") double busStopLatitude,
                      @JsonProperty("px") double busStopLongitude,
                      @JsonProperty("vs") BusNow[] vehicles ) {

        busStop = new BusStop(busStopCode, busStopName, busStopLatitude, busStopLongitude);
        this.vehicles = vehicles;
    }
}
