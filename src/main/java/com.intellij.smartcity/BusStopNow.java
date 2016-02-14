package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 13/02/2016.
 */
public class BusStopNow {

    private BusStop busStop;
    private BusNow[] vehicles;
    //cp np py px vs


    public BusStopNow() {
    }

    public BusStopNow(BusStop busStop, BusNow[] vehicles) {
        this.busStop = busStop;
        this.vehicles = vehicles;
    }

    @JsonCreator
    public BusStopNow(@JsonProperty("cp") int busStopCode,
                      @JsonProperty("np") String busStopName,
                      @JsonProperty("py") double busStopLatitude,
                      @JsonProperty("px") double busStopLongitude,
                      @JsonProperty("vs") BusNow[] vehicles ) {

        busStop = new BusStop();
        busStop.setCode(busStopCode);
        busStop.setName(busStopName);
        busStop.setLatitude(busStopLatitude);
        busStop.setLongitude(busStopLongitude);
        this.vehicles = vehicles;

    }

//    @Override
//    @JsonProperty("cp")
//    public int getCode() {
//        return super.getCode();
//    }
//
//    @Override
//    @JsonProperty("np")
//    public String getName() {
//        return super.getName();
//    }
//
//    @Override
//    @JsonProperty("py")
//    public double getLatitude() {
//        return super.getLatitude();
//    }
//
//    @Override
//    @JsonProperty("px")
//    public double getLongitude() {
//        return super.getLongitude();
//    }
//
//    @JsonProperty("vs")
//    public BusNow[] getVehicles() {
//        return vehicles;
//    }
//
//    @Override
//    public String getAddress() {
//        return super.getAddress();
//    }
}
