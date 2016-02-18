package com.intellij.olhovivoapi;

/**
 * Created by ruan0408 on 12/02/2016.
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class for the bus line json returned by the API.
 * The fields are not in camel case so we can automatically parse the json.
 * */
public class BusLine {

    @JsonProperty("CodigoLinha") private int code;
    @JsonProperty("Circular") private boolean circular;
    @JsonProperty("Letreiro") private String numberSign;
    @JsonProperty("Sentido") private int heading;
    @JsonProperty("Tipo") private int type;
    @JsonProperty("DenominacaoTPTS") private String destinationSignMTST;
    @JsonProperty("DenominacaoTSTP") private String destinationSignSTMT;
    @JsonProperty("Informacoes") private String info;


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

    /**
     * @return The code of this bus line
     */
    public int getCode() {
        return code;
    }

    /**
     * @return True if this bus line doesn't have a secondary terminal.
     */
    public boolean isCircular() {
        return circular;
    }

    /**
     * @return The destination sign, i.e. the destination being shown in the billboard.
     */
    public String getNumberSign() {
        return numberSign;
    }

    /**
     * @return 1 if this bus is heading to the main terminal.
     * 2 if it's going to the secondary terminal.
     */
    public int getHeading() {
        return heading;
    }

    /**
     * @return Second part of the non-internal line code.
     * BASE (10), ATENDIMENTO (21, 23, 32, 41).
     * e.g.: In the line 2732-10, the type is 10.
     */
    public int getType() {
        return type;
    }

    /**
     * @return The name displayed on the billboard when the buses on
     * this line are heading towards the secondary terminal.
     */
    public String getDestinationSignMTST() {
        return destinationSignMTST;
    }

    /**
     * @return The name displayed on the billboard when the buses on
     * this line are heading towards the main terminal.
     */
    public String getDestinationSignSTMT() {
        return destinationSignSTMT;
    }

    /**
     * @return General information about this line.
     */
    public String getInfo() {
        return info;
    }
}
