package com.intellij.busapi;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ruan0408 on 21/02/2016.
 */
public class RouteTest extends BusAPITest {

    private Route route273l = new Route("273L", 10, false, null);
    private Route route8012 = new Route("8012", 10, true, null);

    @Test
    public void testGetFarePrice() throws Exception {
        double price = route8012.getFarePrice();
        Assert.assertEquals(3.80, price, 0.00001);

        price = route273l.getFarePrice();
        Assert.assertEquals(3.80, price, 0.00001);
    }
}