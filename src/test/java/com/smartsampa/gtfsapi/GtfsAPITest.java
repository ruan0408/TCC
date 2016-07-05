package com.smartsampa.gtfsapi;

import org.junit.Test;

/**
 * Created by ruan0408 on 27/02/2016.
 */
public class GtfsAPITest {

    @Test
    public void testGetInstance() throws Exception {
        GtfsDownloader gtfsDownloader = new GtfsDownloader("ruan0408", "costaruan");
        GtfsHandler gtfsHandler = new GtfsHandler(gtfsDownloader);
        GtfsAPI gtfsAPI = new GtfsAPI(gtfsHandler);
    }
}