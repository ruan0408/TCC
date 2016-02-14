package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusLinePositions {

//    private String hr;
//    private Bus[] vs;

    private String currenTime;
    private Bus[] vehicles;

    @JsonProperty("hr")
    public String getCurrenTime() {
        return currenTime;
    }

    @JsonProperty("vs")
    public Bus[] getVehicles() {
        return vehicles;
    }
}
