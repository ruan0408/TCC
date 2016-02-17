package com.intellij.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.serialization.GtfsReader;

import java.io.File;
import java.io.IOException;

/**
 * Created by ruan0408 on 10/02/2016.
 */
public class OlhoVivoAPI {

    private static final String BASE_URL = "http://api.olhovivo.sptrans.com.br/v0/";
    private static final String GTFS_PATH = "gtfs-sp";
    protected static ObjectMapper jsonParser = new ObjectMapper();
    protected static GtfsReader gtfsReader;
    private String authKey;
    private HttpUrlConnector httpConnector;

    public OlhoVivoAPI(String key) {
        jsonParser.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        loadGtfs();
        authKey = key;
        httpConnector = new HttpUrlConnector();
    }

    //TODO Use the store to get information from gtfs
    //TODO Try to download the gtfs file in a new thread.
    //TODO Load the gtfs in a new thread.

    private void loadGtfs() {
        gtfsReader = new GtfsReader();
        GtfsDaoImpl store = new GtfsDaoImpl();
        try {
            gtfsReader.setInputLocation(new File(getClass().getClassLoader().
                    getResource(GTFS_PATH).getPath()));
            gtfsReader.setEntityStore(store);
            gtfsReader.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticate() {

        String url = BASE_URL +"Login/Autenticar?token="+authKey;
        String response = httpConnector.executePostWithoutForm(url);

        if (response.equalsIgnoreCase("true")) return true;
        return false;
    }

    public BusLine[] searchBusLines(String termosBusca) {
        String url = BASE_URL +"/Linha/Buscar?termosBusca="+termosBusca;
        String jsonResponse = httpConnector.executeGet(url);
        return jsonToObject(jsonResponse, BusLine[].class);
    }

    public String getBusLineDetails(int busLineCode) {
        String url = BASE_URL +"/Linha/CarregarDetalhes?codigoLinha="+busLineCode;
        String jsonResponse = httpConnector.executeGet(url);
        return jsonResponse;
    }

    public BusStop[] searchBusStops(String searchTerms) {
        String url = BASE_URL +"/Parada/Buscar?termosBusca="+searchTerms;
        String jsonResponse = httpConnector.executeGet(url);
        return jsonToObject(jsonResponse, BusStop[].class);
    }

    public BusStop[] searchBusStopsByLine(int busLineCode) {
        String url = BASE_URL +"/Parada/BuscarParadasPorLinha?codigoLinha="+busLineCode;
        String jsonResponse = httpConnector.executeGet(url);
        return jsonToObject(jsonResponse, BusStop[].class);
    }

    public BusStop[] searchBusStopsByCorridor(int busCorridorCode) {
        String url = BASE_URL + "/Parada/BuscarParadasPorCorredor?codigoCorredor="+busCorridorCode;
        String jsonResponse = httpConnector.executeGet(url);
        return jsonToObject(jsonResponse, BusStop[].class);
    }

    public BusCorridor[] getAllBusCorridors() {
        String url = BASE_URL + "/Corredor";
        String jsonResponse = httpConnector.executeGet(url);
        return jsonToObject(jsonResponse, BusCorridor[].class);
    }

    public BusLinePositions searchBusPositionsByLine(int busLineCode) {
        String url = BASE_URL + "/Posicao?codigoLinha="+busLineCode;
        String jsonResponse = httpConnector.executeGet(url);
        return jsonToObject(jsonResponse, BusLinePositions.class);
    }

    public ForecastWithStopAndLine getForecastWithStopAndLine(int busStopCode, int busLineCode) {
        String url = BASE_URL + "/Previsao?codigoParada="+busStopCode+"&codigoLinha="+busLineCode;
        String jsonResponse = httpConnector.executeGet(url);
        return jsonToObject(jsonResponse, ForecastWithStopAndLine.class);
    }

    public ForecastWithLine getForecastWithLine(int busLineCode) {
        String url = BASE_URL + "/Previsao/Linha?codigoLinha="+busLineCode;
        String jsonResponse = httpConnector.executeGet(url);

        return jsonToObject(jsonResponse, ForecastWithLine.class);
    }

    public ForecastWithStop getForecastWithStop(int busStopCode) {
        String url = BASE_URL + "/Previsao/Parada?codigoParada="+busStopCode;
        String jsonResponse = httpConnector.executeGet(url);
        return jsonToObject(jsonResponse, ForecastWithStop.class);
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

