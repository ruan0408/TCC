package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartsampa.utils.APIConnectionException;
import com.smartsampa.utils.HttpUrlConnector;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;

/**
 * Created by ruan0408 on 10/02/2016.
 */
public class OlhoVivoAPI {

    private static final String BASE_URL = "http://api.olhovivo.sptrans.com.br/v0/";
    protected static ObjectMapper jsonParser = new ObjectMapper();
    private String authKey;
    private HttpUrlConnector httpConnector;

    public OlhoVivoAPI(String key) {
        jsonParser.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
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

    public BusLine[] searchBusLines(String termosBusca) {
        String url = BASE_URL +"/Linha/Buscar?termosBusca="+termosBusca;
        return performQuery(url, BusLine[].class);
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

    public BusStop[] searchBusStops(String searchTerms) {
        String url = BASE_URL +"/Parada/Buscar?termosBusca="+searchTerms;
        return performQuery(url, BusStop[].class);
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

    public ForecastWithStopAndLine
    getForecastWithStopAndLine(int busStopCode, int busLineCode) {
        String url = BASE_URL + "/Previsao?codigoParada="+busStopCode+"&codigoLinha="+busLineCode;
        return performQuery(url, ForecastWithStopAndLine.class);
    }

    public ForecastWithLine getForecastWithLine(int busLineCode) {
        String url = BASE_URL + "/Previsao/Linha?codigoLinha="+busLineCode;
        return performQuery(url, ForecastWithLine.class);
    }

    public ForecastWithStop
    getForecastWithStop(int busStopCode) {
        String url = BASE_URL + "/Previsao/Parada?codigoParada="+busStopCode;
        return performQuery(url, ForecastWithStop.class);
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

