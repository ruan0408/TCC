package com.smartsampa.busapi;

import com.google.common.base.Objects;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 22/03/2016.
 */
public class BusLane {

    private String name;
    private String address;
    private Shape shape;
    private String heading;
    private String addressStart;
    private String addressEnd;
    private boolean isBothWays;
    private String district;
    private double sizeInMeters;
//    private int headingCode;
    private String regionName;
    private int regionCode;
    private String type;//a direita?
//    private int typeCode;


    public static List<BusLane> searchByTerm(String term) {
        List<BusLane> allBusLanes = getAllBusLanes();
        return allBusLanes.stream()
                .filter(lane -> StringUtils.containsIgnoreCase(lane.getName(), term) ||
                                StringUtils.containsIgnoreCase(lane.getAddress(), term))
                .collect(Collectors.toList());
    }

    public static List<BusLane> getAllBusLanes() {
        return DataToBusLaneFacade.getAllBusLanes();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setAddressStart(String addressStart) {
        this.addressStart = addressStart;
    }

    public void setAddressEnd(String addressEnd) {
        this.addressEnd = addressEnd;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setSizeInMeters(double sizeInMeters) {
        this.sizeInMeters = sizeInMeters;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("address", address)
                .add("shape", shape)
                .add("heading", heading)
                .add("addressStart", addressStart)
                .add("addressEnd", addressEnd)
                .add("isBothWays", isBothWays)
                .add("district", district)
                .add("sizeInMeters", sizeInMeters)
                .add("regionName", regionName)
                .add("regionCode", regionCode)
                .add("type", type)
                .toString();
    }
}
