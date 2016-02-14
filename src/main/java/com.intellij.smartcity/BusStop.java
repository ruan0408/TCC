package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusStop {

    private int code;
    private String name;
    private String address;
    private double latitude;
    private double longitude;

    public BusStop() {}

    @JsonProperty("CodigoParada")
    public int getCode() {
        return code;
    }

    @JsonProperty("Nome")
    public String getName() {
        return name;
    }

    @JsonProperty("Endereco")
    public String getAddress() {
        return address;
    }

    @JsonProperty("Latitude")
    public double getLatitude() {
        return latitude;
    }

    @JsonProperty("Longitude")
    public double getLongitude() {
        return longitude;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
