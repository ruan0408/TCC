package com.smartsampa.busapi;

import com.smartsampa.utils.Point;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
        return new ToStringBuilder(this)
                .append("points", points)
                .append("traveledDistances", traveledDistances)
                .toString();
    }
}
