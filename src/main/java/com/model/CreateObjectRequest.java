package com.model;
/**
 * @author Sumudu on 13/05/2023
 * @project REST_API_TESTAUTOMATION_JAVA
 */
public class CreateObjectRequest {
    private String name;
    public Data data;

    public CreateObjectRequest() {
        data = new Data();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
