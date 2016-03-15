package com.intellij.gtfsapi;

import org.junit.Test;

/**
 * Created by ruan0408 on 27/02/2016.
 */
public class GtfsAPITest {

    private static GtfsAPI gtfsAPI;

    @Test
    public void testGetInstance() throws Exception {
        GtfsAPI.getInstance();
    }
}