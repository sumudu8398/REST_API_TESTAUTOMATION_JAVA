package com.tests;
import com.api.APIClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.json.JSONArray;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.testng.Assert.*;

public class TestEndpoints {

    private APIClient client;
    private Properties assertions;

    @BeforeClass
    public void setup() throws IOException {
        client = new APIClient("https://api.restful-api.dev");
        assertions = new Properties();
        FileInputStream file = new FileInputStream("src/main/resources/assertions.properties");
        assertions.load(file);
        file.close();
    }

    @Test
    public void testGetObjectsEndpoint() throws IOException, JSONException {
        String response = client.get("objects");
        int statusCode = client.getLastStatusCode();

        // Perform assertions on the response and status code
        assertEquals(statusCode, HttpStatus.SC_OK);

        // Assert not null the response
        assertNotNull(response);

        // Convert the response to a JSON array or list
        JSONArray jsonArray = new JSONArray(response);

        // Assert the count of items in the array/list
        assertEquals(jsonArray.length(), 13);

        // Get the specific objects from the array and assert their properties
        JSONObject appleMacBookPro16 = jsonArray.getJSONObject(6);
        JSONObject googlePixel6Pro = jsonArray.getJSONObject(0);


        // Assertions for Apple MacBook Pro 16
        assertEquals(appleMacBookPro16.getString("name"),assertions.getProperty("appleMacBookPro16.name"));
        JSONObject appleMacBookPro16Data = appleMacBookPro16.getJSONObject("data");
        assertEquals(appleMacBookPro16Data.getInt("year"), Integer.parseInt(assertions.getProperty("appleMacBookPro16.year")));
        assertEquals(appleMacBookPro16Data.getDouble("price"), 1849.99, 0.01);
        assertEquals(appleMacBookPro16Data.getString("CPU model"), "Intel Core i9");
        assertEquals(appleMacBookPro16Data.getString("Hard disk size"), "1 TB");

        // Assertions for Google Pixel 6 Pro
        assertEquals(googlePixel6Pro.getString("name"), "Google Pixel 6 Pro");
        JSONObject googlePixel6ProData = googlePixel6Pro.getJSONObject("data");
        assertTrue(googlePixel6ProData.has("color"));
        assertTrue(googlePixel6ProData.has("capacity"));



    }
}
