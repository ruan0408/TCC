package com.smartsampa.busapi;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public interface Bus {

    String getPrefixNumber();

    boolean isWheelChairCapable();

    Double getLatitude();

    Double getLongitude();
}
