package com.smartsampa.busapi;

import com.smartsampa.gtfswrapper.GtfsAPIFacade;
import com.smartsampa.olhovivoapi.OlhovivoAPI;
import com.smartsampa.shapefileapi.ShapefileAPI;

/**
 * Created by ruan0408 on 4/06/2016.
 */
public final class Provider {

    private static OlhovivoAPI olhovivoAPI;
    private static GtfsAPIFacade gtfsAPIFacade;
    private static ShapefileAPI shapefileAPI;

    public static OlhovivoAPI getOlhovivoAPI() {
        return olhovivoAPI;
    }

    public static void setOlhovivoAPI(OlhovivoAPI olhovivo) {
        olhovivoAPI = olhovivo;
    }

    public static GtfsAPIFacade getGtfsAPIFacade() {
        return gtfsAPIFacade;
    }

    public static void setGtfsAPIFacade(GtfsAPIFacade gtfs) {
        gtfsAPIFacade = gtfs;
    }

    public static ShapefileAPI getShapefileAPI() { return shapefileAPI; }

    public static void setShapefileAPI(ShapefileAPI shapefile) { shapefileAPI = shapefile; }
}
