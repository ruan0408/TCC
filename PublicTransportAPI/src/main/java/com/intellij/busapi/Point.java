package com.intellij.busapi;

/**
 * Created by ruan0408 on 19/02/2016.
 */
public class Point {

    private double py;
    private double px;

    public Point(double py, double px) {
        this.py = py;
        this.px = px;
    }

    @Override
    public String toString() {
        return "("+py+","+px+")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) return false;
        Point that = (Point) obj;

        if (Math.abs(this.px-that.px) < 0.000001 &&
                Math.abs(this.py-that.py) < 0.000001)
            return true;

        return false;
    }

    @Override
    public int hashCode() {
        long longPy = Double.doubleToLongBits(py);
        long longPx = Double.doubleToLongBits(px);
        int result = 17;
        result = 31 * result + (int)(longPy^(longPy>>>32));
        result = 31 * result + (int)(longPx^(longPx>>>32));
        return result;
    }
}
