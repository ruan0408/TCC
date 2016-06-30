package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartsampa.busapi.model.AbstractCorridor;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusCorridor extends AbstractCorridor {

    public int code;
    public int codCot;
    public String name;

    @JsonCreator
    BusCorridor(@JsonProperty("CodCorredor") int code,
                @JsonProperty("CodCot") int codCot,
                @JsonProperty("Nome") String name) {
        this.code = code;
        this.codCot = codCot;
        this.name = name;
    }

    @Override
    public int getId() {return code;}


    @Override
    public int getCodCot() {return codCot;}

    @Override
    public String getName() {return name;}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("codCot", codCot)
                .append("code", code)
                .append("name", name)
                .toString();
    }
}
