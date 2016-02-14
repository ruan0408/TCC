package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class BusStopWithLines {

    private BusStop busStop;
    private BusLineNow[] busLines;

    @JsonCreator
    public BusStopWithLines(@JsonProperty("cp") int busStopCode,
                            @JsonProperty("np") String busStopName,
                            @JsonProperty("py") double busStopLatitude,
                            @JsonProperty("px") double busStopLongitude,
                            @JsonProperty("l") BusLineNow[] busLines) {

        busStop = new BusStop(busStopCode, busStopName, busStopLatitude, busStopLongitude);
        this.busLines = busLines;
    }
}
