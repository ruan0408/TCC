package com.intellij.publictransportapi;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by ruan0408 on 21/02/2016.
 */
public class StopTest {

    @BeforeClass
    public static void setUp() throws Exception {
        PublicTransportAPITest.setUp();
    }

    @Test
    public void testGetAllTrips() throws Exception {
        List<Trip> allTrips = PublicTransportAPITest.campanella.getAllTrips();
        Assert.assertEquals(9, allTrips.size());
    }

    @Test
    public void testGetPredictedBuses() throws Exception {
        List<PredictedBus> prediction =
                PublicTransportAPITest.usp.getPredictedBuses(PublicTransportAPITest.brasiliana);
        Assert.assertTrue(!prediction.isEmpty());
    }

    @Test
    public void testGetAllPredictions() throws Exception {
        Map<Trip, List<PredictedBus>> predictions =
                PublicTransportAPITest.campanella.getAllPredictions();

        Assert.assertTrue(!predictions.isEmpty());
    }
}