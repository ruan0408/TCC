package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartsampa.busapi.*;
import com.smartsampa.utils.APIConnectionException;
import com.smartsampa.utils.HttpUrlConnector;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 10/02/2016.
 */
public class OlhoVivoAPI {

    private final static String BASE_URL = "http://api.olhovivo.sptrans.com.br/v0/";
    private static ObjectMapper jsonParser = new ObjectMapper();
    private String authKey;
    private HttpUrlConnector httpConnector;

    public OlhoVivoAPI(String key) {
        jsonParser.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        jsonParser.setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY);
        jsonParser.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        authKey = key;
        httpConnector = new HttpUrlConnector();
    }

    public boolean authenticate() {
        try {
            String url = BASE_URL +"Login/Autenticar?token="+encodeToURL(authKey);
            String response = httpConnector.executePostWithoutForm(url);
            return response.equalsIgnoreCase("true");
        } catch (IOException e) {
            throw new APIConnectionException("There was a problem " +
                    "authenticating in the OlhoVivoAPI with the key "+authKey, e);
        }
    }

    public Set<Trip> getTripsByTerm(String searchTerms) {
        String url = BASE_URL +"/Linha/Buscar?termosBusca="+encodeToURL(searchTerms);
        BusLine[] busLines = performQuery(url, BusLine[].class);

        if (busLines == null) return Collections.emptySet();

        return Arrays.asList(busLines).stream()
                .filter(line -> line.containsTerm(searchTerms))
                .collect(Collectors.toSet());
    }

    public Set<Stop> getStopsByTerm(String searchTerms) {
        String url = BASE_URL +"/Parada/Buscar?termosBusca="+encodeToURL(searchTerms);
        BusStop[] stops = performQuery(url, BusStop[].class);

        return stops != null ? new HashSet<>(Arrays.asList(stops)) : Collections.emptySet();
    }

    public Set<Bus> getAllRunningBusesOfTrip(int busLineCode) {
        String url = BASE_URL + "/Posicao?codigoLinha="+encodeToURL(busLineCode+"");

        BusLinePositions busLinePositions = performQuery(url, BusLinePositions.class);
        if (busLinePositions == null) return Collections.emptySet();

        Bus[] buses = busLinePositions.getVehicles();
        return buses != null ? new HashSet<>(Arrays.asList(buses)) : Collections.emptySet();
    }

    public List<PredictedBus> getPredictionsOfTripAtStop(int busLineCode, int busStopCode) {
        String url = BASE_URL + "/Previsao?codigoParada="+busStopCode+"&codigoLinha="+busLineCode;

        ForecastWithStopAndLine forecast = performQuery(url, ForecastWithStopAndLine.class);
        if (forecast == null) return Collections.emptyList();

        BusNow[] buses = forecast.getBuses();

        return buses != null ? Arrays.asList(buses) : Collections.emptyList();
    }

    public Map<Stop, List<PredictedBus>> getPredictionsOfTrip(int busLineCode) {
        String url = BASE_URL + "/Previsao/Linha?codigoLinha="+encodeToURL(busLineCode+"");

        ForecastWithLine forecastWithLine = performQuery(url, ForecastWithLine.class);
        if (forecastWithLine == null) return Collections.emptyMap();

        BusStopNow[] stopsWithForecast = forecastWithLine.getBusStops();
        if (stopsWithForecast == null) return Collections.emptyMap();

        Map<Stop, List<PredictedBus>> forecast = new HashMap<>();
        for (BusStopNow stopNow : stopsWithForecast) {
            if (stopNow == null)
                continue;

            forecast.put(stopNow, Arrays.asList(stopNow.getVehicles()));
        }

        return forecast;
    }

    public Map<Trip, List<PredictedBus>> getPredictionsAtStop(int busStopCode) {
        String url = BASE_URL + "/Previsao/Parada?codigoParada="+encodeToURL(busStopCode+"");

        ForecastWithStop forecastWithStop = performQuery(url, ForecastWithStop.class);
        if (forecastWithStop== null) return Collections.emptyMap();

        BusLineNow[] linesWithForecast = forecastWithStop.getBusLines();
        if (linesWithForecast == null) return Collections.emptyMap();

        Map<Trip, List<PredictedBus>> forecast = new HashMap<>();
        for (BusLineNow lineNow : linesWithForecast)
            forecast.put(lineNow, Arrays.asList(lineNow.vehicles));

        return forecast;
    }

    public List<Corridor> getAllCorridors() {
        String url = BASE_URL + "/Corredor";
        BusCorridor[] corridors = performQuery(url, BusCorridor[].class);
        return corridors != null ? Arrays.asList(corridors) : Collections.emptyList();
    }

    public List<Stop> getStopsByCorridor(int busCorridorCode) {
        String url = BASE_URL + "/Parada/BuscarParadasPorCorredor?codigoCorredor="+encodeToURL(busCorridorCode+"");
        BusStop[] stops = performQuery(url, BusStop[].class);
        return stops != null ? Arrays.asList(stops) : Collections.emptyList();
    }

    private <T> T performQuery(String url, Class<T> tClass) {
        try {
            String jsonResponse = httpConnector.executeGet(url);
            return jsonToObject(jsonResponse, tClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new APIConnectionException("There was a problem performing the query: "+url);
        }
    }

    private <T> T jsonToObject(String jsonResponse, Class<T> tClass) throws IOException {
        return jsonParser.readValue(jsonResponse, tClass);
    }

    private String encodeToURL(String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new APIConnectionException("There was a problem encoding the string "+string, e);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("authKey", authKey)
                .append("httpConnector", httpConnector)
                .toString();
    }

    //    public BusStop[] searchBusStopsByLine(int busLineCode) {
//        String url = BASE_URL +"/Parada/BuscarParadasPorLinha?codigoLinha="+encodeToURL(busLineCode+"");
//        return performQuery(url, BusStop[].class);
//    }
}

