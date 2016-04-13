package com.smartsampa.risk;

import com.smartsampa.utils.Point;
import com.smartsampa.shapefileapi.ShapefileAPI;
import com.vividsolutions.jts.geom.Coordinate;
import org.opengis.feature.simple.SimpleFeature;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 8/04/2016.
 */
public class PluviometerFacade {

    private static final String PATH_SHAPEFILE_PLUVIOMETER = "pluviometro/sirgas_pluviometro.shp";
    private static final ShapefileAPI shapefileApi = new ShapefileAPI(PATH_SHAPEFILE_PLUVIOMETER);


    public static List<Pluviometer> getAllPluviometers() {
        List<SimpleFeature> allFeatures = shapefileApi.getAllFeatures();

        List<Pluviometer> pluviometers =
                transformAllFeaturesToPluviometers(allFeatures);

        return pluviometers;
    }

    private static List<Pluviometer> transformAllFeaturesToPluviometers(List<SimpleFeature> allFeatures) {
        return allFeatures.parallelStream()
                .map(feature -> buildFromFeature(feature))
                .collect(Collectors.toList());
    }

    private static Pluviometer buildFromFeature(SimpleFeature feature) {
        Pluviometer pluviometer = new Pluviometer();

        pluviometer.setAddress(feature.getAttribute("tx_enderec").toString());
        pluviometer.setLocality(feature.getAttribute("nm_pluviom").toString());

        Coordinate coord = getCoordinateFromFeature(feature);
        pluviometer.setLocation(new Point(coord.x, coord.y));

        return pluviometer;
    }

    private static Coordinate getCoordinateFromFeature(SimpleFeature feature) {
        return ((com.vividsolutions.jts.geom.Geometry) feature.getDefaultGeometry()).getCoordinate();
    }

}
