package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class StopWithTrips extends OlhovivoStop {

    public TripNow[] tripsNow;

    @JsonCreator
    protected StopWithTrips(@JsonProperty("cp") int stopCode,
                            @JsonProperty("np") String stopName,
                            @JsonProperty("py") double stopLatitude,
                            @JsonProperty("px") double stopLongitude,
                            @JsonProperty("l") TripNow[] trips) {

        this.code = stopCode;
        this.name = stopName;
        this.latitude = stopLatitude;
        this.longitude = stopLongitude;
        this.tripsNow = trips;
    }

    public TripNow[] getTripsNow() {
        return tripsNow;
    }

    public BusNow[] getBuses() {
        if (tripsNow == null) return null;
        if (tripsNow.length > 1) return null;

        return tripsNow[0].getVehicles();
    }
}
