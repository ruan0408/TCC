package com.intellij.smartcity;

/**
 * Created by ruan0408 on 12/02/2016.
 */

/**
 * Wrapper class for the bus line json returned by the API.
 * The fields are not in camel case so we can automatically parse the json.
 * */
public class BusLine {

    private int CodigoLinha;
    private boolean Circular;
    private String Letreiro;
    private int Sentido;
    private int Tipo;
    private String DenominacaoTPTS;
    private String DenominacaoTSTP;
    private String Informacoes;

    public int getLineCode() {
        return CodigoLinha;
    }
}
