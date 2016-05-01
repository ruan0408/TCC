package com.smartsampa.shapefileapi;

import com.smartsampa.utils.SmartSampaDir;
import org.opengis.feature.simple.SimpleFeature;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 22/03/2016.
 */
public class ShapefileAPI {

    private List<SimpleFeature> features;

    public ShapefileAPI(String relativePath) {
        String shapefilePath = SmartSampaDir.getPath()+"/"+relativePath;
        features = ShapefileHandler.handleShapefile(shapefilePath);
    }

    public List<SimpleFeature> getAllFeatures() {return features;}

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
