package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class StopNow extends OlhovivoStop {

    public BusNow[] vehicles;

    @JsonCreator
    protected StopNow(@JsonProperty("cp") int stopCode,
                      @JsonProperty("np") String stopName,
                      @JsonProperty("py") double stopLatitude,
                      @JsonProperty("px") double stopLongitude,
                      @JsonProperty("vs") BusNow[] vehicles ) {

        this.code = stopCode;
        this.name = stopName;
        this.latitude = stopLatitude;
        this.longitude = stopLongitude;
        this.vehicles = vehicles;
    }

    public BusNow[] getVehicles() {return vehicles;}

}
