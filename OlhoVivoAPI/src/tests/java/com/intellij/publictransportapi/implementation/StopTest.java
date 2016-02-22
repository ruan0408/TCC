package com.intellij.publictransportapi.implementation;

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
        APITest.setUp();
    }

    @Test
    public void testGetAllTrips() throws Exception {
        List<Trip> allTrips = APITest.campanella.getAllTrips();
        Assert.assertEquals(9, allTrips.size());
    }

    //might throw null pointer exceptions...
    @Test
    public void testGetPredictedBuses() throws Exception {
        List<PredictedBus> prediction =
                APITest.usp.getPredictedBuses(APITest.brasiliana);
        Assert.assertTrue(!prediction.isEmpty());
    }

    @Test
    public void testGetAllPredictions() throws Exception {
        Map<Trip, List<PredictedBus>> predictions =
                APITest.campanella.getAllPredictions();

        Assert.assertTrue(!predictions.isEmpty());
    }
}