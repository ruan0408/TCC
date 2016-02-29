package com.intellij.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.utils.APIConnectionException;
import com.intellij.utils.HttpUrlConnector;
import com.intellij.openapi.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * Created by ruan0408 on 10/02/2016.
 */
public class OlhoVivoAPI {

    private static final String BASE_URL = "http://api.olhovivo.sptrans.com.br/v0/";
    protected static ObjectMapper jsonParser = new ObjectMapper();
    private String authKey;
    private HttpUrlConnector httpConnector;

    public static OlhoVivoAPI getInstance(String key) {
        OlhoVivoAPI api = new OlhoVivoAPI(key);
        try {
            api.authenticate();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        }
        return api;
    }

    private OlhoVivoAPI(String key) {
        jsonParser.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        authKey = key;
        httpConnector = new HttpUrlConnector();
    }

    public boolean authenticate() throws APIConnectionException {

        String url = BASE_URL +"Login/Autenticar?token="+authKey;
        String response = null;
        try {
            response = httpConnector.executePostWithoutForm(url);
        } catch (IOException e) {
            e.printStackTrace();
            throw APIConnectionException.throwOlhoVivoConnectionException();
        }

        if (response.equalsIgnoreCase("true")) return true;
        return false;
    }

    public BusLine[] searchBusLines(String termosBusca) throws APIConnectionException {
        String url = BASE_URL +"/Linha/Buscar?termosBusca="+termosBusca;
        return performQuery(url, BusLine[].class);
    }

    //TODO this is weird.
    public String getBusLineDetails(int busLineCode) throws IOException {
        String url = BASE_URL +"/Linha/CarregarDetalhes?codigoLinha="+busLineCode;
        String jsonResponse = httpConnector.executeGet(url);
        return jsonResponse;
    }

    public BusStop[] searchBusStops(String searchTerms) throws APIConnectionException {
        String url = BASE_URL +"/Parada/Buscar?termosBusca="+searchTerms;
        return performQuery(url, BusStop[].class);
    }

    public BusStop[] searchBusStopsByLine(int busLineCode) throws APIConnectionException {
        String url = BASE_URL +"/Parada/BuscarParadasPorLinha?codigoLinha="+busLineCode;
        return performQuery(url, BusStop[].class);
    }

    public BusStop[] searchBusStopsByCorridor(int busCorridorCode) throws APIConnectionException {
        String url = BASE_URL + "/Parada/BuscarParadasPorCorredor?codigoCorredor="+busCorridorCode;
        return performQuery(url, BusStop[].class);
    }

    public BusCorridor[] getAllBusCorridors() throws APIConnectionException {
        String url = BASE_URL + "/Corredor";
        return performQuery(url, BusCorridor[].class);
    }

    public BusLinePositions searchBusesByLine(int busLineCode) throws APIConnectionException {
        String url = BASE_URL + "/Posicao?codigoLinha="+busLineCode;
        return performQuery(url, BusLinePositions.class);
    }

    public ForecastWithStopAndLine
    getForecastWithStopAndLine(int busStopCode, int busLineCode) throws APIConnectionException {
        String url = BASE_URL + "/Previsao?codigoParada="+busStopCode+"&codigoLinha="+busLineCode;
        return performQuery(url, ForecastWithStopAndLine.class);
    }

    public ForecastWithLine getForecastWithLine(int busLineCode) throws APIConnectionException {
        String url = BASE_URL + "/Previsao/Linha?codigoLinha="+busLineCode;
        return performQuery(url, ForecastWithLine.class);
    }

    public ForecastWithStop
    getForecastWithStop(int busStopCode) throws APIConnectionException {
        String url = BASE_URL + "/Previsao/Parada?codigoParada="+busStopCode;
        return performQuery(url, ForecastWithStop.class);
    }

    private <T> T performQuery(String url, Class<T> tClass) throws APIConnectionException {
        String jsonResponse = null;
        try {
            jsonResponse = httpConnector.executeGet(url);
        } catch (IOException e) {
            e.printStackTrace();
            throw APIConnectionException.throwOlhoVivoConnectionException();
        }
        return jsonToObject(jsonResponse, tClass);
    }

    //TODO assuming olhovivo will ALWAYS return two results for the same line number
    public Pair<BusLine, BusLine> getBothTrips(String fullNumberSign) throws Exception {
        BusLine[] busLines = this.searchBusLines(fullNumberSign);

        if (busLines.length != 2) throw new Exception();
        if (busLines[0].getHeading() == 1)
            return new Pair<>(busLines[0], busLines[1]);

        return new Pair<>(busLines[1], busLines[0]);
    }

    @Nullable
    private <T> T jsonToObject(String jsonResponse, Class<T> tClass) {
        try {
            return jsonParser.readValue(jsonResponse, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

