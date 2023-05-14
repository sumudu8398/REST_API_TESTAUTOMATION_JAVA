package com.api;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.CreateObjectRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author Sumudu on 13/05/2023
 * @project REST_API_TESTAUTOMATION_JAVA
 */

public class APIClient {
    private final String base_url;
    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private int lastStatusCode;

    public APIClient(String base_url) {
        this.base_url = base_url;
        this.client = HttpClientBuilder.create().build();
        this.objectMapper = new ObjectMapper();
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

    public String post(String endpoint, Object requestBody) throws IOException {
        String url = base_url + "/" + endpoint;

        // Serialize the request body to JSON
        String requstBodyJson = objectMapper.writeValueAsString(requestBody);

        HttpPost request = new HttpPost(url);
        request.setHeader("Content-type", "application/json");

        // set request Body
        StringEntity entity = new StringEntity(requstBodyJson, ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        HttpResponse response = client.execute(request);
        lastStatusCode = response.getStatusLine().getStatusCode();

        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null){
            return EntityUtils.toString(responseEntity);
        }else {
            throw new IOException("Empty response received");
        }

    }

    public String put(String endpoint, CreateObjectRequest requestBody, String id) throws IOException {
        String url = base_url + "/" + endpoint + "/" +id;
        HttpPut request = new HttpPut(url);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBodyString = objectMapper.writeValueAsString(requestBody);

        StringEntity requestEntity = new StringEntity(requestBodyString);
        request.setEntity(requestEntity);
        request.setHeader("Content-type", "application/json");

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);

        lastStatusCode = response.getStatusLine().getStatusCode();

        return responseString;
    }

    public void delete(String endpoint, String id) throws IOException {
        String url = base_url + "/" + endpoint + "/" + id;
        HttpDelete request = new HttpDelete(url);

        HttpResponse response = client.execute(request);
        response.getEntity().getContent().close();

        lastStatusCode = response.getStatusLine().getStatusCode();
    }

    public int getLastStatusCode() {
        return lastStatusCode;
    }


}
