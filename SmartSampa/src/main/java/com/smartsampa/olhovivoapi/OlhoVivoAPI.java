package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartsampa.busapi.model.*;
import com.smartsampa.utils.APIConnectionException;
import com.smartsampa.utils.HttpUrlConnector;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by ruan0408 on 10/02/2016.
 */
public class OlhoVivoAPI {

    private static final String BASE_URL = "http://api.olhovivo.sptrans.com.br/v0/";
    protected static ObjectMapper jsonParser = new ObjectMapper();
    private String authKey;
    private HttpUrlConnector httpConnector;

    public OlhoVivoAPI(String key) {
        jsonParser.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        jsonParser.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        authKey = key;
        httpConnector = new HttpUrlConnector();
    }

    public boolean authenticate() {
        String url = BASE_URL +"Login/Autenticar?token="+encodeToURL(authKey);
        String response = null;
        try {
            response = httpConnector.executePostWithoutForm(url);
        } catch (IOException e) {
            e.printStackTrace();
            throw new APIConnectionException("There was a problem authenticating with the OlhoVivoAPI");
        }

        return response.equalsIgnoreCase("true");
    }

    public Set<Trip> getTripsByTerm(String searchTerms) {
        String url = BASE_URL +"/Linha/Buscar?termosBusca="+encodeToURL(searchTerms);
        BusLine[] busLines = performQuery(url, BusLine[].class);

        return busLines != null ? new HashSet<>(Arrays.asList(busLines)) : Collections.emptySet();
    }

    public Set<Stop> getStopsByTerm(String searchTerms) {
        String url = BASE_URL +"/Parada/Buscar?termosBusca="+encodeToURL(searchTerms);
        BusStop[] stops = performQuery(url, BusStop[].class);

        return stops != null ? new HashSet<>(Arrays.asList(stops)) : Collections.emptySet();
    }

    public Set<Bus> getAllRunningBusesOfTrip(int busLineCode) {
        String url = BASE_URL + "/Posicao?codigoLinha="+encodeToURL(busLineCode+"");
        BusLinePositions busLinePositions = performQuery(url, BusLinePositions.class);
        Bus[] buses = busLinePositions.getVehicles();

        return buses != null ? new HashSet<>(Arrays.asList(buses)) : Collections.emptySet();
    }

    public List<PredictedBus> getPredictionsOfTripAtStop(int busStopCode, int busLineCode) {
        String url = BASE_URL + "/Previsao?codigoParada="+busStopCode+"&codigoLinha="+busLineCode;
        ForecastWithStopAndLine forecast = performQuery(url, ForecastWithStopAndLine.class);
        BusNow[] buses = forecast.getBuses();

        return buses != null ? Arrays.asList(buses) : Collections.emptyList();
    }

    public Map<AbstractStop, List<PredictedBus>> getPredictionsOfTrip(int busLineCode) {
        String url = BASE_URL + "/Previsao/Linha?codigoLinha="+encodeToURL(busLineCode+"");
        ForecastWithLine forecastWithLine = performQuery(url, ForecastWithLine.class);
        BusStopNow[] stopsWithForecast = forecastWithLine.getBusStops();

        if (stopsWithForecast == null) return Collections.emptyMap();

        Map<AbstractStop, List<PredictedBus>> forecast = new HashMap<>();
        for (BusStopNow stopNow : stopsWithForecast)
            forecast.put(stopNow, Arrays.asList(stopNow.getVehicles()));

        return forecast;
    }

    public Map<AbstractTrip, List<PredictedBus>> getPredictionsAtStop(int busStopCode) {
        String url = BASE_URL + "/Previsao/Parada?codigoParada="+encodeToURL(busStopCode+"");
        ForecastWithStop forecastWithStop = performQuery(url, ForecastWithStop.class);
        BusLineNow[] linesWithForecast = forecastWithStop.getBusLines();

        Map<AbstractTrip, List<PredictedBus>> forecast = new HashMap<>();
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






    public BusStop[] searchBusStopsByLine(int busLineCode) {
        String url = BASE_URL +"/Parada/BuscarParadasPorLinha?codigoLinha="+encodeToURL(busLineCode+"");
        return performQuery(url, BusStop[].class);
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

    private <T> T jsonToObject(String jsonResponse, Class<T> tClass) {
        try {
            return jsonParser.readValue(jsonResponse, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String encodeToURL(String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new APIConnectionException("There was a problem encoding the string "+string);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("authKey", authKey)
                .append("httpConnector", httpConnector)
                .toString();
    }
}

//    //assuming olhovivo will ALWAYS return two results for the same line number\
//    public Pair<BusLine, BusLine> getBothTrips(String fullNumberSign) {
//        BusLine[] busLines = this.searchBusLines(fullNumberSign);
//
//        if (busLines.length != 2)
//            throw new RuntimeException("Couldn't find two trips for the fullNumberSign: "+fullNumberSign);
//        if (busLines[0].getHeading() == Heading.SECONDARY_TERMINAL)
//            return new MutablePair<>(busLines[0], busLines[1]);
//
//        return new MutablePair<>(busLines[1], busLines[0]);
//    }

