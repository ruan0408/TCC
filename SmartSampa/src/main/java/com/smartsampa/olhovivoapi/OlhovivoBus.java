package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartsampa.model.Bus;
import com.smartsampa.utils.Point;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class OlhovivoBus implements Bus {

    private String prefixNumber;
    private boolean isWheelChairCapable;
    private double latitude;
    private double longitude;

    @JsonCreator
    protected OlhovivoBus(@JsonProperty("p") String prefixNumber,
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

    public Point getLocation() {
        return new Point(latitude, longitude);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("prefixNumber", prefixNumber)
                .append("isWheelChairCapable", isWheelChairCapable)
                .append("latitude", latitude)
                .append("longitude", longitude)
                .toString();
    }
}
