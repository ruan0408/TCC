package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 12/02/2016.
 */

public class BusLineNow {

    public BusLine busLine;
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

        busLine = new BusLine(busLineCode, fullNumberSign, heading, destinationSignMTST,destinationSignSTMT);
        this.numberOfVehicles = numberOfVehicles;
        this.vehicles = vehicles;
    }

    public BusLine getBusLine() {return busLine;}

    public int getCode() {
        return busLine.getOlhovivoId();
    }

    public boolean isCircular() {
        return busLine.isCircular();
    }

    public String getDestinationSign() {
        return busLine.getNumberSign();
    }

    public int getHeading() {
        return busLine.getHeading();
    }

    public int getType() {
        return busLine.getType();
    }

    public String getInfo() {
        return busLine.getInfo();
    }

    public String getDestinationSignMTST() {
        return busLine.getDestinationSignMTST();
    }

    public String getDestinationSignSTMT() {
        return busLine.getDestinationSignSTMT();
    }

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public BusNow[] getVehicles() {
        return vehicles;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("busLine", busLine)
                .append("numberOfVehicles", numberOfVehicles)
                .append("vehicles", vehicles)
                .toString();
    }
}
