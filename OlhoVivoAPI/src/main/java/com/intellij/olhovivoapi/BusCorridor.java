package com.intellij.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusCorridor {

    @JsonProperty("CodCot") private int codCot;
    @JsonProperty("CodCorredor") private int code;
    @JsonProperty("Nome") private String name;

    @JsonCreator
    protected BusCorridor(@JsonProperty("CodCot") int codCot,
                          @JsonProperty("CodCorredor")int code,
                          @JsonProperty("Nome") String name) {

        this.codCot = codCot;
        this.code = code;
        this.name = name;
    }

    /**
     *
     * @return codCot. I don't know what that is. It's not documented and it's always zero.
     */
    public int getCodCot() {
        return codCot;
    }

    /**
     *
     * @return Code of the corridor.
     */
    public int getCode() {
        return code;
    }

    /**
     *
     * @return The name of the corridor.
     */
    public String getName() {
        return name;
    }
}
