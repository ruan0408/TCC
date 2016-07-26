package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartsampa.busapi.Bus;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class OlhovivoBus implements Bus {

    public String prefixNumber;
    public boolean isWheelChairCapable;
    public double latitude;
    public double longitude;

    @JsonCreator
    OlhovivoBus(@JsonProperty("p") String prefixNumber,
                @JsonProperty("a") boolean isWheelChairCapable,
                @JsonProperty("py") double latitude,
                @JsonProperty("px") double longitude) {
        this.prefixNumber = prefixNumber;
        this.isWheelChairCapable = isWheelChairCapable;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPrefixNumber() {
        return prefixNumber;
    }

    public boolean isWheelChairCapable() {
        return isWheelChairCapable;
    }

    @Override
    public Double getLatitude() { return latitude; }

    @Override
    public Double getLongitude() { return longitude; }
}
