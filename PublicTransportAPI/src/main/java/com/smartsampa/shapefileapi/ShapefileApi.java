package com.smartsampa.shapefileapi;

import com.smartsampa.utils.APIConnectionException;
import com.smartsampa.utils.SmartSampaDir;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.MultiLineString;
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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 22/03/2016.
 */
public class ShapefileAPI {

    private static final char latitudeZoneSP = 'K';
    private static final int longitudeZoneSP = 23;

    private String shapefilePath;
    List<SimpleFeature> features;

    public ShapefileAPI(String relativePath) {
        shapefilePath = SmartSampaDir.getPath()+"/"+relativePath;
    }

    public void init() {
        try {
            File file = new File(shapefilePath);
            Map<String, Object> map = new HashMap<>();
            map.put("url", file.toURI().toURL());

            DataStore dataStore = DataStoreFinder.getDataStore(map);
            String typeName = dataStore.getTypeNames()[0];

            FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);
            Filter filter = Filter.INCLUDE; // ECQL.toFilter("BBOX(THE_GEOM, 10,20,30,40)")

            FeatureIterator<SimpleFeature> iterator = source.getFeatures(filter).features();
            this.features = buildListFromIterator(iterator);

            iterator.close();
            dataStore.dispose();
        } catch (Exception e) {
            throw new APIConnectionException("There was a problem when reading the " +
                    "shapefile in the path: "+shapefilePath);
        }
    }

    private List<SimpleFeature>
    buildListFromIterator(FeatureIterator<SimpleFeature> iterator) {
        List<SimpleFeature> list = new ArrayList<>();
        while (iterator.hasNext()) {
            SimpleFeature feature = iterator.next();
            fromUTMToLatLgn(feature);
            list.add(feature);
        }
        return list;
    }

    private void fromUTMToLatLgn(SimpleFeature feature) {
        Coordinate[] coordinates =
                ((MultiLineString) feature.getDefaultGeometry()).getCoordinates();

        for (Coordinate coordinate : coordinates) {
            UTMRef utm = new UTMRef(coordinate.x, coordinate.y, latitudeZoneSP, longitudeZoneSP);
            LatLng latLng = utm.toLatLng();
            coordinate.x = latLng.getLat();
            coordinate.y = latLng.getLng();
        }
    }

    public Map<String, List<SimpleFeature>> groupBy(Function<SimpleFeature, String> function) {
         return features.stream().collect(Collectors.groupingBy(function));
    }

    protected void printProperties() {
        for (SimpleFeature feature : features)
            System.out.println(feature.getProperties());
    }

    protected void printPropertiesToFile() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("/Users/ruan0408/Downloads/output.txt", "UTF-8");
        for (SimpleFeature feature : features)
            writer.println(feature.getProperties());

        writer.close();
    }
}
