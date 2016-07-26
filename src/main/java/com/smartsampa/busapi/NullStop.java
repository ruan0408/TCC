package com.smartsampa.busapi;

/**
 * Created by ruan0408 on 5/07/2016.
 */
final class NullStop extends Stop {

    private static final Stop ourInstance = new NullStop();

    private NullStop() {}

    static Stop getInstance() {return ourInstance;}

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getReference() {
        return null;
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public Double getLatitude() { return null; }

    @Override
    public Double getLongitude() { return null; }
}
