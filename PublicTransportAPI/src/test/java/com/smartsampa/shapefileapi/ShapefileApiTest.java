package com.smartsampa.shapefileapi;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by ruan0408 on 22/03/2016.
 */
public class ShapefileAPITest {

    private ShapefileAPI shapefileApi;

    @Ignore
    @Test
    public void testPrintBusLanesProperties() throws Exception {
        shapefileApi = new ShapefileAPI("faixa_onibus/sirgas_faixa_onibus.shp");
        shapefileApi.printProperties();
    }

    @Ignore
    @Test
    public void testPrintPluviometerProperties() throws Exception {
        shapefileApi = new ShapefileAPI("pluviometro/sirgas_pluviometro.shp");
        shapefileApi.printProperties();
    }

    @Ignore
    @Test
    public void testPrintPropertiesToFile() throws Exception {
        shapefileApi.printPropertiesToFile();
    }
}