package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 12/02/2016.
 */

public class BusLineNow extends BusLine {

    public int numberOfVehicles;
    public BusNow[] vehicles;

    @JsonCreator
    protected BusLineNow(@JsonProperty("cl") int busLineCode,
                         @JsonProperty("c") String fullNumberSign,
                         @JsonProperty("sl") int heading,
                         @JsonProperty("lt0") String destinationSignMTST,
                         @JsonProperty("lt1") String destinationSignSTMT,
                         @JsonProperty("qv") int numberOfVehicles,
                         @JsonProperty("vs") BusNow[] vehicles) {

        this.code = busLineCode;
        this.numberSign = getNumberSign(fullNumberSign);
        this.type = getType(fullNumberSign);
        this.heading = heading;
        this.destinationSignMTST = destinationSignMTST;
        this.destinationSignSTMT = destinationSignSTMT;
        this.numberOfVehicles = numberOfVehicles;
        this.vehicles = vehicles;
    }

    private String getNumberSign(String fullNumberSign) {
        return fullNumberSign.split("-")[0];
    }

    private int getType(String fullNumberSign) {
        return Integer.parseInt(fullNumberSign.split("-")[1]);
    }

    public BusNow[] getVehicles() {return vehicles;}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("numberOfVehicles", numberOfVehicles)
                .append("vehicles", vehicles)
                .toString();
    }
}
