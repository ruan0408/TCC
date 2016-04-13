package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartsampa.model.Stop;
import com.smartsampa.utils.Point;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusStop extends Stop {

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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public Integer getOlhovivoId() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public Point getLocation() { return new Point(latitude, longitude);}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("name", name)
                .append("address", address)
                .append("latitude", latitude)
                .append("longitude", longitude)
                .toString();
    }
}
