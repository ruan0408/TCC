package com.intellij.smartcity;

/**
 * Created by ruan0408 on 10/02/2016.
 */
public class OlhoVivoAPIWrapper {

    private static final String baseUrl = "http://api.olhovivo.sptrans.com.br/v0/";
    private String authKey;

    public OlhoVivoAPIWrapper(String key) {
        authKey = key;
    }

    public boolean authenticate() {

        String url = baseUrl+"Login/Autenticar?token="+authKey;
        String response = HttpUrlConnectionWrapper.executePostWithoutForm(url);

        if (response.equalsIgnoreCase("true")) return true;
        return false;
    }

    public String getLinhas(String termosBusca) {
        String url = baseUrl+"/Linha/Buscar?termosBusca="+termosBusca;
        return HttpUrlConnectionWrapper.executeGet(url);
    }
}

