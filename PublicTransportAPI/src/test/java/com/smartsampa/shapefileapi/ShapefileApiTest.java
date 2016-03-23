package com.smartsampa.shapefileapi;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by ruan0408 on 22/03/2016.
 */
public class ShapefileAPITest {

    private static final ShapefileAPI shapefileApi;
    static {
        shapefileApi = new ShapefileAPI("faixa_onibus/sirgas_faixa_onibus.shp");
        shapefileApi.init();
    }

    @Test
    public void testInit() throws Exception {
        shapefileApi.init();
    }

    @Ignore
    @Test
    public void testPrintProperties() throws Exception {
        shapefileApi.printProperties();
    }

    @Ignore
    @Test
    public void testPrintPropertiesToFile() throws Exception {
        shapefileApi.printPropertiesToFile();
    }

    @Test
    public void testGroupBy() throws Exception {

    }
}