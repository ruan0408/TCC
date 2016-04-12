package com.smartsampa.shapefileapi;

import com.smartsampa.utils.APIConnectionException;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.UTMRef;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ruan0408 on 8/04/2016.
 */
public class ShapefileHandler {

    private static final char latitudeZoneSP = 'K';
    private static final int longitudeZoneSP = 23;

    public static List<SimpleFeature> handleShapefile(String shapefilePath) {
        try {
             return getFeatureList(shapefilePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new APIConnectionException("There was a problem when reading the " +
                    "shapefile in the path: "+shapefilePath);
        }
    }

    private static List<SimpleFeature> getFeatureList(String shapefilePath) throws IOException {
        List<SimpleFeature> features;
        File file = new File(shapefilePath);
        Map<String, Object> map = new HashMap<>();
        map.put("url", file.toURI().toURL());

        DataStore dataStore = DataStoreFinder.getDataStore(map);
        String typeName = dataStore.getTypeNames()[0];

        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);
        Filter filter = Filter.INCLUDE; // ECQL.toFilter("BBOX(THE_GEOM, 10,20,30,40)")

        FeatureIterator<SimpleFeature> iterator = source.getFeatures(filter).features();
        features = buildListFromIterator(iterator);

        iterator.close();
        dataStore.dispose();
        return features;
    }

    private static List<SimpleFeature> buildListFromIterator(FeatureIterator<SimpleFeature> iterator) {
        List<SimpleFeature> list = new ArrayList<>();
        while (iterator.hasNext()) {
            SimpleFeature feature = iterator.next();
            fromUTMToLatLgn(feature);
            list.add(feature);
        }
        return list;
    }

    private static void fromUTMToLatLgn(SimpleFeature feature) {
        Coordinate[] coordinates =
                ((Geometry) feature.getDefaultGeometry()).getCoordinates();

        for (Coordinate coordinate : coordinates) {
            UTMRef utm = new UTMRef(coordinate.x, coordinate.y, latitudeZoneSP, longitudeZoneSP);
            LatLng latLng = utm.toLatLng();
            coordinate.x = latLng.getLat();
            coordinate.y = latLng.getLng();
        }
    }
}
