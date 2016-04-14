package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusCorridor {

    @JsonProperty("CodCot") private int codCot;
    @JsonProperty("CodCorredor") private int code;
    @JsonProperty("Nome") private String name;

//    @JsonCreator
//    protected BusCorridor(@JsonProperty("CodCot") int codCot,
//                          @JsonProperty("CodCorredor")int code,
//                          @JsonProperty("Nome") String name) {
//
//        this.codCot = codCot;
//        this.code = code;
//        this.name = name;
//    }

    public int getCodCot() {
        return codCot;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("codCot", codCot)
                .append("code", code)
                .append("name", name)
                .toString();
    }
}
