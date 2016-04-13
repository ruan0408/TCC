package com.smartsampa.olhovivoapi;

/**
 * Created by ruan0408 on 12/02/2016.
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartsampa.model.Trip;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Wrapper class for the bus line json returned by the PublicTransportAPI.
 * The fields are not in camel case so we can automatically parse the json.
 * */
public class BusLine extends Trip {
//TODO change visibility of all classes from olhovivo, since they are data structures
    @JsonProperty("CodigoLinha") public int code;
    @JsonProperty("Circular") public boolean circular;
    @JsonProperty("Letreiro") public String numberSign;
    @JsonProperty("Sentido") public int heading;
    @JsonProperty("Tipo") public int type;
    @JsonProperty("DenominacaoTPTS") public String destinationSignMTST;
    @JsonProperty("DenominacaoTSTP") public String destinationSignSTMT;
    @JsonProperty("Informacoes") public String info;


    protected BusLine(int code, String numberSign, int heading,
                      String destinationSignMTST, String destinationSignSTMT) {

        this.code = code;
        this.numberSign = numberSign;
        this.heading = heading;
        this.destinationSignMTST = destinationSignMTST;
        this.destinationSignSTMT = destinationSignSTMT;
    }

    @JsonCreator
    protected BusLine(@JsonProperty("CodigoLinha") int code,
                      @JsonProperty("Circular") boolean circular,
                      @JsonProperty("Letreiro") String numberSign,
                      @JsonProperty("Sentido") int heading,
                      @JsonProperty("Tipo") int type,
                      @JsonProperty("DenominacaoTPTS") String destinationSignMTST,
                      @JsonProperty("DenominacaoTSTP") String destinationSignSTMT,
                      @JsonProperty("Informacoes") String info) {

        this(code, numberSign, heading, destinationSignMTST, destinationSignSTMT);
        this.circular = circular;
        this.type = type;
        this.info = info;
    }


    public int getType() {
        return type;
    }

    public String getInfo() {return info;}

    public String getDestinationSignMTST() {
        return destinationSignMTST;
    }

    public String getDestinationSignSTMT() {
        return destinationSignSTMT;
    }

    public int getCode() { return code; }

    @Override
    public Integer getOlhovivoId() {
        return code;
    }

    @Override
    public String getDestinationSign() {
        return heading == 1 ? destinationSignMTST : destinationSignSTMT;
    }

    @Override
    public String getNumberSign() {
        return numberSign+"-"+getType();
    }

    @Override
    public Integer getHeading() {
        return heading;
    }

    @Override
    public Boolean isCircular() {
        return circular;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("circular", circular)
                .append("numberSign", numberSign)
                .append("heading", heading)
                .append("type", type)
                .append("destinationSignMTST", destinationSignMTST)
                .append("destinationSignSTMT", destinationSignSTMT)
                .append("info", info)
                .toString();
    }
}
