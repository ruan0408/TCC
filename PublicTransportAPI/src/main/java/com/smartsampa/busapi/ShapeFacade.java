package com.smartsampa.busapi;

import com.smartsampa.gtfsapi.GtfsAPI;
import org.onebusaway.gtfs.model.ShapePoint;

import java.util.List;

/**
 * Created by ruan0408 on 9/04/2016.
 */
public class ShapeFacade {

    protected static Shape shapePointsToShape(List<ShapePoint> shapes) {
        shapes.sort(GtfsAPI.COMP_BY_SHAPE_POINT_SEQUENCE);

        Point[] points = new Point[shapes.size()];
        double[] distances = new double[shapes.size()];

        for (int i = 0; i < shapes.size(); i++) {
            ShapePoint p = shapes.get(i);
            points[i] = new Point(p.getLat(), p.getLon());
            distances[i] = p.getDistTraveled();
        }
        return new Shape(points, distances);
    }
}
