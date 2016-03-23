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
    private Shape  shape;
    private String heading;
    private String addressStart;
    private String addressEnd;
    private String district;
    private double sizeInMeters;
    private String regionName;
    private int    regionCode;
    private String streetSide;
    private String implantationDate;
    private String startWorkingTime;
    private String endWorkingTime;
    private int    amoutWorkingHours;

//    private int typeCode;
//    private int headingCode;

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

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setRegionCode(int regionCode) {
        this.regionCode = regionCode;
    }

    public void setStreetSide(String streetSide) {
        this.streetSide = streetSide;
    }

    public void setImplantationDate(String implantationDate) {
        this.implantationDate = implantationDate;
    }

    public void setStartWorkingTime(String startWorkingTime) {
        this.startWorkingTime = startWorkingTime;
    }

    public void setEndWorkingTime(String endWorkingTime) {
        this.endWorkingTime = endWorkingTime;
    }

    public void setAmoutWorkingHours(int amoutWorkingHours) {
        this.amoutWorkingHours = amoutWorkingHours;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Shape getShape() {
        return shape;
    }

    public String getHeading() {
        return heading;
    }

    public String getAddressStart() {
        return addressStart;
    }

    public String getAddressEnd() {
        return addressEnd;
    }

    public String getDistrict() {
        return district;
    }

    public double getSizeInMeters() {
        return sizeInMeters;
    }

    public String getRegionName() {
        return regionName;
    }

    public int getRegionCode() {
        return regionCode;
    }

    public String getStreetSide() {
        return streetSide;
    }

    public String getImplantationDate() {
        return implantationDate;
    }

    public String getStartWorkingTime() {
        return startWorkingTime;
    }

    public String getEndWorkingTime() {
        return endWorkingTime;
    }

    public int getAmoutWorkingHours() {
        return amoutWorkingHours;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("address", address)
                .add("shape", shape.toString())
                .add("heading", heading)
                .add("addressStart", addressStart)
                .add("addressEnd", addressEnd)
                .add("district", district)
                .add("sizeInMeters", sizeInMeters)
                .add("regionName", regionName)
                .add("regionCode", regionCode)
                .add("streetSide", streetSide)
                .add("implantationDate", implantationDate)
                .add("startWorkingTime", startWorkingTime)
                .add("endWorkingTime", endWorkingTime)
                .add("amoutWorkingHours", amoutWorkingHours)
                .toString();
    }
}
