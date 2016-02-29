package com.intellij.gtfsapi;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by ruan0408 on 27/02/2016.
 */
public class GTFSApiTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetInstance() throws Exception {
        GTFSApi.getInstance("ruan0408", "costaruan");

    }
}