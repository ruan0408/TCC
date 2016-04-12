package com.smartsampa.risk;

import com.google.common.base.Objects;
import com.smartsampa.busapi.Point;

import java.util.List;

/**
 * Created by ruan0408 on 8/04/2016.
 */
public class Pluviometer {

    private Point location;
    private String address;
    private String locality;

    public static List<Pluviometer> getAllPluviometers() {
        return PluviometerFacade.getAllPluviometers();
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("location", location)
                .add("address", address)
                .add("locality", locality)
                .toString();
    }
}
