package com.intellij.busapi;


import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ruan0408 on 18/02/2016.
 */
public class TripTest extends BusAPITest {

    private Trip alvim = Trip.getTrip("273L-10", "stmt");
    private Trip bonifacio = Trip.getTrip("273L-10", "mtst");
    private Trip usp = Trip.getTrip("8012-10", "mtst");
    private Trip butanta = Trip.getTrip("8012-10", "stmt");

    @Test
    public void testGetTrip() throws Exception {
        Trip alvim = Trip.getTrip("273L-10", "mtst");
        Assert.assertEquals("METRÔ ARTUR ALVIM", alvim.getDestinationSign());
        Assert.assertEquals(224, alvim.getOlhovivoTripId());
    }

    @Test
    public void testSearchTripByTerm() throws Exception {
        List<Trip> trips = Trip.searchTripByTerm("273L-10");
        Assert.assertTrue(trips.size() == 2);
        Assert.assertTrue(trips.contains(alvim));
        Assert.assertTrue(trips.contains(bonifacio));

        trips = Trip.searchTripByTerm("8012-10");
        Assert.assertTrue(trips.size() == 2);
        Assert.assertTrue(trips.contains(usp));
        Assert.assertTrue(trips.contains(butanta));
    }

    @Test
    public void testGetDepartureIntervalAtTime() throws Exception {
        int departureInterval = alvim.getDepartureIntervalAtTime("19:04");
        Assert.assertEquals(720, departureInterval);

        departureInterval = bonifacio.getDepartureIntervalAtTime("09:00");
        Assert.assertEquals(720, departureInterval);
    }

    //Depends on the current time.
    @Test
    public void testGetDepartureIntervalNow() throws Exception {
        int departureInterval = bonifacio.getDepartureIntervalNow();
        Assert.assertNotEquals(-1, departureInterval);
    }

    @Test
    public void testGetWorkingDays() throws Exception {
        String workingDays = usp.getWorkingDays();
        Assert.assertEquals("USD", workingDays);
    }

    @Test
    public void testGetShape() throws Exception {
        Shape shape = usp.getShape();
        Assert.assertEquals(417, shape.size());
    }

    @Test
    public void testGetAllStops() throws Exception {
        List<Stop> allStops = butanta.getAllStops();
        Assert.assertEquals(32, allStops.size());
    }

//    Depends on the current number of buses running...
    @Test
    public void testGetAllRunningBuses() throws Exception {
        List<Bus> allBuses = alvim.getAllRunningBuses();
        Assert.assertTrue(allBuses.size() > 0);
    }

    //dont know how to test this.
    @Test
    public void testGetPredictedBuses() throws Exception {
        Stop campanella = mock(Stop.class);
        when(campanella.getId()).thenReturn(360004869);

        List<PredictedBus> prediction = alvim.getPredictionsAtStop(campanella);
        Assert.assertTrue(!prediction.isEmpty());
    }

    // dont know how to test this also.
    @Test
    public void testGetAllPredictions() throws Exception {
        Map<Stop, List<PredictedBus>> allPredictions = alvim.getAllPredictions();
        Assert.assertTrue(!allPredictions.isEmpty());
    }
}