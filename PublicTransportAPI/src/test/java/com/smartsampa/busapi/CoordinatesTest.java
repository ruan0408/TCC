package com.smartsampa.busapi;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import uk.me.jstott.jcoord.LatLng;
import uk.me.jstott.jcoord.UTMRef;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruan0408 on 21/03/2016.
 */
public class CoordinatesTest {


    @Test
    public void testBla2() throws Exception {

        File file = new File("/Users/ruan0408/Downloads/faixa_onibus/sirgas_faixa_onibus.shp");
        Map<String, Object> map = new HashMap<>();
        map.put("url", file.toURI().toURL());

        DataStore dataStore = DataStoreFinder.getDataStore(map);
        String typeName = dataStore.getTypeNames()[0];

        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);
        Filter filter = Filter.INCLUDE; // ECQL.toFilter("BBOX(THE_GEOM, 10,20,30,40)")

        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
        FeatureIterator<SimpleFeature> features = collection.features();

        while (features.hasNext()) {
            SimpleFeature feature = features.next();
            System.out.print(feature.getID());
            System.out.print(": ");
            System.out.println(feature.getDefaultGeometryProperty().getValue());
        }
        features.close();
    }

    @Test
    public void testConvertUTMToWGS84() throws Exception {
        UTMRef utm1 = new UTMRef(323150.35664478, 7395228.33411042, 'K', 23);
//        System.out.println("UTM Reference: " + utm1.toString());
        LatLng ll3 = utm1.toLatLng();
        System.out.println("Converted to Lat/Long: " + ll3.toString());
//        System.out.println();
    }
}
