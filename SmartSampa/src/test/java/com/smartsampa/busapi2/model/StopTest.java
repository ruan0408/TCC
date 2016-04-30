package com.smartsampa.busapi2.model;

import com.smartsampa.utils.Point;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.smartsampa.busapi2.model.BusAPITestUtils.isAfter4amAndBeforeMidnight;
import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ruan0408 on 16/04/2016.
 */
public class StopTest {

    @BeforeClass
    public static void setUp() throws Exception {
        BusAPITestUtils.setUpDataSources();
    }

    @Test
    public void testSearchStopsByTerm() throws Exception {
        Set<AbstractStop> stops = AbstractStop.searchStopsByTerm("campanella");
        assertFalse(stops.isEmpty());
    }

    @Test
    public void testGetTrips() throws Exception {
        Stop campanella = AbstractStop.getStopById(360004869);
        Set<Trip> trips = campanella.getTrips();

        assertFalse(trips.isEmpty());
        assertTrue(trips.stream().anyMatch(t -> t.getNumberSign().equals("2732-10")));
    }

    @Test
    public void testGetAllPredictions() throws Exception {
        assumeTrue(isAfter4amAndBeforeMidnight());

        Stop vitalBrasil = AbstractStop.getStopById(1211364);
        Map<Trip, List<PredictedBus>> predictions = vitalBrasil.getAllPredictions();

        assumeNotNull(predictions);
        assertTrue(predictions.size() > 0);
        assertTrue(predictions.values().stream().anyMatch(buses -> !buses.isEmpty()));
    }

    @Test
    public void testGetPredictedBusesOfTrip() throws Exception {
        assumeTrue(isAfter4amAndBeforeMidnight());

        Stop campanella = AbstractStop.getStopById(1211364);
        AbstractTrip alvimPerua = mock(AbstractTrip.class);
        when(alvimPerua.getOlhovivoId()).thenReturn(33715);
        List<PredictedBus> predictions = campanella.getPredictedBusesOfTrip(alvimPerua);

        assumeNotNull(predictions);
        assertFalse(predictions.isEmpty());
    }

    @Test
    public void testGetId() throws Exception {
        Stop fea = AbstractStop.getStopById(120010356);
        assertEquals(120010356, fea.getId().intValue());
    }

    @Test
    public void testGetName() throws Exception {
        Stop fea = AbstractStop.getStopById(120010356);
        assertEquals("Fea", fea.getName());
    }

    @Test
    public void testGetAddress() throws Exception {
        Stop fea = AbstractStop.getStopById(120010356);
        assertNull(fea.getAddress());
    }

    @Test
    public void testGetReference() throws Exception {
        Stop fea = AbstractStop.getStopById(120010356);
        assertEquals("Av. Prof. Luciano Gualberto Ref.: R V/ Av Professor Lucio Martins Rodrigues",
                fea.getReference());
    }

    @Test
    public void testGetLocation() throws Exception {
        Stop fea = AbstractStop.getStopById(120010356);
        assertEquals(new Point(-23.558951,-46.729803), fea.getLocation());
    }
}