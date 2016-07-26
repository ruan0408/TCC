package com.smartsampa.busapi;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.smartsampa.busapi.BusAPITestUtils.isAfter4amAndBeforeMidnight;
import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ruan0408 on 16/07/2016.
 */
public class RealTimeAPITest {

    private static RealTimeAPI realTimeAPI;

    @BeforeClass
    public static void setUp() {
        BusAPITestUtils.setUpDataSources();
        realTimeAPI = new RealTimeAPI();
    }

    @Test
    public void getPredictionsPerTrip() throws Exception {
        assumeTrue(isAfter4amAndBeforeMidnight());

        Stop vitalBrasil = mock(Stop.class);
        when(vitalBrasil.getId()).thenReturn(1211364);

        Map<Trip, List<PredictedBus>> predictions = realTimeAPI.getPredictionsPerTrip(vitalBrasil);

        assumeNotNull(predictions);
        assertTrue(predictions.size() > 0);
        assertTrue(predictions.values().stream().anyMatch(buses -> !buses.isEmpty()));
    }

    @Test
    public void getPredictionsPerStopOfAlvim() throws Exception {
        assumeTrue(isAfter4amAndBeforeMidnight());

        Trip alvim = mock(Trip.class);
        when(alvim.getOlhovivoId()).thenReturn(224);

        Map<Stop, List<PredictedBus>> predictions = realTimeAPI.getPredictionsPerStop(alvim);

        assumeNotNull(predictions);
        assertTrue(predictions.size() > 0);
        assertTrue(predictions.values().stream().anyMatch(buses -> !buses.isEmpty()));
    }

    @Test
    public void getPredictionsPerStopOfBonifacio() throws Exception {
        assumeTrue(isAfter4amAndBeforeMidnight());

        Trip bonifacio = mock(Trip.class);
        when(bonifacio.getOlhovivoId()).thenReturn(32992);

        Map<Stop, List<PredictedBus>> predictions = realTimeAPI.getPredictionsPerStop(bonifacio);

        assumeNotNull(predictions);
        assertTrue(predictions.size() > 0);
        assertTrue(predictions.values().stream().anyMatch(buses -> !buses.isEmpty()));
    }

    @Test
    public void getPredictions() throws Exception {
        assumeTrue(isAfter4amAndBeforeMidnight());

        Trip alvimPerua = mock(Trip.class);
        Stop campanella = mock(Stop.class);
        when(alvimPerua.getOlhovivoId()).thenReturn(33715);
        when(campanella.getId()).thenReturn(360004869);

        List<PredictedBus> buses = realTimeAPI.getPredictions(alvimPerua, campanella);

        assertFalse(buses.isEmpty());
    }

    @Test
    public void getAllRunningBuses() throws Exception {
        Trip alvimPerua = mock(Trip.class);
        when(alvimPerua.getOlhovivoId()).thenReturn(33715);

        Set<Bus> buses = realTimeAPI.getAllRunningBuses(alvimPerua);
        assertFalse(buses.isEmpty());

        assertTrue(buses.size() > 0);
    }

}