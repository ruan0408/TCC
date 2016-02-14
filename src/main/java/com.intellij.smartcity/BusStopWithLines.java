package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class BusStopWithLines {

    private BusStop busStop;
    private BusLineNow[] busLines;
//cp np py px l

    @JsonCreator
    public BusStopWithLines(@JsonProperty("cp") int busStopCode,
                            @JsonProperty("np") String busStopName,
                            @JsonProperty("py") double busStopLatitude,
                            @JsonProperty("px") double busStopLongitude,
                            @JsonProperty("l") BusLineNow[] busLines) {

        busStop.setCode(busStopCode);
        busStop.setName(busStopName);
        busStop.setLatitude(busStopLatitude);
        busStop.setLongitude(busStopLongitude);
        this.busLines = busLines;
    }

//    @JsonProperty("l")
//    public BusLineNow[] getBusLines() {
//        return busLines;
//    }
}
