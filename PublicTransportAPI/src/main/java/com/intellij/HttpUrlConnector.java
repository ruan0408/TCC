package com.intellij;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruan0408 on 11/02/2016.
 */
public class HttpUrlConnector {

    private CloseableHttpClient client = HttpClients.createDefault();

    public String executePostWithoutForm(String targetURL) {
        return executeRequest(new HttpPost(targetURL));
    }

    public String executeGet(String url) {
        return executeRequest(new HttpGet(url));
    }

    @Nullable
    private String executeRequest(HttpRequestBase request) {
        try (CloseableHttpResponse response = client.execute(request);
             BufferedReader rd = new BufferedReader(
                     new InputStreamReader(response.getEntity().getContent()))) {

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) result.append(line);

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    protected void downloadGtfs() throws Exception {

        DefaultHttpClient client = new DefaultHttpClient();
        client.setCookieStore(new BasicCookieStore());

        String url = "http://www.sptrans.com.br/desenvolvedores/Default.aspx";
        HttpPost post = new HttpPost(url);
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("devPlaceUserName", "ruan0408"));
        urlParameters.add(new BasicNameValuePair("devPlaceUserPass", "costaruan"));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        CloseableHttpResponse response1 = client.execute(post);
        System.out.println(response1.getStatusLine());

        url = "http://ruan0408:costaruan@www.sptrans.com.br/desenvolvedores/GTFS.aspx";
        post = new HttpPost(url);
//        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.clear();
        urlParameters.add(new BasicNameValuePair("btDownloadGTFS", ""));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        File yourFile = new File("/Users/ruan0408/Downloads/downloadGtfs.zip");
        if(!yourFile.exists()) {
            yourFile.createNewFile();
        }

        try (CloseableHttpResponse response = client.execute(post);
             FileOutputStream output = new FileOutputStream(yourFile)) {

            InputStream input = response.getEntity().getContent();

            copy(input, output, 1024);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        byte[] buf = new byte[bufferSize];
        int n = input.read(buf);
        while (n >= 0) {
            output.write(buf, 0, n);
            n = input.read(buf);
        }
        output.flush();
    }
}
