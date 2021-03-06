package com.smartsampa.gtfswrapper;

import com.smartsampa.busapi.Shape;
import com.smartsampa.utils.Point;
import org.onebusaway.gtfs.model.ShapePoint;

import java.util.List;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public class GtfsShapeAdapter implements Shape {

    private Point[] points;
    private double[] traveledDistances;

    public GtfsShapeAdapter(List<ShapePoint> shapePoints) {

        shapePoints.sort(ShapePoint::compareTo);

        points = new Point[shapePoints.size()];
        traveledDistances = new double[shapePoints.size()];

        for (int i = 0; i < shapePoints.size(); i++) {
            ShapePoint p = shapePoints.get(i);
            points[i] = new Point(p.getLat(), p.getLon());
            traveledDistances[i] = p.getDistTraveled();
        }
    }

    @Override
    public Point[] getPoints() {
        return points;
    }

    @Override
    public double[] getTraveledDistances() {
        return traveledDistances;
    }
}
