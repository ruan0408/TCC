package com.smartsampa.busapi;

/**
 * Created by ruan0408 on 5/07/2016.
 */
final class NullTrip extends Trip {

    private static final Trip ourInstance = new NullTrip();

    private NullTrip() {}

    static Trip getInstance() { return ourInstance; }

    @Override
    public String getNumberSign() {
        return null;
    }

    @Override
    public Heading getHeading() {
        return null;
    }

    @Override
    public String getDestinationSign() {
        return null;
    }

    @Override
    public String getWorkingDays() {
        return null;
    }

    @Override
    public Double getFarePrice() {
        return null;
    }

    @Override
    public Boolean isCircular() {
        return null;
    }

    @Override
    public Shape getShape() {
        return null;
    }

    @Override
    public int getDepartureIntervalInSecondsAtTime(String hhmm) {
        return 0;
    }

    @Override
    public int getDepartureIntervalInSecondsNow() {
        return 0;
    }

    @Override
    public Integer getOlhovivoId() {
        return null;
    }

    @Override
    public String getGtfsId() {
        return null;
    }
}
