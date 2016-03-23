package com.smartsampa.busapi;

import com.google.common.base.Objects;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class Shape {

    private Point[] points;
    private double[] traveledDistances;

    public Shape(Point[] points, double[] traveledDistances) {
        this.points = points;
        this.traveledDistances = traveledDistances;
    }

    public Shape(List<Point> points) {
        this.points = points.toArray(new Point[points.size()]);
    }

    public int size() {
        return points.length;
    }

    public Point[] getPoints() {return points;}

    public double[] getTraveledDistances() {return traveledDistances;}

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("points", Arrays.toString(points))
                .add("traveledDistances", Arrays.toString(traveledDistances))
                .toString();
    }
}
