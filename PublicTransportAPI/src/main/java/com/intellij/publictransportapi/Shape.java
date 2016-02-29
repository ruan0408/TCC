package com.intellij.publictransportapi;

import com.intellij.utils.Utils;
import org.apache.commons.lang.text.StrBuilder;
import org.onebusaway.gtfs.model.ShapePoint;

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

    public int size() {
        return points.length;
    }

    public Point[] getPoints() {return points;}

    public double[] getTraveledDistances() {return traveledDistances;}

    protected static Shape convert(List<ShapePoint> shapes) {
        shapes.sort(Utils.compBySequence);
        Point[] points = new Point[shapes.size()];
        double[] distances = new double[shapes.size()];

        for (int i = 0; i < shapes.size(); i++) {
            ShapePoint p = shapes.get(i);
            points[i] = new Point(p.getLat(), p.getLon());
            distances[i] = p.getDistTraveled();
        }
        return new Shape(points, distances);
    }

    @Override
    public String toString() {
        StrBuilder builder = new StrBuilder();
        for (int i = 0; i < points.length; i++) {
            builder.appendln(points[i].toString()+" "+traveledDistances[i]);
        }
        return builder.toString();
    }
}
