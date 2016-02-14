package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusLineNow extends BusLine {

//    private String c;
//    private String cl;
//    private int sl;
//    private String lt0;
//    private String lt1;
    //    private int qv;
    //    private BusNow[] vs;
    private int numberOfVehicles;
    private BusNow[] vehicles;

    @Override
    @JsonProperty("cl")
    public int getCode() {
        return super.getCode();
    }

    @Override
    @JsonProperty("c")
    public String getDestinationSign() {
        return super.getDestinationSign();
    }

    @Override
    @JsonProperty("sl")
    public int getHeading() {
        return super.getHeading();
    }

    @Override
    @JsonProperty("lt0")
    public String getDestinationSignMTST() {
        return super.getDestinationSignMTST();
    }

    @Override
    @JsonProperty("lt1")
    public String getDestinationSignSTMT() {
        return super.getDestinationSignSTMT();
    }

    @JsonProperty("qv")
    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    @JsonProperty("vs")
    public BusNow[] getVehicles() {
        return vehicles;
    }

    @Override
    public boolean isCircular() {
        return super.isCircular();
    }

    @Override
    public int getType() {
        return super.getType();
    }

    @Override
    public String getInfo() {
        return super.getInfo();
    }
}
