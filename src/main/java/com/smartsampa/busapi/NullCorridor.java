package com.smartsampa.busapi;

import java.util.List;

/**
 * Created by ruan0408 on 5/07/2016.
 */
final class NullCorridor implements Corridor {

    private static final Corridor ourInstace = new NullCorridor();

    private NullCorridor() {}

    static Corridor getInstance() { return ourInstace; }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getCodCot() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public List<Stop> getStops() {
        return null;
    }
}
