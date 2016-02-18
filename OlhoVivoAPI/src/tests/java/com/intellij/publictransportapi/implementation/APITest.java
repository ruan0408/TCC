package com.intellij.publictransportapi.implementation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Created by ruan0408 on 18/02/2016.
 */
public class APITest {

    public static Trip alvim, bonifacio;
    public static Route route273l;

    public static void loadTestObjects() {
        route273l = new Route("273L", 10, false, null);

        alvim = new Trip();
        alvim.setInternalId(224);
        alvim.setDestinationSign("Metrô Artur Alvim");
        alvim.setRoute(route273l);

        bonifacio = new Trip();
        bonifacio.setInternalId(32992);
        bonifacio.setDestinationSign("Cptm José Bonifácio.");
        bonifacio.setRoute(route273l);

        route273l.setMTST(bonifacio);
        route273l.setSTMT(alvim);

    }

    @BeforeClass
    public static void setUp() throws Exception {
        API.init("3de5ce998806e0c0750b1434e17454b6490ccf0a595f3884795da34460a7e7b3");
        loadTestObjects();
    }

    //TODO write another test with a circular line.
    @Test
    public void testSearchTrip() throws Exception {
        List<Trip> tripList = API.searchTrip("273L");

        Assert.assertTrue(tripList.size() == 2);
        Assert.assertTrue(tripList.contains(alvim));
        Assert.assertTrue(tripList.contains(bonifacio));
    }
}