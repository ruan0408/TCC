package com.smartsampa.busapi;

import com.smartsampa.busapi2.model.BusAPITestUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by ruan0408 on 21/02/2016.
 */
public class RouteTest {

    private Route route273l = new Route("273L", 10, false, null);
    private Route route8012 = new Route("8012", 10, true, null);

    @BeforeClass
    public static void setUp() throws Exception {
        BusAPITestUtils.setUpDataSources();
    }

    @Test
    public void testGetFarePrice() throws Exception {
        double price = route8012.getFarePrice();
        Assert.assertEquals(3.80, price, 0.00001);

        price = route273l.getFarePrice();
        Assert.assertEquals(3.80, price, 0.00001);
    }
}