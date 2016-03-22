package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusStop {

    @JsonProperty("CodigoParada") private int code;
    @JsonProperty("Nome") private String name;
    @JsonProperty("Endereco") private String address;
    @JsonProperty("Latitude") private double latitude;
    @JsonProperty("Longitude") private double longitude;

    protected BusStop(int code, String name, double latitude, double longitude) {
        this.code = code;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @JsonCreator
    protected BusStop(@JsonProperty("CodigoParada") int code,
                      @JsonProperty("Nome") String name,
                      @JsonProperty("Endereco") String address,
                      @JsonProperty("Latitude") double latitude,
                      @JsonProperty("Longitude") double longitude) {
        this(code, name, latitude, longitude);
        this.address = address;
    }

    /**
     * @return The code of this bus stop.
     */
    public int getCode() {
        return code;
    }

    /**
     * @return The name of this bus stop.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The address of this bus stop.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return The latitude of this bus stop.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return The longitude of this bus stop.
     */
    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("code", code)
                .add("name", name)
                .add("address", address)
                .add("latitude", latitude)
                .add("longitude", longitude)
                .toString();
    }
}
