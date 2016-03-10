package com.intellij.publictransportapi;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by ruan0408 on 18/02/2016.
 */
public class TripTest {

    @BeforeClass
    public static void setUp() throws Exception {
        PublicTransportAPITest.setUp();
    }

    @Test
    public void testGetDepartureInterval() throws Exception {
        int departureInterval = PublicTransportAPITest.alvim.getDepartureInterval("19:04");
        Assert.assertEquals(720, departureInterval);

        departureInterval = PublicTransportAPITest.bonifacio.getDepartureInterval("09:00");
        Assert.assertEquals(720, departureInterval);
    }

    //Depends on the current time.
    @Test
    public void testGetDepartureIntervalNow() throws Exception {
        int departureInterval = PublicTransportAPITest.bonifacio.getDepartureIntervalNow();
        Assert.assertNotEquals(-1, departureInterval);
    }

    @Test
    public void testGetWorkingDays() throws Exception {
        String workingDays = PublicTransportAPITest.usp.getWorkingDays();
        Assert.assertEquals("USD", workingDays);
    }

    @Test
    public void testGetDetails() throws Exception {
        String ans = PublicTransportAPITest.usp.getDetails();
        Assert.assertEquals("[]", ans);
    }

    @Test
    public void testGetShape() throws Exception {
        Shape shape = PublicTransportAPITest.usp.getShape();
        Assert.assertEquals(417, shape.size());
    }

    @Test
    public void testGetAllStops() throws Exception {
        List<Stop> allStops = PublicTransportAPITest.butanta.getAllStops();
        Assert.assertEquals(32, allStops.size());
    }

    //Depends on the current number of buses running...
    @Test
    public void testGetAllBuses() throws Exception {
        List<Bus> allBuses = PublicTransportAPITest.alvim.getAllBuses();
        Assert.assertTrue(allBuses.size() > 0);
    }

    //TODO dont know how to test this.
    @Test
    public void testGetPredictedBuses() throws Exception {
        List<PredictedBus> prediction = PublicTransportAPITest.alvim.getPredictedBuses(PublicTransportAPITest.campanella);
        Assert.assertTrue(!prediction.isEmpty());
    }

    //TODO dont know how to test this also.
    @Test
    public void testGetAllPredictions() throws Exception {
        Map<Stop, List<PredictedBus>> allPredictions = PublicTransportAPITest.alvim.getAllPredictions();
        Assert.assertTrue(!allPredictions.isEmpty());
    }
}