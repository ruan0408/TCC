package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class BusStopNow extends BusStop {

    public BusNow[] vehicles;

    @JsonCreator
    protected BusStopNow(@JsonProperty("cp") int busStopCode,
                         @JsonProperty("np") String busStopName,
                         @JsonProperty("py") double busStopLatitude,
                         @JsonProperty("px") double busStopLongitude,
                         @JsonProperty("vs") BusNow[] vehicles ) {

        this.code = busStopCode;
        this.name = busStopName;
        this.latitude = busStopLatitude;
        this.longitude = busStopLongitude;
        this.vehicles = vehicles;
    }

    public BusNow[] getVehicles() {return vehicles;}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("vehicles", vehicles)
                .toString();
    }
}
