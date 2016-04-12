package com.smartsampa.busapi;

import com.smartsampa.shapefileapi.ShapefileAPI;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.opengis.feature.simple.SimpleFeature;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 22/03/2016.
 */
public class BusLaneFacade {

    private static final String PATH_SHAPEFILE_BUSLANE = "faixa_onibus/sirgas_faixa_onibus.shp";
    private static final ShapefileAPI shapefileApi = new ShapefileAPI(PATH_SHAPEFILE_BUSLANE);

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
        buslane.setImplantationDate(feature.getAttribute("dt_implant").toString());
        buslane.setStartWorkingTime(feature.getAttribute("ho_inicio_").toString());
        buslane.setEndWorkingTime(feature.getAttribute("ho_fim_fun").toString());
        buslane.setStreetSide(feature.getAttribute("nm_tipo_fa").toString());

        buslane.setAmoutWorkingHours(Integer.parseInt(feature.getAttribute("qt_ho_func").toString()));
        buslane.setRegionCode(Integer.parseInt(feature.getAttribute("cd_regiao_").toString()));
        buslane.setSizeInMeters(Double.parseDouble(feature.getAttribute("qt_metro_f").toString()));

        List<Point> points = getPointsFromFeatures(featureList);
        buslane.setShape(new Shape(points));
        return buslane;
    }

    private static List<Point> getPointsFromFeatures(List<SimpleFeature> featureList) {
        LinkedHashSet<Point> points = new LinkedHashSet<>();
        featureList.stream()
                .map(feat -> {
                    Coordinate[] coordinates = ((Geometry) feat.getDefaultGeometry()).getCoordinates();
                    return Arrays.asList(coordinates)
                            .stream()
                            .map(coord -> new Point(coord.x, coord.y))
                            .collect(Collectors.toList());
                })
                .forEachOrdered(list -> points.addAll(list));
        return new ArrayList<>(points);
    }
}
