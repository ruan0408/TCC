package com.smartsampa.busapi2.model;

import java.util.List;

/**
 * Created by ruan0408 on 29/04/2016.
 */
public interface Corridor {

    int getId();

    int getCodCot();

    String getName();

    List<Stop> getStops();
}
