package com.smartsampa.busapi;

import com.smartsampa.shapefileapi.ShapefileApi;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.MultiLineString;
import org.jetbrains.annotations.NotNull;
import org.opengis.feature.simple.SimpleFeature;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 22/03/2016.
 */
public class DataToBusLaneFacade {

    private static String SHAPEFILE_RELATIVE_PATH;
    private static ShapefileApi shapefileApi;

    static {
        SHAPEFILE_RELATIVE_PATH = "faixa_onibus/sirgas_faixa_onibus.shp";
        shapefileApi = new ShapefileApi(SHAPEFILE_RELATIVE_PATH);
        shapefileApi.init();
    }

    public static List<BusLane> getAllBusLanes() {
        Map<String, List<SimpleFeature>> groupedByName =
                shapefileApi.groupBy(feature -> feature.getAttribute("nm_denomin").toString());

        List<BusLane> busLanes = new ArrayList<>();
        for (List<SimpleFeature> featureList : groupedByName.values()) {
            BusLane busLane = buildFromFeatures(featureList);
            if (busLane == null)
                continue;

            busLanes.add(busLane);
        }
        return busLanes;
    }

    private static BusLane buildFromFeatures(List<SimpleFeature> featureList) {
        SimpleFeature feature = featureList.get(0);
        if (feature == null) return null;
        BusLane buslane = new BusLane();

        buslane.setName(feature.getAttribute("nm_denomin").toString());
        buslane.setAddress(feature.getAttribute("nm_logrado").toString());
        buslane.setHeading(feature.getAttribute("tx_sentido").toString());
        buslane.setAddressStart(feature.getAttribute("tx_inicio_").toString());
        buslane.setAddressEnd(feature.getAttribute("tx_fim_log").toString());
        buslane.setDistrict(feature.getAttribute("tx_distrit").toString());

        buslane.setSizeInMeters(Double.parseDouble(feature.getAttribute("qt_metro_f").toString()));

        List<Point> points = getPointsFromFeatures(featureList);
        buslane.setShape(new Shape(points));
        return buslane;
    }

    @NotNull
    private static List<Point> getPointsFromFeatures(List<SimpleFeature> featureList) {
        LinkedHashSet<Point> points = new LinkedHashSet<>();
        featureList.stream()
                .map(feat -> {
                    Coordinate[] coordinates = ((MultiLineString) feat.getDefaultGeometry()).getCoordinates();
                    return Arrays.asList(coordinates)
                            .stream()
                            .map(coord -> new Point(coord.x, coord.y))
                            .collect(Collectors.toList());
                })
                .forEachOrdered(list -> points.addAll(list));
        return new ArrayList<>(points);
    }
}
