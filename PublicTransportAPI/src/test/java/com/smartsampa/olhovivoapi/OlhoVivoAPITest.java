package com.smartsampa.olhovivoapi;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Predicate;

import static com.smartsampa.busapi.BusAPITestUtils.isAfter4amAndBeforeMidnight;

/**
 * Created by ruan0408 on 11/02/2016.
 */
public class OlhoVivoAPITest {

    private static final String AUTHKEY = "3de5ce998806e0c0750b1434e17454b6490ccf0a595f3884795da34460a7e7b3";
    private static OlhoVivoAPI api;

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
        api = new OlhoVivoAPI(AUTHKEY);
        api.authenticate();
    }

    @Test
    public void testAuthenticate() throws Exception{
        boolean resp = api.authenticate();
        Assert.assertTrue(resp);
    }

    @Test
    public void testSearchBusLines() throws Exception {
        BusLine[] busLines = api.searchBusLines("8000");
        Predicate<BusLine> containsBusLine8000 =
                busLine -> busLine.getNumberSign().equals("8000");

        Assert.assertTrue(arrayHasMatch(busLines, containsBusLine8000));
    }

    @Test
    public void testGetBusLineDetails() throws Exception {
        String response = api.getBusLineDetails(alvimPerua);
        Assert.assertEquals("[]", response);
    }

    @Test
    public void testSearchBusStops() throws Exception {
        BusStop[] busStops = api.searchBusStops("afonso");
        Predicate<BusStop> containsAfonso =
                stop -> stop.getName().toLowerCase().contains("afonso");
        Assert.assertTrue(arrayHasMatch(busStops, containsAfonso));
    }

    @Test
    public void testSearchBusStopsByLine() throws Exception {
        BusStop[] busStops = api.searchBusStopsByLine(usp8012);
        Assume.assumeTrue(busStops.length > 0);

        Predicate<BusStop> namesNotNull = stop -> stop.getName() != null;

        Assert.assertNotNull(busStops);
        Assert.assertTrue(arrayHasMatch(busStops, namesNotNull));
    }

    @Test
    public void testSearchBusStopsByCorridor() throws Exception {
        BusStop[] busStops = api.searchBusStopsByCorridor(campoLimpoCorridor);
        Predicate<BusStop> namesNotNull = stop -> stop.getName() != null;

        Assert.assertNotNull(busStops);
        Assert.assertTrue(busStops.length > 0);
        Assert.assertTrue(arrayHasMatch(busStops, namesNotNull));
    }

    @Test
    public void testGetAllBusCorridors() throws Exception {
        BusCorridor[] corridors = api.getAllBusCorridors();
        Predicate<BusCorridor> containsCampoLimpo =
                corridor -> corridor.getName().equalsIgnoreCase("Campo Limpo");

        Assert.assertNotNull(corridors);
        Assert.assertTrue(corridors.length > 0);
        Assert.assertTrue(arrayHasMatch(corridors, containsCampoLimpo));
    }

    @Test
    public void testSearchBusPositionsByLine() throws Exception {
        Bus[] busesPosition = api.searchBusesByLine(bonifacio).getVehicles();

        Assert.assertNotNull(busesPosition);
        Assert.assertTrue(busesPosition.length > 0);
    }

    @Test
    public void testGetForecastWithLineAndStop() throws Exception {
        Assume.assumeTrue(isAfter4amAndBeforeMidnight());

        ForecastWithStopAndLine forecast =
                api.getForecastWithStopAndLine(campanellaStopId, alvim);

        Assert.assertNotNull(forecast.getBuses());
        Assert.assertTrue(forecast.getBuses().length > 0);
    }

    @Test
    public void testGetForecastWithLine() throws Exception {
        ForecastWithLine forecast = api.getForecastWithLine(bonifacio);
        BusStopNow[] busStops = forecast.getBusStops();

        Assert.assertNotNull(busStops);
        Assert.assertTrue(busStops.length > 0);
    }

    @Test
    public void testGetForecastWithStop() throws Exception {
        Assume.assumeTrue(isAfter4amAndBeforeMidnight());

        ForecastWithStop forecast = api.getForecastWithStop(campanellaStopId);
        BusLineNow[] busLines = forecast.getBusLines();

        Assert.assertEquals(forecast.getBusStop().getCode(), campanellaStopId);
        Assert.assertNotNull(busLines);
        Assert.assertTrue(busLines.length > 0);
    }

    private <T> boolean arrayHasMatch(T[] array, Predicate predicate) {
        return Arrays.asList(array)
                .parallelStream()
                .anyMatch(predicate);
    }
}