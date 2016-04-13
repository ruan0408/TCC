package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartsampa.model.Bus;
import com.smartsampa.model.PredictedBus;
import com.smartsampa.model.Stop;
import com.smartsampa.model.Trip;
import com.smartsampa.utils.APIConnectionException;
import com.smartsampa.utils.HttpUrlConnector;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
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

        String url = BASE_URL +"Login/Autenticar?token="+authKey;
        String response = null;
        try {
            response = httpConnector.executePostWithoutForm(url);
        } catch (IOException e) {
            e.printStackTrace();
            throw new APIConnectionException("There was a problem authenticating with the OlhoVivoAPI");
        }

        if (response.equalsIgnoreCase("true")) return true;
        return false;
    }





    public BusLine[] searchBusLines(String searchTerms) {
        String url = BASE_URL +"/Linha/Buscar?termosBusca="+searchTerms;
        return performQuery(url, BusLine[].class);
    }

    public Set<Trip> getTripsByTerm(String searchTerms) {
        String url = BASE_URL +"/Linha/Buscar?termosBusca="+searchTerms;
        BusLine[] busLines = performQuery(url, BusLine[].class);

        return new HashSet<>(Arrays.asList(busLines));
    }





    public BusStop[] searchBusStops(String searchTerms) {
        String url = BASE_URL +"/Parada/Buscar?termosBusca="+searchTerms;
        return performQuery(url, BusStop[].class);
    }

    public Set<Stop> getStopsByTerm(String searchTerms) {
        String url = BASE_URL +"/Parada/Buscar?termosBusca="+searchTerms;
        BusStop[] busStops = performQuery(url, BusStop[].class);

        return new HashSet<>(Arrays.asList(busStops));
    }





    public String getBusLineDetails(int busLineCode) {
        try {
            String url = BASE_URL +"/Linha/CarregarDetalhes?codigoLinha="+busLineCode;
            return httpConnector.executeGet(url);
        } catch (IOException e) {
            throw new APIConnectionException("There was a problem getting the " +
                    "details of the line of code: "+ busLineCode);
        }
    }







    public BusStop[] searchBusStopsByLine(int busLineCode) {
        String url = BASE_URL +"/Parada/BuscarParadasPorLinha?codigoLinha="+busLineCode;
        return performQuery(url, BusStop[].class);
    }

    public BusStop[] searchBusStopsByCorridor(int busCorridorCode) {
        String url = BASE_URL + "/Parada/BuscarParadasPorCorredor?codigoCorredor="+busCorridorCode;
        return performQuery(url, BusStop[].class);
    }

    public BusCorridor[] getAllBusCorridors() {
        String url = BASE_URL + "/Corredor";
        return performQuery(url, BusCorridor[].class);
    }





    public BusLinePositions searchBusesByLine(int busLineCode) {
        String url = BASE_URL + "/Posicao?codigoLinha="+busLineCode;
        return performQuery(url, BusLinePositions.class);
    }

    public Set<Bus> getAllRunningBusesOfTrip(int busLineCode) {
        String url = BASE_URL + "/Posicao?codigoLinha="+busLineCode;
        BusLinePositions busLinePositions = performQuery(url, BusLinePositions.class);
        Bus[] buses = busLinePositions.getVehicles();
        return new HashSet<>(Arrays.asList(buses));
    }





    public ForecastWithStopAndLine getForecastWithStopAndLine(int busStopCode, int busLineCode) {
        String url = BASE_URL + "/Previsao?codigoParada="+busStopCode+"&codigoLinha="+busLineCode;
        return performQuery(url, ForecastWithStopAndLine.class);
    }

    public List<PredictedBus> getPredictionsOfTripAtStop(int busStopCode, int busLineCode) {
        String url = BASE_URL + "/Previsao?codigoParada="+busStopCode+"&codigoLinha="+busLineCode;
        ForecastWithStopAndLine forecast = performQuery(url, ForecastWithStopAndLine.class);
        return Arrays.asList(forecast.getBuses());
    }






    public ForecastWithLine getForecastWithLine(int busLineCode) {
        String url = BASE_URL + "/Previsao/Linha?codigoLinha="+busLineCode;
        return performQuery(url, ForecastWithLine.class);
    }

    public Map<Stop, List<PredictedBus>> getPredictionsOfTrip(int busLineCode) {
        String url = BASE_URL + "/Previsao/Linha?codigoLinha="+busLineCode;
        ForecastWithLine forecastWithLine = performQuery(url, ForecastWithLine.class);
        BusStopNow[] stopsWithForecast = forecastWithLine.getBusStops();

        Map<Stop, List<PredictedBus>> forecast = new HashMap<>();
        for (BusStopNow stops : stopsWithForecast)
            forecast.put(stops.getBusStop(), Arrays.asList(stops.getVehicles()));

        return forecast;
    }




    public ForecastWithStop getForecastWithStop(int busStopCode) {
        String url = BASE_URL + "/Previsao/Parada?codigoParada="+busStopCode;
        return performQuery(url, ForecastWithStop.class);
    }

    public Map<Trip, List<PredictedBus>> getPredictionsAtStop(int busStopCode) {
        String url = BASE_URL + "/Previsao/Parada?codigoParada="+busStopCode;
        ForecastWithStop forecastWithStop = performQuery(url, ForecastWithStop.class);
        BusLineNow[] linesWithForecast = forecastWithStop.getBusLines();

        Map<Trip, List<PredictedBus>> forecast = new HashMap<>();
        for (BusLineNow lineNow : linesWithForecast)
            forecast.put(lineNow.getBusLine(), Arrays.asList(lineNow.getVehicles()));

        return forecast;
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

    //assuming olhovivo will ALWAYS return two results for the same line number\
    public Pair<BusLine, BusLine> getBothTrips(String fullNumberSign) {
        BusLine[] busLines = this.searchBusLines(fullNumberSign);

        if (busLines.length != 2)
            throw new RuntimeException("Couldn't find two trips for the fullNumberSign: "+fullNumberSign);
        if (busLines[0].getHeading() == 1)
            return new MutablePair<>(busLines[0], busLines[1]);

        return new MutablePair<>(busLines[1], busLines[0]);
    }

    private <T> T jsonToObject(String jsonResponse, Class<T> tClass) {
        try {
            return jsonParser.readValue(jsonResponse, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("authKey", authKey)
                .append("httpConnector", httpConnector)
                .toString();
    }
}

