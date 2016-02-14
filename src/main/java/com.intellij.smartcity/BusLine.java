package com.intellij.smartcity;

/**
 * Created by ruan0408 on 12/02/2016.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class for the bus line json returned by the API.
 * The fields are not in camel case so we can automatically parse the json.
 * */
public class BusLine {
    private int code;
    private boolean circular;
    private String destinationSign;
    private int heading;
    private int type;
    private String destinationSignMTST;
    private String destinationSignSTMT;
    private String info;

    @JsonProperty("CodigoLinha")
    public int getCode() {
        return code;
    }

    @JsonProperty("Circular")
    public boolean isCircular() {
        return circular;
    }

    @JsonProperty("Letreiro")
    public String getDestinationSign() {
        return destinationSign;
    }

    @JsonProperty("Sentido")
    public int getHeading() {
        return heading;
    }

    @JsonProperty("Tipo")
    public int getType() {
        return type;
    }

    @JsonProperty("DenominacaoTPTS")
    public String getDestinationSignMTST() {
        return destinationSignMTST;
    }

    @JsonProperty("DenominacaoTSTP")
    public String getDestinationSignSTMT() {
        return destinationSignSTMT;
    }

    @JsonProperty("Informacoes")
    public String getInfo() {
        return info;
    }
}
