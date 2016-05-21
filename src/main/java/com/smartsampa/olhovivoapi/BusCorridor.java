package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartsampa.busapi.model.AbstractCorridor;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusCorridor extends AbstractCorridor {

    @JsonProperty("CodCorredor") public int code;
    @JsonProperty("CodCot") public int codCot;
    @JsonProperty("Nome") public String name;

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
