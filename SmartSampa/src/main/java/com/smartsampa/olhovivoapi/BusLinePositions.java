package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("currentTime", currentTime)
                .append("vehicles", vehicles)
                .toString();
    }
}
