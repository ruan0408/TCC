package com.intellij.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class BusStopWithLines {

    private BusStop busStop;
    private BusLineNow[] busLines;

    @JsonCreator
    protected BusStopWithLines(@JsonProperty("cp") int busStopCode,
                               @JsonProperty("np") String busStopName,
                               @JsonProperty("py") double busStopLatitude,
                               @JsonProperty("px") double busStopLongitude,
                               @JsonProperty("l") BusLineNow[] busLines) {

        busStop = new BusStop(busStopCode, busStopName, busStopLatitude, busStopLongitude);
        this.busLines = busLines;
    }

    /**
     * @return The bus stop related to the request.
     */
    public BusStop getBusStop() {
        return busStop;
    }

    public BusLineNow[] getBusLines() {
        return busLines;
    }

    public BusNow[] getBuses() {
        if (busLines == null) return null;
        if (busLines.length > 1) return null;

        return busLines[0].getVehicles();
    }
}
