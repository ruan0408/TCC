package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class Bus {

    private String prefixNumber;
    private boolean wheelChairCapable;
    private double latitude;
    private double longitude;

    @JsonProperty("p")
    public String getPrefixNumber() {return prefixNumber;}
    @JsonProperty("a")
    public boolean isWheelChairCapable() {return wheelChairCapable;}
    @JsonProperty("py")
    public double getLatitude() {return latitude;}
    @JsonProperty("px")
    public double getLongitude() {return longitude;}
}
