package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusCorridor {

    private int codCot;
    private int code;
    private String name;

    @JsonProperty("CodCot")
    public int getCodCot() {return codCot; }

    @JsonProperty("CodCorredor")
    public int getCode() {return code;}

    @JsonProperty("Nome")
    public String getName() {return name;}
}
