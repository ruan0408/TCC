package com.intellij.busapi;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by ruan0408 on 21/02/2016.
 */
public class StopTest extends BusAPITest {

    private Stop campanella = new Stop(360004869,
            "Av. Dos Campanellas", new Point(-23.536056,-46.467776));

    private Stop brasiliana = new Stop(120010354,
            "Biblioteca Brasiliana", new Point(-23.562368,-46.723045));

    @Test
    public void testGetAllTrips() throws Exception {
        List<Trip> allTrips = campanella.getAllTrips();
        Assert.assertEquals(9, allTrips.size());
    }

    @Test
    public void testGetPredictedBusesAtStop() throws Exception {
        Trip usp = mock(Trip.class);
        when(usp.getOlhovivoTripId()).thenReturn(2023);
        List<PredictedBus> predictedBuses = brasiliana.getPredictedBusesOfTrip(usp);
        Assert.assertTrue(!predictedBuses.isEmpty());
    }

    @Test
    public void testGetAllPredictions() throws Exception {
        Map<Trip, List<PredictedBus>> predictions = campanella.getAllPredictions();
        Assert.assertTrue(!predictions.isEmpty());
    }
}