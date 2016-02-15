package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class Bus {

    private String prefixNumber;
    private boolean wheelChairCapable;
    private double latitude;
    private double longitude;

    @JsonCreator
    protected Bus(@JsonProperty("p") String prefixNumber,
                  @JsonProperty("a")boolean wheelChairCapable,
                  @JsonProperty("py")double latitude,
                  @JsonProperty("px")double longitude) {
        this.prefixNumber = prefixNumber;
        this.wheelChairCapable = wheelChairCapable;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return The prefix number of the bus.
     */
    public String getPrefixNumber() {
        return prefixNumber;
    }

    /**
     * @return True if the bus is wheelchar capable. False otherwise.
     */
    public boolean isWheelChairCapable() {
        return wheelChairCapable;
    }

    /**
     * @return The latitude coordinate of the bus.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return The longitude coordinate of the bus.
     */
    public double getLongitude() {
        return longitude;
    }
}
