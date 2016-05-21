package com.smartsampa.busapi.model;

/**
 * Created by ruan0408 on 30/04/2016.
 */
public interface BusLane {

    String getName();

    String getAddress();

    String getAddressStart();

    String getAddressEnd();

    String getHeading();

    String getDistrict();

    String getImplantationDate();

    String getStartWorkingTime();

    String getEndWorkingTime();

    String getStreetSide();

    int getAmountWorkingHours();

    int getRegionCode();

    double getSizeInMeters();

    Shape getShape();
}
