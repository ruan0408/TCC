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
        APITest.setUp();
    }

    @Test
    public void testGetDepartureInterval() throws Exception {
        int departureInterval = APITest.alvim.getDepartureInterval("19:04");
        Assert.assertEquals(720, departureInterval);

        departureInterval = APITest.bonifacio.getDepartureInterval("09:00");
        Assert.assertEquals(720, departureInterval);
    }

    //Depends on the current time.
    @Test
    public void testGetDepartureIntervalNow() throws Exception {
        int departureInterval = APITest.bonifacio.getDepartureIntervalNow();
        Assert.assertEquals(720, departureInterval);
    }

    @Test
    public void testGetWorkingDays() throws Exception {
        String workingDays = APITest.usp.getWorkingDays();
        Assert.assertEquals("USD", workingDays);
    }

    @Test
    public void testGetDetails() throws Exception {
        String ans = APITest.usp.getDetails();
        Assert.assertEquals("[]", ans);
    }

    @Test
    public void testGetShape() throws Exception {
        Shape shape = APITest.usp.getShape();
        Assert.assertEquals(417, shape.size());
    }

    @Test
    public void testGetAllStops() throws Exception {
        List<Stop> allStops = APITest.butanta.getAllStops();
        Assert.assertEquals(32, allStops.size());
    }

    //Depends on the current number of buses running...
    @Test
    public void testGetAllBuses() throws Exception {
        List<Bus> allBuses = APITest.alvim.getAllBuses();
        Assert.assertEquals(3, allBuses.size());
    }

    //TODO dont know how to test this.
    @Test
    public void testGetPredictedBuses() throws Exception {
        List<PredictedBus> prediction = APITest.alvim.getPredictedBuses(APITest.campanella);
        Assert.assertTrue(!prediction.isEmpty());
    }

    //TODO dont know how to test this also.
    @Test
    public void testGetAllPredictions() throws Exception {
        Map<Stop, List<PredictedBus>> allPredictions = APITest.alvim.getAllPredictions();
        Assert.assertTrue(!allPredictions.isEmpty());
    }
}