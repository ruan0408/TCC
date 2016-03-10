package com.intellij.publictransportapi;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by ruan0408 on 21/02/2016.
 */
public class RouteTest {

    @BeforeClass
    public static void setUp() throws Exception {
        PublicTransportAPITest.setUp();
    }

    @Test
    public void testGetFarePrice() throws Exception {
        double price = PublicTransportAPITest.route8012.getFarePrice();
        Assert.assertEquals(3.80, price, 0.00001);
    }
}