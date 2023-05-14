package com.tests;
import com.api.APIClient;
import com.model.CreateObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.http.HttpStatus;
import static org.testng.Assert.*;
import org.json.JSONArray;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Sumudu on 13/05/2023
 * @project REST_API_TESTAUTOMATION_JAVA
 */

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

    @Test(testName = "Validating GET Request Endpoint")
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
        assertEquals(appleMacBookPro16Data.getDouble("price"),Double.parseDouble(assertions.getProperty("appleMacBookPro16.price")));
        assertEquals(appleMacBookPro16Data.getString("CPU model"), assertions.getProperty("appleMacBookPro16.cpuModel"));
        assertEquals(appleMacBookPro16Data.getString("Hard disk size"), assertions.getProperty("appleMacBookPro16.hardDiskSize"));

        // Assertions for Google Pixel 6 Pro
        assertEquals(googlePixel6Pro.getString("name"), assertions.getProperty("googlePixel6Pro.name"));

    }

    @Test(testName = "Validating POST Request Endpoint")
    public void testPostObjectEndpoint() throws IOException, JSONException{

        // create request body
        CreateObjectRequest requestBody = new CreateObjectRequest();
        requestBody.setName("Apple MacBook Pro 14");
        requestBody.getData().setYear(2019);
        requestBody.getData().setPrice(1899.99);
        requestBody.getData().setCpuModel("Intel Core i9");
        requestBody.getData().setHardDiskSize("1 TB");

        //perform the POST request

        String response = client.post("objects", requestBody);
        int statusCode = client.getLastStatusCode();

        // Perform assertions on the response and status code
        assertEquals(statusCode, HttpStatus.SC_OK);

        // Assert not null the response
        assertNotNull(response);

        // Convert the response to a JSON object
        JSONObject jsonResponse = new JSONObject(response);

        // Assert the properties of the response object
        assertTrue(jsonResponse.has("id"));
        assertEquals(jsonResponse.getString("name"), assertions.getProperty("create.name"));

        // Extract the ID from the response
        String objectId = jsonResponse.getString("id");
        assertNotNull(objectId);
        System.out.println("Created object ID: " + objectId);

    /*
    if you need to validate manually the POST request please uncomment this line
     */
//        System.out.println(response);

    }

    @Test(testName = "Validating PUT Request Endpoint")
    public void testPutObjectsEndpoint() throws IOException, JSONException {
        // Create a new object
        CreateObjectRequest requestBody = new CreateObjectRequest();
        requestBody.setName("Apple MacBook Pro 14");
        requestBody.getData().setYear(2019);
        requestBody.getData().setPrice(1899.99);
        requestBody.getData().setCpuModel("Intel Core i9");
        requestBody.getData().setHardDiskSize("1 TB");
        String response = client.post("objects", requestBody);

        // Convert the response to a JSON object
        JSONObject jsonResponse = new JSONObject(response);

        // Extract the ID from the response
        String objectId = jsonResponse.getString("id");

        // Update the request
        CreateObjectRequest updateRequestBody = new CreateObjectRequest();
        updateRequestBody.setName(assertions.getProperty("update.name"));
        updateRequestBody.getData().setCpuModel(assertions.getProperty("update.cpuModel"));
        updateRequestBody.getData().setYear(Integer.parseInt(assertions.getProperty("update.year")));
        updateRequestBody.getData().setPrice(Double.parseDouble(assertions.getProperty("update.price")));

        String updateResponse = client.put("objects",updateRequestBody,objectId);
        int statusCode = client.getLastStatusCode();

        // Perform assertions on the response and status code
        assertEquals(statusCode, HttpStatus.SC_OK);

        JSONObject updateResponseJson = new JSONObject(updateResponse);

        /*
        If need to validate the update request please uncomment this line
         */
//        System.out.println(updateResponseJson);

        String updatedObjectId = updateResponseJson.getString("id");

        // Assert the updated object ID matches the original object ID
        assertEquals(updatedObjectId, objectId);
    }

    @Test(testName = "Validating DELETE Request Endpoint")
    public void testDeleteObjectEndpoint() throws IOException, JSONException{

        // Create a new object
        CreateObjectRequest requestBody = new CreateObjectRequest();
        requestBody.setName("Samsung Galaxy S22");
        requestBody.getData().setYear(2023);
        requestBody.getData().setPrice(1000.00);
        requestBody.getData().setCpuModel("Snapdragon chipset");
        requestBody.getData().setHardDiskSize("512 GB");
        String response = client.post("objects", requestBody);

        // Convert the response to a JSON object
        JSONObject jsonResponse = new JSONObject(response);

        // Extract the ID from the response
        String objectId = jsonResponse.getString("id");

        // Delete the Object
        client.delete("objects",objectId);

        int statusCode = client.getLastStatusCode();

        // Perform assertions on the status code
        assertEquals(statusCode, HttpStatus.SC_OK); // when success the delete operation, getting the status code as 200 not 204

        // Send a GET request to check if the object exists
        String getResponse = client.get("objects");
        JSONArray jsonArray = new JSONArray(getResponse);

        // Assert that the deleted object is no longer present in the response
        boolean isObjectPresent = false;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            if (id.equals(objectId)) {
                isObjectPresent = true;
                break;
            }
        }

        assertFalse(isObjectPresent);

    }


}
