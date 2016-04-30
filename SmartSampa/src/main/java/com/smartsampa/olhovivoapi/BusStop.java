package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartsampa.busapi2.model.AbstractStop;
import com.smartsampa.utils.Point;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusStop extends AbstractStop {

    @JsonProperty("CodigoParada") public int code;
    @JsonProperty("Nome") public String name;
    @JsonProperty("Endereco") public String address;
    @JsonProperty("Latitude") public double latitude;
    @JsonProperty("Longitude") public double longitude;


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
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
