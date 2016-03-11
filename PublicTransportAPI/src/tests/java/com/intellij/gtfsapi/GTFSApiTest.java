package com.intellij.gtfsapi;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by ruan0408 on 27/02/2016.
 */
public class GtfsAPITest {

    private static GtfsAPI gtfsAPI;

    @BeforeClass
    public static void setUp() throws Exception {
        gtfsAPI = new GtfsAPI("ruan0408", "costaruan");
    }

    @Test
    public void testInit() throws Exception {
        gtfsAPI.init();
    }
}