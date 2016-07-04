package com.smartsampa.utils;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ruan0408 on 11/02/2016.
 */
public class HttpUrlConnector {

    private CloseableHttpClient client = HttpClients.createDefault();

    public String executePostWithoutForm(String targetURL) throws IOException {
        return executeRequest(new HttpPost(targetURL));
    }

    public String executeGet(String url) throws IOException {
        return executeRequest(new HttpGet(url));
    }

    private String executeRequest(HttpRequestBase request) throws IOException {
        CloseableHttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) result.append(line);

        return result.toString();
    }
}
