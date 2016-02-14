package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusStop {

    @JsonProperty("CodigoParada") private int code;
    @JsonProperty("Nome") private String name;
    @JsonProperty("Endereco") private String address;
    @JsonProperty("Latitude") private double latitude;
    @JsonProperty("Longitude") private double longitude;

    public BusStop(int code, String name, double latitude, double longitude) {
        this.code = code;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @JsonCreator
    public BusStop(@JsonProperty("CodigoParada") int code,
                   @JsonProperty("Nome") String name,
                   @JsonProperty("Endereco") String address,
                   @JsonProperty("Latitude") double latitude,
                   @JsonProperty("Longitude") double longitude) {
        this(code, name, latitude, longitude);
        this.address = address;
    }
}
