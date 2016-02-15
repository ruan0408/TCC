package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusLinePositions {

    private String currentTime;
    private Bus[] vehicles;

    @JsonCreator
    protected BusLinePositions(@JsonProperty("hr") String currentTime,
                               @JsonProperty("vs") Bus[] vehicles) {

        this.currentTime = currentTime;
        this.vehicles = vehicles;
    }

    /**
     * @return The time the response was sent.
     */
    public String getCurrentTime() {
        return currentTime;
    }

    /**
     * @return The vehicles on this line.
     */
    public Bus[] getVehicles() {
        return vehicles;
    }
}
