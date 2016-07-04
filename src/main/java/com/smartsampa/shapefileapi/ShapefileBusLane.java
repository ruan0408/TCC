package com.smartsampa.shapefileapi;

import com.smartsampa.busapi.BusLane;
import com.smartsampa.busapi.Shape;
import com.smartsampa.utils.Point;
import com.vividsolutions.jts.geom.Geometry;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.opengis.feature.simple.SimpleFeature;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

/**
 * Created by ruan0408 on 30/04/2016.
 */
public class ShapefileBusLane implements BusLane {

    private List<SimpleFeature> features;

    public ShapefileBusLane(List<SimpleFeature> features) {
        this.features = features;
    }

    public boolean containsTerm(String term) {
        if (containsIgnoreCase(getName(), term)) return true;
        if (containsIgnoreCase(getAddress(), term)) return true;
        if (containsIgnoreCase(getAddressStart(), term)) return true;
        if (containsIgnoreCase(getAddressEnd(), term)) return true;
        return false;
    }

    @Override
    public String getName() {
        return features.get(0).getAttribute("nm_denomin").toString();
    }

    @Override
    public String getAddress() {
        return features.get(0).getAttribute("nm_logrado").toString();
    }

    @Override
    public String getAddressStart() {
        return features.get(0).getAttribute("tx_inicio_").toString();
    }

    @Override
    public String getAddressEnd() {
        return features.get(0).getAttribute("tx_fim_log").toString();
    }

    @Override
    public String getHeading() {
        return features.get(0).getAttribute("tx_sentido").toString();
    }

    @Override
    public String getDistrict() {
        return features.get(0).getAttribute("tx_distrit").toString();
    }

    @Override
    public String getImplantationDate() {
        return features.get(0).getAttribute("dt_implant").toString();
    }

    @Override
    public String getStartWorkingTime() {
        return features.get(0).getAttribute("ho_inicio_").toString();
    }

    @Override
    public String getEndWorkingTime() {
        return features.get(0).getAttribute("ho_fim_fun").toString();
    }

    @Override
    public String getStreetSide() {
        return features.get(0).getAttribute("nm_tipo_fa").toString();
    }

    @Override
    public int getAmountWorkingHours() {
        return Integer.parseInt(features.get(0).getAttribute("qt_ho_func").toString());
    }

    @Override
    public int getRegionCode() {
        return Integer.parseInt(features.get(0).getAttribute("cd_regiao_").toString());
    }

    @Override
    public double getSizeInMeters() {
        return Integer.parseInt(features.get(0).getAttribute("qt_metro_f").toString());
    }

    @Override
    public Shape getShape() {
        Point[] points = getPointsFromFeatures(features);
        return new ShapefileShape(points);
    }

    private Point[] getPointsFromFeatures(List<SimpleFeature> featureList) {
        return featureList.stream()
                .map(SimpleFeature::getDefaultGeometry)
                .map(geom -> (Geometry)geom)
                .map(Geometry::getCoordinates)
                .flatMap(Arrays::stream)
                .map(coord -> new Point(coord.x, coord.y))
                .toArray(Point[]::new);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", getName())
                .append("address", getAddress())
                .append("district", getDistrict())
                .toString();
    }

    private class ShapefileShape implements Shape {

        Point[] points;

        ShapefileShape(Point[] points) {this.points = points;}

        @Override
        public Point[] getPoints() {return points;}

        @Override
        public double[] getTraveledDistances() {return null;}
    }
}
