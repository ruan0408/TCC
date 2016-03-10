package com.intellij.gtfsapi;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by ruan0408 on 27/02/2016.
 */
public class GTFSApiTest {

    private static GTFSApi gtfsApi;

    @BeforeClass
    public static void setUp() throws Exception {
        gtfsApi = new GTFSApi("ruan0408", "costaruan");
    }

    @Test
    public void testInit() throws Exception {
        gtfsApi.init();
    }
}