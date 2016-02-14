package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */

public class BusLineNow {

    private BusLine busLine;
    private int numberOfVehicles;
    private BusNow[] vehicles;

    @JsonCreator
    protected BusLineNow(@JsonProperty("cl") int busLineCode,
                         @JsonProperty("c") String busLineDestinationSign,
                         @JsonProperty("sl") int heading,
                         @JsonProperty("lt0") String destinationSignMTST,
                         @JsonProperty("lt1") String destinationSignSTMT,
                         @JsonProperty("qv") int numberOfVehicles,
                         @JsonProperty("vs") BusNow[] vehicles) {

        busLine = new BusLine(busLineCode, busLineDestinationSign, heading, destinationSignMTST,destinationSignSTMT);
        this.numberOfVehicles = numberOfVehicles;
        this.vehicles = vehicles;
    }


}
