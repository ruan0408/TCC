package com.intellij.publictransportapi.implementation;

import org.apache.commons.lang.text.StrBuilder;

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

    public int size() {
        return points.length;
    }

    public Point[] getPoints() {return points;}

    public double[] getTraveledDistances() {return traveledDistances;}

    @Override
    public String toString() {
        StrBuilder builder = new StrBuilder();
        for (int i = 0; i < points.length; i++) {
            builder.appendln(points[i].toString()+" "+traveledDistances[i]);
        }
        return builder.toString();
    }
}
