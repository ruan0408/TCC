package com.intellij.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class BusStopNow {

    private BusStop busStop;
    private BusNow[] vehicles;

    @JsonCreator
    protected BusStopNow(@JsonProperty("cp") int busStopCode,
                         @JsonProperty("np") String busStopName,
                         @JsonProperty("py") double busStopLatitude,
                         @JsonProperty("px") double busStopLongitude,
                         @JsonProperty("vs") BusNow[] vehicles ) {

        busStop = new BusStop(busStopCode, busStopName, busStopLatitude, busStopLongitude);
        this.vehicles = vehicles;
    }

    /**
     * @return This bus stop.
     */
    public BusStop getBusStop() {
        return busStop;
    }

    /**
     * @return The current vehicles that pass by this bus stop.
     */
    public BusNow[] getVehicles() {
        return vehicles;
    }
}
