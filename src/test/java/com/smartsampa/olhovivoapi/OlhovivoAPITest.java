package com.smartsampa.olhovivoapi;

import com.smartsampa.busapi.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import static com.smartsampa.busapi.BusAPITestUtils.isAfter4amAndBeforeMidnight;
import static org.junit.Assert.*;
import static org.junit.Assume.*;

/**
 * Created by ruan0408 on 11/02/2016.
 */
public class OlhovivoAPITest {

    private static final String AUTHKEY = "3de5ce998806e0c0750b1434e17454b6490ccf0a595f3884795da34460a7e7b3";
    private static OlhovivoAPI api;

    private final int campanellaStopId = 360004869;
    private final int bibBrasilianaStopId = 120010354;

    private final int alvim = 224;
    private final int bonifacio = 32992;

    private final int alvimPerua = 33715;

    private final int usp8012 = 2023;
    private final int butanta8012 = 34791;

    private final int campoLimpoCorridor = 8;


    @BeforeClass
    public static void setUp() throws Exception {
        api = new OlhovivoAPI(AUTHKEY);
        api.authenticate();
    }

    @Test
    public void testAuthenticate() throws Exception{
        boolean resp = api.authenticate();
        assertTrue(resp);
    }

    @Test
    public void testGetTripsByTerm() throws Exception {
        Set<Trip> trips = api.getTripsByTerm("8000");
        assertTrue(trips.stream().anyMatch(t -> t.getNumberSign().equals("8000-10")));

    }

    @Test
    public void testGetStopsByTerm() throws Exception {
        Set<Stop> stops = api.getStopsByTerm("afonso");
        assertTrue(stops.stream().anyMatch(s -> s.getName().toLowerCase().contains("afonso")));
    }

    @Test
    public void testGetAllRunningBusesOfTrip() throws Exception {
        assumeTrue(isAfter4amAndBeforeMidnight());
        Set<Bus> buses = api.getAllRunningBusesOfTrip(bonifacio);
        assertFalse(buses.isEmpty());
        assertTrue(buses.stream().allMatch(b -> b.getPrefixNumber() != null));
    }

    @Test
    public void testGetPredictionsOfTripAtStop() throws Exception {
        assumeTrue(isAfter4amAndBeforeMidnight());
        List<PredictedBus> predictedBuses = api.getPredictionsOfTripAtStop( alvim, campanellaStopId );
        assertFalse(predictedBuses.isEmpty());
        assertTrue(predictedBuses.stream().allMatch(b -> b.getPrefixNumber() != null));
    }

    @Test
    public void testGetPredictionsOfTrip() throws Exception {
        assumeTrue(isAfter4amAndBeforeMidnight());
        Map<Stop, List<PredictedBus>> predictions = api.getPredictionsOfTrip(bonifacio);
        assertFalse(predictions.isEmpty());
    }


    @Test
    public void testGetPredictionsAtStop() throws Exception {
        assumeTrue(isAfter4amAndBeforeMidnight());
        Map<Trip, List<PredictedBus>> predictions = api.getPredictionsAtStop(campanellaStopId);
        assertFalse(predictions.isEmpty());
    }

    @Test
    public void testGetAllCorridors() throws Exception {
        List<Corridor> corridors = api.getAllCorridors();
        Predicate<Corridor> containsCampoLimpo =
                corridor -> corridor.getName().equalsIgnoreCase("Campo Limpo");

        assertNotNull(corridors);
        assertTrue(corridors.size() > 0);
        assertTrue(corridors.stream().anyMatch(containsCampoLimpo));
    }

    @Test
    public void testGetStopsByCorridor() throws Exception {
        List<Stop> stops = api.getStopsByCorridor(campoLimpoCorridor);
        Predicate<Stop> namesNotNull = stop -> stop.getName() != null;

        assertNotNull(stops);
        assertTrue(stops.size() > 0);
        assertTrue(stops.stream().allMatch(namesNotNull));
    }
}