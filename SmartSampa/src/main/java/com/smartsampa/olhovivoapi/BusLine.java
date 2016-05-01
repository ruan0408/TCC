package com.smartsampa.olhovivoapi;

/**
 * Created by ruan0408 on 12/02/2016.
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartsampa.busapi.model.Heading;
import com.smartsampa.busapi.model.AbstractTrip;

/**
 * Wrapper class for the bus line json returned by the PublicTransportAPI.
 * The fields are not in camel case so we can automatically parse the json.
 * */
public class BusLine extends AbstractTrip {
//TODO change visibility of all classes from olhovivo, since they are data structures
    @JsonProperty("CodigoLinha") public int code;
    @JsonProperty("Circular") public boolean circular;
    @JsonProperty("Letreiro") public String numberSign;
    @JsonProperty("Sentido") public int heading;
    @JsonProperty("Tipo") public int type;
    @JsonProperty("DenominacaoTPTS") public String destinationSignMTST;
    @JsonProperty("DenominacaoTSTP") public String destinationSignSTMT;
    @JsonProperty("Informacoes") public String info;


    public int getType() {return type;}

    public String getInfo() {return info;}

    public String getDestinationSignMTST() {
        return destinationSignMTST;
    }

    public String getDestinationSignSTMT() {
        return destinationSignSTMT;
    }

    public int getCode() { return code; }

    @Override
    public String getDestinationSign() {return heading == 1 ? destinationSignMTST : destinationSignSTMT;}

    @Override
    public String getNumberSign() {return numberSign+"-"+getType();}

    @Override
    public Heading getHeading() {return Heading.getHeadingFromInt(heading);}

    @Override
    public Boolean isCircular() {return circular;}

    @Override
    public Integer getOlhovivoId() {return code;}
}
