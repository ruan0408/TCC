package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartsampa.busapi2.model.Bus;
import com.smartsampa.utils.Point;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class OlhovivoBus implements Bus {

    @JsonProperty("p") public String prefixNumber;
    @JsonProperty("a") public boolean isWheelChairCapable;
    @JsonProperty("py") public double latitude;
    @JsonProperty("px") public double longitude;

    public OlhovivoBus() {}

    OlhovivoBus(String prefixNumber, boolean isWheelChairCapable,
                double latitude, double longitude) {
        this.prefixNumber = prefixNumber;
        this.isWheelChairCapable = isWheelChairCapable;
        this.latitude = latitude;
        this.longitude = longitude;
    }

//    @JsonCreator
//    protected OlhovivoBus(@JsonProperty("p") String prefixNumber,
//                          @JsonProperty("a")boolean isWheelChairCapable,
//                          @JsonProperty("py")double latitude,
//                          @JsonProperty("px")double longitude) {
//        this.prefixNumber = prefixNumber;
//        this.isWheelChairCapable = isWheelChairCapable;
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }

    public String getPrefixNumber() {
        return prefixNumber;
    }

    public boolean isWheelChairCapable() {
        return isWheelChairCapable;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Point getLocation() {
        return new Point(latitude, longitude);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OlhovivoBus{");
        sb.append("prefixNumber='").append(prefixNumber).append('\'');
        sb.append(", isWheelChairCapable=").append(isWheelChairCapable);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append('}');
        return sb.toString();
    }
}
