package com.smartsampa.busapi;


import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.smartsampa.busapi.BusAPITestUtils.isAfter4amAndBeforeMidnight;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ruan0408 on 18/02/2016.
 */
public class TripTest {

    private static Trip alvim, bonifacio, usp, butanta;

    @BeforeClass
    public static void setUp() throws Exception {
        BusAPITestUtils.setUpDataSources();
        alvim = Trip.getTrip("273L-10", "mtst");
        bonifacio = Trip.getTrip("273L-10", "stmt");
        usp = Trip.getTrip("8012-10", "mtst");
        butanta = Trip.getTrip("8012-10", "stmt");
    }

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
    //can go wrong after 1am.
    @Test
    public void testGetDepartureIntervalNow() throws Exception {
        int departureInterval = bonifacio.getDepartureIntervalNow();
        Assert.assertTrue(departureInterval > 0);
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
        Assert.assertNotNull(shape.getPoints());
        Assert.assertNotNull(shape.getTraveledDistances());
    }

    @Test
    public void testGetAllStops() throws Exception {
        List<Stop> allStops = butanta.getAllStops();
        Assert.assertEquals(32, allStops.size());
    }

    @Test
    public void testGetAllRunningBuses() throws Exception {
        Assume.assumeTrue(isAfter4amAndBeforeMidnight());
        List<Bus> allBuses = alvim.getAllRunningBuses();
        Assert.assertTrue(allBuses.size() > 0);
    }

    @Test
    public void testGetPredictedBusesAtStop() throws Exception {
        Assume.assumeTrue(isAfter4amAndBeforeMidnight());

        int campanellaStopId = 360004869;
        Stop campanella = mock(Stop.class);
        when(campanella.getId()).thenReturn(campanellaStopId);

        List<PredictedBus> prediction = alvim.getPredictionsAtStop(campanella);
        Assert.assertTrue(!prediction.isEmpty());
    }

    @Test
    public void testGetAllPredictions() throws Exception {
        Assume.assumeTrue(isAfter4amAndBeforeMidnight());

        Map<Stop, List<PredictedBus>> allPredictions = alvim.getAllPredictions();
        Assert.assertTrue(!allPredictions.isEmpty());
    }

    @Test
    public void testGetFarePrice() throws Exception {
        double farePrice = bonifacio.getFarePrice();
        Assert.assertEquals(3.80, farePrice, 0.000001);
    }
}