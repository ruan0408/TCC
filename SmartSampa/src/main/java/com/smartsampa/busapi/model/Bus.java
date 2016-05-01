package com.smartsampa.busapi.model;

import com.smartsampa.utils.Point;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public interface Bus {

    String getPrefixNumber();
    boolean isWheelChairCapable();
    Point getLocation();
}
