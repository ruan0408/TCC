package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class Bus {

    private String prefixNumber;
    private boolean isWheelChairCapable;
    private double latitude;
    private double longitude;

    @JsonCreator
    protected Bus(@JsonProperty("p") String prefixNumber,
                  @JsonProperty("a")boolean isWheelChairCapable,
                  @JsonProperty("py")double latitude,
                  @JsonProperty("px")double longitude) {
        this.prefixNumber = prefixNumber;
        this.isWheelChairCapable = isWheelChairCapable;
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
        return isWheelChairCapable;
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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("prefixNumber", prefixNumber)
                .add("isWheelChairCapable", isWheelChairCapable)
                .add("latitude", latitude)
                .add("longitude", longitude)
                .toString();
    }
}
