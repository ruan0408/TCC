package com.smartsampa.busapi;

import java.util.List;

/**
 * Created by ruan0408 on 29/04/2016.
 */
public interface Corridor {

    static Corridor emptyCorridor() { return NullCorridor.getInstance(); }

    int getId();

    int getCodCot();

    String getName();

    List<Stop> getStops();
}
