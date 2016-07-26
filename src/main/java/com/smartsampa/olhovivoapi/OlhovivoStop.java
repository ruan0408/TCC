package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartsampa.busapi.Stop;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class OlhovivoStop extends Stop {

    public int code;
    public String name;
    public String address;
    public double latitude;
    public double longitude;

    OlhovivoStop() {};

    @JsonCreator
    OlhovivoStop(@JsonProperty("CodigoParada") int code,
                 @JsonProperty("Nome") String name,
                 @JsonProperty("Endereco") String address,
                 @JsonProperty("Latitude") double latitude,
                 @JsonProperty("Longitude") double longitude) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public Integer getId() { return code; }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public Double getLatitude() { return latitude; }

    @Override
    public Double getLongitude() { return longitude; }
}
