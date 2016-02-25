package com.intellij;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
//TODO add dependency for mechanize, in case we really need to use python
    @Nullable
    protected void downloadGtfs() throws Exception {

        CloseableHttpClient client = HttpClients.createDefault();
        CookieStore cookieStore = new BasicCookieStore();
        HttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

        String url = "http://www.sptrans.com.br/desenvolvedores/Default.aspx";
        HttpPost post = new HttpPost(url);
//        post.addHeader("Content-Type", "text/html");
        post.addHeader("User-Agent", "Firefox");
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("__EVENTTARGET", "btnDevPlaceLogin"));
        urlParameters.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
        urlParameters.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwULLTE4OTg1ODMzMTEPZBYCAgEPZBYCAgUPZBYCAgUPZBYGAgUPEA8WBh4NRGF0YVRleHRGaWVsZAUGUmVnaWFvHg5EYXRhVmFsdWVGaWVsZAUCSUQeC18hRGF0YUJvdW5kZ2QQFQUFTm9ydGUDU3VsBUxlc3RlBU9lc3RlBkNlbnRybxUFATMBNAE1ATYBNxQrAwVnZ2dnZ2RkAgcPEA8WBh8ABQZSZWdpYW8fAQUCSUQfAmdkEBUFBU5vcnRlA1N1bAVMZXN0ZQVPZXN0ZQZDZW50cm8VBQEzATQBNQE2ATcUKwMFZ2dnZ2dkZAILDxAPFgYfAAUHQXNzdW50bx8BBQJJRB8CZ2QQFQMIQ3VsdHVyYWwXTXVkYW7Dp2EgZGUgSXRpbmVyw6FyaW8UVHVkbyBzb2JyZSBhIFNQVHJhbnMVAwE0ATUBNhQrAwNnZ2dkZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WAQUSZm9vdGVyJGFzc3VudG9OZXdzt6AsqgaoBE8A6vVYlzubLIRncF8="));
        urlParameters.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "8670F803"));
        urlParameters.add(new BasicNameValuePair("__PREVIOUSPAGE", "nYv4ZSINq2Nk9-fgU7AfADn5PuAh3H8JjIM09SMi8ZWvVwTg2bgjznTQGPmFJOFEJEw0Ofjk5LNQNphL8LYRDQA18Aoo_32TGZXk2b2pdEy6jSKJ0"));
        urlParameters.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEWFQKgpb6ADgLyl+GoDQLCotn/AgKy+7+3CwKYkZrhAQLl0pyxCQLMj6uNDgLtl6l7AuyXqXsC65epewLql6l7AumXqXsCq5+dpgUCqp+dpgUCrZ+dpgUCrJ+dpgUCr5+dpgUCxYSLjQ0Cq6n20gwCrKn20gwCran20gyPOe+C+UkjZMLvJPdb79jAd7s/DQ=="));
//        urlParameters.add(new BasicNameValuePair("busca", "|"));
//        urlParameters.add(new BasicNameValuePair("tipoHeaderPesquisa", ""));
        urlParameters.add(new BasicNameValuePair("devPlaceUserName", "ruan0408"));
        urlParameters.add(new BasicNameValuePair("devPlaceUserPass", "costaruan"));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        CloseableHttpResponse response = client.execute(post, httpContext);
        System.out.println(response.getStatusLine());

        url = "http://eu3.heatmap.it/log/clk?pid=1240&u=http://www.sptrans.com.br/desenvolvedores/Default.aspx&dpath=Fdddddddd{da&durl=javascript:webform_dopostbackwithoptions(newwebform_postbackoptions(btndevp";
        HttpGet get = new HttpGet(url);
        get.addHeader("User-Agent", "Firefox");
        response = client.execute(get, httpContext);

        System.out.println(response.getStatusLine());

        for (Cookie cookie : cookieStore.getCookies()) {
            System.out.println(cookie);
        }

        url = "http://www.sptrans.com.br/desenvolvedores/GTFS.aspx";
        post = new HttpPost(url);
        post.addHeader("User-Agent", "Firefox");
//        post.addHeader("Content-Type", "text/html");
        urlParameters.clear();
        urlParameters.add(new BasicNameValuePair("__EVENTTARGET", "btDownloadGTFS"));
        urlParameters.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
        urlParameters.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwUJMjYyODkwODg5D2QWAgIBD2QWAgIFD2QWAgIFD2QWBgIFDxAPFgYeDURhdGFUZXh0RmllbGQFBlJlZ2lhbx4ORGF0YVZhbHVlRmllbGQFAklEHgtfIURhdGFCb3VuZGdkEBUFBU5vcnRlA1N1bAVMZXN0ZQVPZXN0ZQZDZW50cm8VBQEzATQBNQE2ATcUKwMFZ2dnZ2dkZAIHDxAPFgYfAAUGUmVnaWFvHwEFAklEHwJnZBAVBQVOb3J0ZQNTdWwFTGVzdGUFT2VzdGUGQ2VudHJvFQUBMwE0ATUBNgE3FCsDBWdnZ2dnZGQCCw8QDxYGHwAFB0Fzc3VudG8fAQUCSUQfAmdkEBUDCEN1bHR1cmFsF011ZGFuw6dhIGRlIEl0aW5lcsOhcmlvFFR1ZG8gc29icmUgYSBTUFRyYW5zFQMBNAE1ATYUKwMDZ2dnZGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFEmZvb3RlciRhc3N1bnRvTmV3c38dEBLa0T21qbs/ED35HJArzEcR"));
        urlParameters.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "1264513B"));
        urlParameters.add(new BasicNameValuePair("__PREVIOUSPAGE", "nYv4ZSINq2Nk9-fgU7AfADn5PuAh3H8JjIM09SMi8ZWvVwTg2bgjznTQGPmFJOFEJEw0Ofjk5LNQNphL8LYRDQA18Aoo_32TGZXk2b2pdEy6jSKJ0"));
        urlParameters.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEWEgKU9rXdAgLIpKOvDQLl0pyxCQLMj6uNDgLtl6l7AuyXqXsC65epewLql6l7AumXqXsCq5+dpgUCqp+dpgUCrZ+dpgUCrJ+dpgUCr5+dpgUCxYSLjQ0Cq6n20gwCrKn20gwCran20gx4zWIR6BiofqlBftTNCoelYwAW0A=="));
        urlParameters.add(new BasicNameValuePair("busca", "|"));
        urlParameters.add(new BasicNameValuePair("tipoHeaderPesquisa", ""));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        Cookie cookie = cookieStore.getCookies().get(0);

        post.setHeader("Cookie", cookie.getName()+"="+cookie.getValue());

        File yourFile = new File("/Users/ruan0408/Downloads/downloadGtfs.zip");
        if(!yourFile.exists()) {
            yourFile.createNewFile();
        }

        response = client.execute(post, httpContext);
        System.out.println(Arrays.toString(response.getAllHeaders()));
        FileOutputStream output = new FileOutputStream(yourFile);

        InputStream input = response.getEntity().getContent();

        copy(input, output, 1024);

        client.close();
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
