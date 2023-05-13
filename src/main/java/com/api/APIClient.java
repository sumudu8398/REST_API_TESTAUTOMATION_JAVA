package com.api;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class APIClient {
    private final String base_url;
    private final HttpClient client;
    private int lastStatusCode;



    public APIClient(String base_url) {
        this.base_url = base_url;
        this.client = HttpClientBuilder.create().build();
    }

    public String get(String endpoint) throws IOException{
        String url = base_url + "/" + endpoint;
        HttpGet request = new HttpGet(url);

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);

        lastStatusCode = response.getStatusLine().getStatusCode();

        return responseString;
    }

    public String post(String endpoint, String requestBody) throws IOException{
        String url = base_url + "/" + endpoint;
        HttpPost request = new HttpPost(url);

        StringEntity requestEntity = new StringEntity(requestBody);
        request.setEntity(requestEntity);
        request.setHeader("Content-type", "application/json");

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);

        lastStatusCode = response.getStatusLine().getStatusCode();

        return responseString;
    }

    public String put(String endpoint, String requestBody) throws IOException {
        String url = base_url + "/" + endpoint;
        HttpPut request = new HttpPut(url);

        StringEntity requestEntity = new StringEntity(requestBody);
        request.setEntity(requestEntity);
        request.setHeader("Content-type", "application/json");

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);

        lastStatusCode = response.getStatusLine().getStatusCode();

        return responseString;
    }

    public void delete(String endpoint) throws IOException {
        String url = base_url + "/" + endpoint;
        HttpDelete request = new HttpDelete(url);

        HttpResponse response = client.execute(request);
        response.getEntity().getContent().close();

        lastStatusCode = response.getStatusLine().getStatusCode();
        response.getEntity().getContent().close();
    }

    public int getLastStatusCode() {
        return lastStatusCode;
    }


}
