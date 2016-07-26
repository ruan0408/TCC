package com.smartsampa.utils;

/**
 * Created by ruan0408 on 19/02/2016.
 */
public class Point {

    private double latitude;
    private double longitude;

    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    @Override
    public String toString() {
        return "("+ latitude +","+ longitude +")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) return false;
        Point that = (Point) obj;

        if (Math.abs(this.longitude -that.longitude) < 0.0000000000000001 &&
                Math.abs(this.latitude -that.latitude) < 0.0000000000000001)
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        long longPy = Double.doubleToLongBits(latitude);
        long longPx = Double.doubleToLongBits(longitude);
        int result = 17;
        result = 31 * result + (int)(longPy^(longPy>>>32));
        result = 31 * result + (int)(longPx^(longPx>>>32));
        return result;
    }
}
