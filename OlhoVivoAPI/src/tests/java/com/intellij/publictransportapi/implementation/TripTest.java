package com.intellij.publictransportapi.implementation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Created by ruan0408 on 18/02/2016.
 */
public class TripTest {

    @BeforeClass
    public static void setUp() throws Exception {
        API.init("3de5ce998806e0c0750b1434e17454b6490ccf0a595f3884795da34460a7e7b3");
        APITest.loadTestObjects();
    }

    @Test
    public void testGetDepartureInterval() throws Exception {
        int departureInterval = APITest.alvim.getDepartureInterval("19:04");
        Assert.assertEquals(720, departureInterval);

        departureInterval = APITest.bonifacio.getDepartureInterval("09:00");
        Assert.assertEquals(720, departureInterval);
    }

    //TODO this test is dependent on the current time. Mock it.
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
        System.out.println(allStops.size());
        for (Stop s : allStops)
            System.out.println(s.toString());
    }

    @Test
    public void testGetAllBuses() throws Exception {

    }

    @Test
    public void testGetPredictedBuses() throws Exception {

    }

    @Test
    public void testGetAllPredictions() throws Exception {

    }
}