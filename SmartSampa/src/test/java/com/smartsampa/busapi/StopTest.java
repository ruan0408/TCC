package com.smartsampa.busapi;

import com.smartsampa.busapi2.model.BusAPITestUtils;
import com.smartsampa.utils.Point;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by ruan0408 on 21/02/2016.
 */
public class StopTest {

    private static Stop campanella, brasiliana;

    @BeforeClass
    public static void setUp() throws Exception {
        BusAPITestUtils.setUpDataSources();
        campanella = new Stop(360004869,
                "Av. Dos Campanellas", new Point(-23.536056,-46.467776));
        brasiliana = new Stop(120010354,
                "Biblioteca Brasiliana", new Point(-23.562368,-46.723045));
    }

    @Test
    public void testSearchStopsByTerm() throws Exception {
        List<Stop> stopList = Stop.searchStopsByTerm("Fea");
        assertTrue(!stopList.isEmpty());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Av. Dos Campanellas", campanella.getName());
    }

    @Test
    public void testGetLocation() throws Exception {
        Point locationBibBrasiliana = new Point(-23.562368,-46.723045);
        assertEquals(locationBibBrasiliana, brasiliana.getLocation());
    }

    @Test
    public void testGetAllTrips() throws Exception {
        List<Trip> allTrips = campanella.getAllTrips();
        assertEquals(9, allTrips.size());
    }

    @Test
    public void testGetPredictedBusesOfTrip() throws Exception {
        Trip alvimPerua = mock(Trip.class);
        int alvimPeruaTripId = 33715;
        when(alvimPerua.getOlhovivoTripId()).thenReturn(alvimPeruaTripId);
        List<PredictedBus> predictedBuses = campanella.getPredictedBusesOfTrip(alvimPerua);

        assertTrue(!predictedBuses.isEmpty());
        Predicate<PredictedBus> arrivalNotNull = bus -> bus.getPredictedArrival() != null;
        assertTrue(predictedBuses.stream().allMatch(arrivalNotNull));
    }

    @Test
    public void testGetAllPredictions() throws Exception {
        Map<Trip, List<PredictedBus>> predictions = campanella.getAllPredictions();
        assertTrue(!predictions.isEmpty());
    }
}