package com.smartsampa.busapi;


import com.smartsampa.busapi2.model.BusAPITestUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.smartsampa.busapi2.model.BusAPITestUtils.isAfter4amAndBeforeMidnight;
import static org.junit.Assert.*;
import static org.junit.Assume.*;
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
        assertEquals("METRÔ ARTUR ALVIM", alvim.getDestinationSign());
        assertEquals(224, alvim.getOlhovivoTripId());
    }

    @Test
    public void testSearchTripByTerm() throws Exception {
        List<Trip> trips = Trip.searchTripByTerm("273L-10");
        assertTrue(trips.size() == 2);
        assertTrue(trips.contains(alvim));
        assertTrue(trips.contains(bonifacio));

        trips = Trip.searchTripByTerm("8012-10");
        assertTrue(trips.size() == 2);
        assertTrue(trips.contains(usp));
        assertTrue(trips.contains(butanta));
    }

    @Test
    public void testGetDepartureIntervalAtTime() throws Exception {
        int departureInterval = alvim.getDepartureIntervalAtTime("19:04");
        assertEquals(720, departureInterval);

        departureInterval = bonifacio.getDepartureIntervalAtTime("09:00");
        assertEquals(720, departureInterval);
    }

    //Depends on the current time.
    //can go wrong after 1am.
    @Test
    public void testGetDepartureIntervalNow() throws Exception {
        int departureInterval = bonifacio.getDepartureIntervalNow();
        assertTrue(departureInterval > 0);
    }

    @Test
    public void testGetWorkingDays() throws Exception {
        String workingDays = usp.getWorkingDays();
        assertEquals("USD", workingDays);
    }

    @Test
    public void testGetShape() throws Exception {
        Shape shape = usp.getShape();
        assertEquals(419, shape.size());
        assertNotNull(shape.getPoints());
        assertNotNull(shape.getTraveledDistances());
    }

    @Test
    public void testGetAllStops() throws Exception {
        List<Stop> allStops = butanta.getAllStops();
        assertEquals(33, allStops.size());
    }

    @Test
    public void testGetAllRunningBuses() throws Exception {
        assumeTrue(isAfter4amAndBeforeMidnight());
        List<Bus> allBuses = alvim.getAllRunningBuses();
        assertTrue(allBuses.size() > 0);
    }

    @Test
    public void testGetPredictedBusesAtStop() throws Exception {
        assumeTrue(isAfter4amAndBeforeMidnight());

        int campanellaStopId = 360004869;
        Stop campanella = mock(Stop.class);
        when(campanella.getId()).thenReturn(campanellaStopId);

        List<PredictedBus> prediction = alvim.getPredictionsAtStop(campanella);
        assertTrue(!prediction.isEmpty());
    }

    @Test
    public void testGetAllPredictions() throws Exception {
        assumeTrue(isAfter4amAndBeforeMidnight());

        Map<Stop, List<PredictedBus>> allPredictions = alvim.getAllPredictions();
        assertTrue(!allPredictions.isEmpty());
    }

    @Test
    public void testGetFarePrice() throws Exception {
        double farePrice = bonifacio.getFarePrice();
        assertEquals(3.80, farePrice, 0.000001);
    }
}