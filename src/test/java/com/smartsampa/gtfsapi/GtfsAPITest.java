package com.smartsampa.gtfsapi;

import org.junit.Test;

/**
 * Created by ruan0408 on 27/02/2016.
 */
public class GtfsAPITest {

    private static GtfsAPI gtfsAPI;

    @Test
    public void testGetInstance() throws Exception {
        GtfsDownloader gtfsDownloader = new GtfsDownloader("ruan0408", "costaruan");
        GtfsHandler gtfsHandler = new GtfsHandler(gtfsDownloader);
        gtfsAPI = new GtfsAPI(gtfsHandler.getGtfsDao());
    }
}