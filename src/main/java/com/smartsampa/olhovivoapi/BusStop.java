package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartsampa.busapi.model.AbstractStop;
import com.smartsampa.utils.Point;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusStop extends AbstractStop {

    public int code;
    public String name;
    public String address;
    public double latitude;
    public double longitude;

    BusStop() {};

    @JsonCreator
    BusStop(@JsonProperty("CodigoParada") int code,
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
